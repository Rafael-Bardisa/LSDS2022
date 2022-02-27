package upf.edu;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;
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

        SparkConf conf = new SparkConf().setAppName("Word Count");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        //

        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputDir = argsList.get(1);
        String bucket = argsList.get(2);
        int twitter_counter = 0;

        // preemptively deletes all content of the output file
        new FileWriter(outputDir);

        System.out.println("Language: " + blue(language) + ".\nOutput file: " + blue(outputDir) + ".\nDestination bucket: " + blue(bucket) + "\n");
        Instant beforeExecution = Instant.now();
        // un if para ver si arglist.get(3) es folder o un file suelto??
        JavaRDD<String> tweets = sparkContext.textFile(argsList.get(3));

        JavaPairRDD<String, Integer> counts = tweets
                .map(SimplifiedTweet::fromJson)
                .filter(Optional::isPresent)
                .filter(f_tweet -> f_tweet.get().getLanguage().equals(language))
                .mapToPair(f_tweet -> new Tuple2<>(f_tweet.get().getLanguage(),1))
                .reduceByKey(Integer::sum);
        System.out.println("Total words: " + counts.count());
        //counts.saveAsTextFile(outputDir);
       /* for(String inputFile: argsList.subList(3, argsList.size())) {
            System.out.println("Processing: " + blue(inputFile));
            final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputDir);
            try {
                twitter_counter = filter.filterLanguage(language);
                System.out.println("Counted tweets in "+language+": "+twitter_counter);
            }catch (IOException exception){
                System.out.println("\33[91mException found parsing " + inputFile + "!\33[0m");
            }
        }*/
        JavaRDD<SimplifiedTweet> tweetRDD = tweets
                .map(SimplifiedTweet::fromJson)
                .filter(Optional::isPresent)
                .filter(f_tweet -> f_tweet.get().getLanguage().equals(language))
                .map(Optional::get);
        System.out.println("Total tweets: " + tweetRDD.count());
        tweetRDD.saveAsTextFile(outputDir);




        final S3Uploader uploader = new S3Uploader(bucket, language, "upf");
        System.out.println("Processing complete. Uploading to " + blue("s3://" + bucket + "/" + language + "/" + outputDir));
        uploader.upload(Collections.singletonList(outputDir));

        System.out.println("Time needed to filter " + language + " files: " + blue(Long.toString(Duration.between(beforeExecution, Instant.now()).getSeconds())) + " seconds");

    }

}

