package org.demo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.demo.spark.accumulator.StudentAccumulator;
import org.demo.spark.accumulator.TradeTimeAccumulator;
import org.demo.spark.aggregate.MyAverage;
import org.demo.spark.beans.Student;
import org.demo.spark.beans.TradeTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.stddev;


/**
 * https://spark.apache.org/docs/2.1.1/ml-features.html#onehotencoder
 * https://github.com/clakech/sparkassandra-dockerized
 * spark-submit --class org.demo.spark.App --master spark://10.64.134.27:7077 --total-executor-cores 2 ./target/sparkdemo-1.0-SNAPSHOT-jar-with-dependencies.jar
 */
public class DemoApp {

    final static Logger log = LoggerFactory.getLogger(DemoApp.class);

    public static void main(String[] args) {

        String db = "test";
        String table = "trades_by_time";
        if(args.length==2) {
            db=args[0];
            table=args[1];
        }


        SparkConf conf = new SparkConf().setAppName("demo").setMaster("spark://10.64.134.27:7077").
                set("spark.cassandra.connection.host", "10.64.134.27");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession session = SparkSession.builder().appName("session").getOrCreate();

        session.udf().register("myavg", new MyAverage());


        JavaRDD<TradeTime> tradeTimes =
                javaFunctions(sc).cassandraTable(db, table, mapRowTo(TradeTime.class));


        //Give me avg time on days for tradtetime_millis

        Dataset<TradeTime> tradeTimesDf = session.createDataset(tradeTimes.rdd(), Encoders.bean(TradeTime.class));
//          //   1 sql with one group by
//        Dataset<Row> rows = tradeTimesDf.groupBy("day").agg(expr("myavg(tradetime_millis)"));
//        rows.show(1000);

//       //  2 sql with by two group by
//         Dataset<Row> rows = tradeTimesDf.groupBy("day","market_event").agg(expr("myavg(tradetime_millis)"));
//         rows.show();


        // 4 pure programmatic
        TradeTimeAccumulator tradeTimeAcc = new TradeTimeAccumulator();
        sc.sc().register(tradeTimeAcc);
        tradeTimes.foreach(trade -> tradeTimeAcc.add(trade));

        tradeTimeAcc.value().forEach((k,v) -> log.info("^^^^^^^^^^^^^ " + k+" " + v));








    }
}
