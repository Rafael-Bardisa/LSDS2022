package upf.edu;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import scala.Tuple2;
import twitter4j.Status;
import twitter4j.auth.OAuthAuthorization;
import upf.edu.util.ConfigUtils;
import upf.edu.util.LanguageMapUtils;

import java.io.IOException;

public class TwitterStateless {
    public static void main(String[] args) throws IOException, InterruptedException {
        String propertiesFile = FilePopup.propertyPath(args, 2);
        String input = args.length == 2 ? args[1] : args[0];
        OAuthAuthorization auth = ConfigUtils.getAuthorizationFromFileProperties(propertiesFile);

        SparkConf conf = new SparkConf().setAppName("Real-time Twitter Stateless Exercise");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(20));
        jsc.checkpoint("/tmp/checkpoint");

        final JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(jsc, auth);

        // Read the language map file by line
        final JavaRDD<String> languageMapLines = jsc
                .sparkContext()
                .textFile(input);
        // transform it to the expected RDD like in Lab 4
        final JavaPairRDD<String, String> languageMap = LanguageMapUtils
                .buildLanguageMap(languageMapLines);

        // prepare the output
        final JavaPairDStream<String, Integer> languageRankStream = stream
                .mapToPair(x -> new Tuple2<>(x.getLang(), 1));

        languageRankStream.transformToPair(rdd -> rdd
                .join(languageMap)
                .mapToPair(x -> new Tuple2<>(x._2._1, x._2._2))
                .sortByKey(false)
                .mapToPair(x -> new Tuple2<>(x._2, x._1))
                );

        // print first 10 results
        languageRankStream.print();

        // Start the application and wait for termination signal
        jsc.start();
        jsc.awaitTermination();
    }
}
