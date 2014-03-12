package de.dezibel.control;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tobias
 */
public class HashGeneratorTest {
    
    public HashGeneratorTest() {
    }

    /**
     * Test of hash method, of class HashGenerator.
     * Used http://online-code-generator.com/md5-hash-with-optional-salt.php
     * as comparison to find results
     */
    @Test
    public void testHash() {
        System.out.println("hash");
        String s = "password";
        HashGenerator instance = new HashGenerator();
        String expResult = "8f17a383f45966e59c6521ac6b52bba3";
        String result = instance.hash(s);
        assertEquals(expResult, result);
        
        s = "";
        instance = new HashGenerator();
        expResult = "ff51886f3b3a048504eec6efe30f8045";
        result = instance.hash(s);
        assertEquals(expResult, result);
    }
    
}
