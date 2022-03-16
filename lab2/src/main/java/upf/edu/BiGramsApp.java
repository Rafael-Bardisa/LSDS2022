package upf.edu;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import upf.edu.model.ExtendedSimplifiedTweet;
import upf.edu.BiGram;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Optional;

public class BiGramsApp {
    public static void main(String[] args){
        String input = args[0];
        String outputDir = args[1];
        System.out.println();
        //Create a SparkContext to initialize
        SparkConf conf = new SparkConf().setAppName("BiGrams App");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        // Load input
        JavaRDD<String> sentences = sparkContext.textFile(input);

        JavaPairRDD<BiGram, Integer> syntaxTest = sentences
                .flatMap(s -> Arrays.asList(s.split("\n")).iterator()) // divide en tweets
                .map(ExtendedSimplifiedTweet::fromJson)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(extendedSimplifiedTweet -> BiGram.fromExtendedTweet(extendedSimplifiedTweet).iterator())
                .mapToPair(biGram -> new Tuple2<>(biGram, 1))
                .reduceByKey(Integer::sum);

        System.out.println("Total Bigrams: " + syntaxTest.count());
        syntaxTest.saveAsTextFile(outputDir);

/*
        JavaPairRDD<String, Integer> counts = sentences
                .flatMap(s -> Arrays.asList(s.split("[ ]")).iterator())
                .map(BiGramsApp::normalise)
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);
        System.out.println("Total words: " + counts.count());
        counts.saveAsTextFile(outputDir);*/
    }


}
