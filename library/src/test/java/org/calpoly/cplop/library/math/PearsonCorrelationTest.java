package org.calpoly.cplop.library;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class PearsonCorrelationTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PearsonCorrelationTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( PearsonCorrelationTest.class );
    }

    /**
     * Equality Test
     */
    public void testPearsonCorrelationEquals() {
        List<Double> d1 = Arrays.asList(2.0, 2.0, 2.0, 2.0);
        List<Double> d2 = Arrays.asList(2.0, 2.0, 2.0, 4.0);
        assertEquals(1.0,
                PearsonCorrelation.pearsonCorrelation(d2, d2),
                PearsonCorrelation.delta);
        assertEquals(2.0, PearsonCorrelation.average(d1), PearsonCorrelation.delta);
        assertEquals(2.0, PearsonCorrelation.average(d2, 3), PearsonCorrelation.delta);
        assertEquals(0.0,
                PearsonCorrelation.pearsonCorrelation(d1, d2, 3),
                PearsonCorrelation.delta);
    }
}

