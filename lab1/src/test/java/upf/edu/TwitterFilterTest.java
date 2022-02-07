package upf.edu;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
    // Place your code here

    @Test
    public void resourcePathTest() throws IOException
    {
        // tests access and read files from the Lab1 folder by default. Test access given static directory string
        File test = new File(testDirectory + "testfile");
        assertTrue(test.exists());
    }
}
