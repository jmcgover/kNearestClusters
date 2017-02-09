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
        Similaritable closest = new SimilaritablePearson(0.999);
        Similaritable closestCopy = new SimilaritablePearson(0.999);
        Similaritable farthest = new SimilaritablePearson(-0.999);
        this.lower          = closest;
        this.lowerIdentical = closestCopy;
        this.higher         = farthest;
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimilaritablePearsonTest.class );
    }
}


