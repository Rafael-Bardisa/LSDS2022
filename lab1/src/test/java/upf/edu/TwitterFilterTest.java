package upf.edu;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.Test;
import upf.edu.filter.FileLanguageFilter;
import upf.edu.uploader.S3Uploader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * Unit test for simple App.
 */
public class TwitterFilterTest
{
    private static final String testDirectory  = "src/test/resources/";
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("\33[94mANSI escape codes\33[0m");
        assertTrue( true );
    }


        /**tests access and read files from the Lab1 folder by default.
         * Test access given static directory string
         * */
    @Test
    public void resourcePathTest() {
        // tests access and read files from the Lab1 folder by default. Test access given static directory string
        File test = new File(testDirectory + "testfile");
        assertTrue(test.exists());
    }


        /**tries to read a bad json.
         * Should not write anything to the output file
         * */
    @Test
    public void fakeJsonTest() throws IOException
    {
        // preemptively deletes all content of the output file
        new FileWriter(testDirectory + "out.txt");

        // tries to read a bad json. Should not write anything to the output file
        FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "false.json", testDirectory + "out.txt");
        jsonFilter.filterLanguage("es");
        assertEquals(0, new File(testDirectory + "out.txt").length());
    }


        /**tries to read a json tweet with the correct language.
         * Should write one simplified tweet to the output file, as there is only one english tweet
         * */
    @Test
    public void jsonTestYesLang() throws IOException
    {
        // preemptively deletes all content of the output file
        new FileWriter(testDirectory + "out.txt");

        //
        FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "test.json", testDirectory + "out.txt");
        jsonFilter.filterLanguage("en");
        assertEquals(229, new File(testDirectory + "out.txt").length());
    }


        /**tries to read a json tweet with the incorrect language.
         * Should not write anything to the output file
         * */
    @Test
    public void jsonTestNoLang() throws IOException
    {
        // preemptively deletes all content of the output file
        new FileWriter(testDirectory + "out.txt");

        // tries to read a json tweet with the incorrect language. Should not write anything to the output file
        FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "test.json", testDirectory + "out.txt");
        jsonFilter.filterLanguage("es");
        assertEquals(0, new File(testDirectory + "out.txt").length());
    }


        /**tries to read an incomplete json tweet with the correct language.
         * Should not write anything to the output file
         * */
    @Test
    public void jsonTestIncomplete() throws IOException
    {
        // preemptively deletes all content of the output file
        new FileWriter(testDirectory + "out.txt");

        //
        FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "incomplete.json", testDirectory + "out.txt");
        jsonFilter.filterLanguage("en");
        assertEquals(0, new File(testDirectory + "out.txt").length());
    }


        /**
         * tries to read a json tweet with the correct language multiple times.
         * Should write the same amount of tweets to the output file
         * */
    @Test
    public void jsonTestLoop() throws IOException
    {
        // preemptively deletes all content of the output file
        new FileWriter(testDirectory + "out.txt");

        Random rng = new Random();
        int repetitions = rng.nextInt(10);
        for (int i = 0; i < repetitions; i++){
            FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "test.json", testDirectory + "out.txt");
            jsonFilter.filterLanguage("en");
        }
        assertEquals(new File(testDirectory + "out.txt").length(), 229L * repetitions);
    }


    /**Uploads a test file to the bucket given the existing upf credentials
     * */
    @Test
    public void awsCredentials()
    {
        S3Uploader uploader = new S3Uploader("lsds2022s3bucket", "junit-tests", "upf");
        uploader.upload(Collections.singletonList(testDirectory + "testfile"));
    }


    /**Uploads a test file to a non existing bucket given the existing upf credentials. Should not crash
     * */
    @Test
    public void awsCredentialsNoBucket()
    {
        S3Uploader uploader = new S3Uploader("lisdbfljkabkawjlbfkhbfa", "junit-tests", "upf");
        uploader.upload(Collections.singletonList(testDirectory + "testfile"));
    }
}
