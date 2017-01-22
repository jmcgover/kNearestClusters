package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Similaritable objects
 */

public abstract class SimilaritableBaseTest
    extends TestCase
{
    protected Similaritable lower;
    protected Similaritable lowerIdentical;
    protected Similaritable higher;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimilaritableBaseTest(String testName)
    {
        super(testName);
    }

    /**
     * String test.
     */
    public void testEquals()
    {
        assertEquals(lower, lower);
        assertEquals(lower, lowerIdentical);
        assertEquals(lowerIdentical, lower);
        assertFalse(lower.equals(higher));
        assertFalse(higher.equals(lower));
    }
    /**
     * Ensures a total ordering
     */
    public void testLessThan() {
        assertTrue(lower.compareTo(higher) < 0);
        assertFalse(higher.compareTo(lower) < 0);
    }
    public void testGreaterThan() {
        assertTrue(higher.compareTo(lower) > 0);
        assertFalse(lower.compareTo(higher) > 0);
    }
    public void testCompareToEquals() {
        assertTrue(lower.compareTo(lower) == 0);
        assertTrue(lower.compareTo(lowerIdentical) == 0);
        assertTrue(lowerIdentical.compareTo(lower) == 0);
        assertFalse(lower.compareTo(higher) == 0);
        assertFalse(higher.compareTo(lower) == 0);
    }
}
