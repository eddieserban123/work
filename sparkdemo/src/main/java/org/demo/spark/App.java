package org.demo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.SparkSession;
import org.demo.spark.beans.Student;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("demo").setMaster("local").
                        set("spark.cassandra.connection.host", "localhost");

        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession session = SparkSession.builder().appName("session").getOrCreate();
        JavaRDD<Student> students = sc.parallelize(
                javaFunctions(sc).cassandraTable("test", "school", mapRowTo(Student.class)).collect());

        JavaPairRDD<Integer, Student> classrooms = students.groupBy(new Function<Student, Integer>() {
            public Integer call(Student student) throws Exception {
                return student.getClassroom();
            }
        }).flatMapValues(a -> a);


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
