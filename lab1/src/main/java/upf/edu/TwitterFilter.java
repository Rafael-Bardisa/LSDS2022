package upf.edu;

import upf.edu.filter.FileLanguageFilter;
import upf.edu.filter.FilterException;
import upf.edu.uploader.S3Uploader;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TwitterFilter {

    private static String blue(String inputString) {
        return "\33[94m" + inputString + "\33[0m";
    }

    public static void main( String[] args ) throws IOException {
        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);
        System.out.println("Language: " + blue(language) + ".\nOutput file: " + blue(outputFile) + ".\nDestination bucket: " + blue(bucket) + "\n");
        for(String inputFile: argsList.subList(3, argsList.size())) {
            System.out.println("Processing: \33[94m" + inputFile + "\33[0m");
            final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
            try {
                filter.filterLanguage(language);
            }catch (IOException exception){
                System.out.println("\33[91m el programa petó\33[0m");
                continue;   //TODO esto no se queda asi
            }
        }

        //final S3Uploader uploader = new S3Uploader(bucket, "prefix", "default");
        //uploader.upload(Arrays.asList(outputFile));

    }


}
