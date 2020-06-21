package com.dredom.java9;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    boolean go = true;
    
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() {
        Assume.assumeTrue(go);
        Assert.assertTrue(true);
    }
}
