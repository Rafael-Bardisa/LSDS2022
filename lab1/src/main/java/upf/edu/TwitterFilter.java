package upf.edu;

import upf.edu.filter.FileLanguageFilter;
import upf.edu.filter.FilterException;
import upf.edu.uploader.S3Uploader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TwitterFilter {
    public static void main( String[] args ) throws IOException {
        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);
        System.out.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);
        for(String inputFile: argsList.subList(3, argsList.size())) {
            System.out.println("Processing: " + inputFile);
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
