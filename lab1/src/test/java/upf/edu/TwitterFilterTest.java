package upf.edu;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import upf.edu.uploader.S3Uploader;

/**
 * Unit test for simple App.
 */
public class TwitterFilterTest
{


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("ANSI escape codes");
        assertTrue( true );
    }
    /*
    @Test
    public void test(){
        System.out.println("\33[94mANSI escape codes\33[0m\ns3://");
        assertTrue(false);
    }
    */

    @Test
    public void uploadTest(){
        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());
        S3Uploader client = new S3Uploader("lsds2022s3bucket", "", "upf");
        String[] file = new String[]{"src/test/resources/testfile"};
        client.upload(Arrays.asList(file));
    }
    // Place your code here
}
