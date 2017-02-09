package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Similaritable objects
 */

public class SimilaritableStringTest
    extends SimilaritableBaseTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimilaritableStringTest(String testName)
    {
        super(testName);
        this.lower          = new SimilaritableString("banana");
        this.lowerIdentical = new SimilaritableString("banana");
        this.higher         = new SimilaritableString("pancake");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimilaritableStringTest.class );
    }
}


