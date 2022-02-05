package upf.edu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
    @Test
    public void test(){
        System.out.println("\33[94mANSI escape codes\33[0m");
        assertTrue(false);
    }
    // Place your code here
}
