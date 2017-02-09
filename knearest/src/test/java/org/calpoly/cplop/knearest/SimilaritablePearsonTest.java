package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Similaritable objects
 */

public class SimilaritablePearsonTest 
    extends SimilaritableBaseTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimilaritablePearsonTest(String testName)
    {
        super(testName);
        this.lower          = new SimilaritablePearson(-0.899);
        this.lowerIdentical = new SimilaritablePearson(-0.899);
        this.higher         = new SimilaritablePearson(0.999);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimilaritablePearsonTest.class );
    }
}


