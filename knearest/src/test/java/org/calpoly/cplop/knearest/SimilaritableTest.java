package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Similaritable objects
 */

public class SimilaritableTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimilaritableTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimilaritableTest.class );
    }

    /**
     * String test.
     */
    public void testSimilaritableString()
    {
        Similaritable first = new SimilaritableString("banana");
        Similaritable firstIdentical = new SimilaritableString("banana");
        Similaritable second = new SimilaritableString("pancake");

        assertEquals(first, firstIdentical);
        assertFalse(first.equals(second));
        assertTrue(first.compareTo(second) < 0);
        assertTrue(second.compareTo(first) > 0);
        assertTrue(first.compareTo(firstIdentical) == 0);
        assertTrue(firstIdentical.compareTo(first) == 0);
    }
    /**
     * Double test.
     */
    public void testSimilaritableDouble()
    {
        Similaritable first = new SimilaritableDouble(1.0);
        Similaritable firstIdentical = new SimilaritableDouble(1.0);
        Similaritable second = new SimilaritableDouble(1.5);

        assertEquals(first, firstIdentical);
        assertFalse(first.equals(second));
        assertTrue(first.compareTo(second) < 0);
        assertTrue(second.compareTo(first) > 0);
        assertTrue(first.compareTo(firstIdentical) == 0);
        assertTrue(firstIdentical.compareTo(first) == 0);
    }
}
