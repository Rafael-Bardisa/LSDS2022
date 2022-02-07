package upf.edu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import upf.edu.filter.FileLanguageFilter;
import upf.edu.uploader.S3Uploader;
import static org.junit.Assert.assertTrue;
import org.junit.Test;



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
        System.out.println("ANSI escape codes");
        assertTrue( true );
    }


        /**tests access and read files from the Lab1 folder by default.
         * Test access given static directory string
         * */
    @Test
    public void resourcePathTest() throws IOException
    {
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
        assertTrue(new File(testDirectory + "out.txt").length() == 0);
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
        assertTrue(new File(testDirectory + "out.txt").length() == 229);
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
        assertTrue(new File(testDirectory + "out.txt").length() == 0);
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
        assertTrue(new File(testDirectory + "out.txt").length() == 0);
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
        int repetitions = rng.nextInt()%10;
        for (int i = 0; i < repetitions; i++){
            FileLanguageFilter jsonFilter = new FileLanguageFilter(testDirectory + "test.json", testDirectory + "out.txt");
            jsonFilter.filterLanguage("en");
        }
        assertTrue(new File(testDirectory + "out.txt").length() == 229L * repetitions);
    }
}
