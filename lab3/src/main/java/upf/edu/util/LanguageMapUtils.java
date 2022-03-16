package upf.edu.util;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

public class LanguageMapUtils {

    public static JavaPairRDD<String, String> buildLanguageMap(JavaRDD<String> lines) {
    // map a lista, filtrar, map to pair, distinct
        return lines.mapToPair(str -> new Tuple2<>(str.split("\t")[1], str.split("\t")[2]));// IMPLEMENT ME
    }
}
