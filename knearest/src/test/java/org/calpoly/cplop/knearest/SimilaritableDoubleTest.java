package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Similaritable objects
 */

public class SimilaritableDoubleTest 
    extends SimilaritableBaseTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimilaritableDoubleTest(String testName)
    {
        super(testName);
        this.lower          = new SimilaritableDouble(1.0);
        this.lowerIdentical = new SimilaritableDouble(1.0);
        this.higher         = new SimilaritableDouble(1.5);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimilaritableDoubleTest.class );
    }
}

