package upf.edu;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;
import upf.edu.filter.FileLanguageFilter;
import upf.edu.model.SimplifiedTweet;
import upf.edu.uploader.S3Uploader;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TwitterLanguageFilterApp {

    private static String blue(String inputString) {
        return "\33[94m" + inputString + "\33[0m";
    }
    public static void main( String[] args ) throws IOException {

        SparkConf conf = new SparkConf().setAppName("Twitter filter");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        //

        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);

        int twitter_counter = 0;

        // preemptively deletes all content of the output file
        new FileWriter(outputFile);

        System.out.println("Language: " + blue(language) + ".\nOutput file: " + blue(outputFile));
        Instant beforeExecution = Instant.now();

        JavaRDD<String> tweets = sparkContext.textFile(argsList.get(2));



        JavaPairRDD<String, Integer> counts = tweets
                .map(t -> SimplifiedTweet.fromJson(t))
                .filter(simpl_tweet -> simpl_tweet.isPresent())
                .filter(f_tweet -> f_tweet.get().getLanguage().equals(language))
                .mapToPair(f_tweet -> new Tuple2<>(f_tweet.get().getLanguage(),1))
                .reduceByKey((a,b)->a+b);
        System.out.println("Total words: " + counts.count());



        System.out.println("Time needed to filter " + language + " files: " + blue(Long.toString(Duration.between(beforeExecution, Instant.now()).getSeconds())) + " seconds");
        counts.saveAsTextFile(outputFile);
    }

}

