/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benny
 */
public class LockableTest {
    
    public LockableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of lock method, of class Lockable.
     */
    @Test
    public void testLock_0args() {
        System.out.println("lock");
        Lockable instance = new LockableImpl();
        instance.lock();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lock method, of class Lockable.
     */
    @Test
    public void testLock_String() {
        System.out.println("lock");
        String text = "";
        Lockable instance = new LockableImpl();
        instance.lock(text);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unlock method, of class Lockable.
     */
    @Test
    public void testUnlock() {
        System.out.println("unlock");
        Lockable instance = new LockableImpl();
        instance.unlock();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLocked method, of class Lockable.
     */
    @Test
    public void testIsLocked() {
        System.out.println("isLocked");
        Lockable instance = new LockableImpl();
        boolean expResult = false;
        boolean result = instance.isLocked();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLockText method, of class Lockable.
     */
    @Test
    public void testGetLockText() {
        System.out.println("getLockText");
        Lockable instance = new LockableImpl();
        String expResult = "";
        String result = instance.getLockText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class LockableImpl implements Lockable {

        public void lock() {
        }

        public void lock(String text) {
        }

        public void unlock() {
        }

        public boolean isLocked() {
            return false;
        }

        public String getLockText() {
            return "";
        }
    }
}
