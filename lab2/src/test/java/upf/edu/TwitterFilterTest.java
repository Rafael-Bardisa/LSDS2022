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
        int nTweets = jsonFilter.filterLanguage("es");
        assertEquals(0, new File(testDirectory + "out.txt").length());
        assertEquals(0, nTweets);
    }
}
