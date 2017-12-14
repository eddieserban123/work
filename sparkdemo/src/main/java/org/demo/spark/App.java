package org.demo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.Function;

import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.util.StatCounter;
import org.demo.spark.accumulator.StudentAccumulator;
import org.demo.spark.beans.Student;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Collection;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static org.apache.spark.sql.functions.*;


/**
 * https://spark.apache.org/docs/2.1.1/ml-features.html#onehotencoder
 */
public class App {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("demo").setMaster("local").
                        set("spark.cassandra.connection.host", "localhost");

        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession session = SparkSession.builder().appName("session").getOrCreate();
        JavaRDD<Student> students =
                javaFunctions(sc).cassandraTable("test", "school", mapRowTo(Student.class));

        //first way

        Dataset<Student> studentsDf = session.createDataset(students.rdd(), Encoders.bean(Student.class));
        Dataset<Row> rows = studentsDf.groupBy("classroom").agg(avg("mark1"), stddev("mark2"));
        rows.show();

        //more programmatic way

        StudentAccumulator stAcc =  new StudentAccumulator();
        sc.sc().register(stAcc);

        students.foreach(student -> stAcc.add(student));




        JavaPairRDD<Integer, Student> classrooms = students.groupBy(
                (Function<Student, Integer>) student -> student.getClassroom()).flatMapValues(a -> a);





//
//
//        classrooms.combineByKey(new Function<Student, Object>() {
//            @Override
//            public Object call(Student student) throws Exception {
//                return null;
//            }
//        })
//



        students.map(new Function<Student, Vector>() {
            @Override
            public Vector call(Student student) throws Exception {
                return null;
            }
        });

        classrooms.mapToDouble(new DoubleFunction<Tuple2<Integer, Student>>() {
            @Override
            public double call(Tuple2<Integer, Student> student) throws Exception {
                return 0;
            }
        });

//        JavaPairRDD<Integer, Double[]> res = students.mapPartitions(new FlatMapFunction<Iterator<Student>, Double[]>() {
//            @Override
//            public Iterator<Double[]> call(Iterator<Student> studentIterator) throws Exception {
//                return null;
//            }
//        });
//



        classrooms.lookup(1).forEach(a -> System.out.println(a));

        classrooms.lookup(2).forEach(a -> System.out.println(a));




//        classrooms.foreach(new VoidFunction<Tuple2<Integer, Iterable<Student>>>() {
//            public void call(Tuple2<Integer, Iterable<Student>> g) throws Exception {
//                System.out.println("\n" + g._1());
//               for(Student s: g._2()) {
//                   System.out.print(s + " ");
//
//               }
//            }
//        });

    }



    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
}
