
public static void main(String[]args){
        if(args.length< 1){
        System.err.println("Please provide an input location");
        System.exit(0);
        }
        SparkConf conf=new SparkConf().setAppName("Blog Example: Statistics and Correlation");

        JavaSparkContext sc=new JavaSparkContext(conf);

// context.
        JavaRDD<String> gameLogs=sc.textFile(args[0]);
        ```

        The next step is to manipulate the data to get summary information for each season.The Retrosheet data is structured so each row represents one game.Loading all the data from 2010to 2015at once means that the data needs to be grouped by season.Since the game log has the day the game was played,that can be used to determine the season.As the date is a string I chose to turn the game date into year only since the day the game is played is not necessary for the calculations in this example.
        ```language-java
// Break up the lines based on the comma delimiter.
        JavaRDD<String[]>mappedFile=gameLogs.map(line->line.split(",",-1));
// Parse each line to transform the date into only a year to be able to
// determine what game was in what season.
        JavaRDD<String[]>parsedDate=mappedFile.map(HomerunWinStatisticsController::manipulateDate);
        ```
        Mapping Data
        ```language-java
/**
 * Function that will manipulate the date so we can determine winning
 * percentage and homeruns per team per season.
 */
private static String[]manipulateDate(String[]game){
// Thread local variable containing each thread's date format
final ThreadLocal<DateTimeFormatter> dtf=
        new ThreadLocal<DateTimeFormatter>(){
@Override protected DateTimeFormatter initialValue(){
        return DateTimeFormat.forPattern("yyyymmdd");
        }
        };
        DateTime gameDate=dtf.get().parseDateTime(game[0].replace("\"",""));
        String[]gameLog=game.clone();
        gameLog[0]=String.valueOf(gameDate.getYear());

        return gameLog;
        }
        ```

        Method to Manipulate DateThis mapping allows the data to be grouped by season.Since each game log has the information for two teams,the home team and the visiting team,two group bys need to be performed.One to get all the home games played by a team and the second to get all the away games played by a team in a season.Since the first element in the String array to be the year so we group each game log by the year and either the home team and the visiting team.


        ==Spark group by==
        ```language-java
// Group by the home team and year played in.
        JavaPairRDD<String, Iterable<String[]>>mappedByVisitingTeam=parsedDate
        .groupBy(line->(line[3]+","+line[0]));

// Group by the visiting team and year played in.
        JavaPairRDD<String, Iterable<String[]>>mappedByHomeTeam=parsedDate
        .groupBy(line->(line[6]+","+line[0]));
        ```

        Now that we have the two RDDs,we can join the two together by the key to get all the games played by a team in a year.The result of the join is the Scala implementation of a Tuple with the visiting team games as the first element and the home team games as the second element.

        ==Spark join==
        ```language-java
// Join the visiting team and home team RDD together to get a whole
// season of game logs for each team
        JavaPairRDD<String, Tuple2<Iterable<String[]>,Iterable<String[]>>>joined=mappedByVisitingTeam
        .join(mappedByHomeTeam);
        ```

        The data is now structured in a way that we can manipulate the data to get the total number of homeruns a team had in a season,and the team’s winning percentage in that season.These summary statistics are needed to determine the correlation.We can again use the mapping function to calculate these values for each game and return a new RDD with only these values.Using doubles is important so we can get the actual percentage.Doubles will also be needed to do the correlation.

        ==Spark Map by team==
        ```language-java
// Using the map function to transform the data
        JavaRDD<Double[]>mappedTo=joined.map(HomerunWinStatisticsController::calculateByTeam);
        ```


        ==Map Function for Calculate by Team==
        ```language-java
/**
 * This function will parse out and calculate the total number of homeruns,
 * games won and games played for a team in a single season. Once this is
 * calculated the team, homerun total and winning percentage is returned.
 * Tuple is made up Tuple2<teamKey, Tuple2<gamesAsVisitor, gamesAsHome>>
 */
private static Double[]calculateByTeam(Tuple2<String, Tuple2<Iterable<String[]>,Iterable<String[]>>>v1){
        double teamHomeRuns=0;
        double teamWins=0;
        double gamesPlayed=0;
        /**
         * Grab and loop through the games played as a visitor. Parse out
         * the number of homeruns, and determine if the game was a win or
         * loss. Also increment the total games played.
         */
        for(String[]visitingGames:v1._2._1){
        double visitingTeamScore=Double.parseDouble(visitingGames[9]);
        double homeTeamScore=Double.parseDouble(visitingGames[10]);
        if(visitingTeamScore>homeTeamScore){
        teamWins++;
        }
        teamHomeRuns+=Integer.parseInt(visitingGames[25]);
        gamesPlayed++;
        }

        /**
         * Grab and loop through the games played at home. Parse out the
         * number of homeruns, and determine if the game was a win or loss.
         * Also increment the total games played.
         */
        for(String[]homeGames:v1._2._2){
        double visitingTeamScore=Double.parseDouble(homeGames[9]);
        double homeTeamScore=Double.parseDouble(homeGames[10]);
        if(homeTeamScore>visitingTeamScore){
        teamWins++;
        }
        teamHomeRuns+=Integer.parseInt(homeGames[53]);
        gamesPlayed++;
        }

        Double[]gameInformation={teamHomeRuns,
        teamWins/gamesPlayed};
        return gameInformation;
        }
        ```

        The final step before being able to perform the correlation is to create two RDDs to pass to the correlation function by using the flatMapToDouble function.

        ==Flat map to double==
        ```language-java
        JavaDoubleRDD homeruns=mappedTo.flatMapToDouble(HomerunWinStatisticsController::getHomerun);

        JavaDoubleRDD winningPercentage=mappedTo.flatMapToDouble(HomerunWinStatisticsController::getWinningPercentage);
        ```


        ==Homerun Function==
        ```language-java
/**
 * This function will parse out the homerun total for each team in a single
 * season.
 */
private static Iterable<Double> getHomerun(Double[]t){
        return Arrays.asList(t[0]);
        }
        ```

        ==Winning Percentage Function==
        ```language-java
/**
 * This function will parse out the winning percentage for each team in a
 * single season.
 */
private static Iterable<Double> getWinningPercentage(Double[]t){
        return Arrays.asList(t[1]);
        }
        ```

        These two RDDs can then be passed to the correlation API function.For this example,I printed the result to the console.This is not a best practice to use System.out but this result could be passed on to other applications that can either use the value to make decisions or to visualize the result.For the game logs from 2010to 2015,the correlation was0.3948095282394066which is a weak correlation which shows that a large amount of homeruns does not highly correlate to a high winning percentage.

        ==Spark Correlation==
        ```language-java
        Double correlation=Statistics.corr(JavaDoubleRDD.toRDD(homeruns),JavaDoubleRDD.toRDD(winningPercentage),"pearson");
// temporarily print to console

        System.out.println("**************Pearson coefficiant for homeruns to winning percentage "+correlation);
        ```

        There are also other built in basic statistics that,once the data is manipulated,can be performed to get further insights into homeruns and winning percentage.StatCounter provides an API to get the mean,standard deviation and variance without having to calculate each individually.


        ==Spark StatCounter==
        ```language-java
// List of the main statistics for homeruns;
        StatCounter homerunStats=homeruns.stats();

// temporarily print out to console some example statistics that are
// included in StatCounter - see Javadocs for complete list
        System.out.println("**************Mean of homeruns "+homerunStats.mean());

        System.out.println("**************Standard deviation of homeruns "+homerunStats.stdev());

        System.out.println("**************Variance of homeruns "+homerunStats.variance());
// List of the main statistics for winning percentage.
        StatCounter winningPercentageStats=winningPercentage.stats();

// temporarily print out to console some example statistics that are
// included in StatCounter - see Javadocs for complete list
        System.out.println("**************Mean of winning percentage "+winningPercentageStats.mean());

        System.out.println("**************Standard deviation of winning percentage "+winningPercentageStats.stdev());

        System.out.println("**************Variance of winning percentage "+winningPercentageStats.variance());
