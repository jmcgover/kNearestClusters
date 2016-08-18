package org.cplop.builders;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Collection;
import java.util.Map;

/**
 * Unit test for the Data Builder
 */
public class DataBuilderTest
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataBuilderTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( DataBuilderTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBuildAndGet() {
        DataBuilder builder;
        builder = new DataBuilder();

        Collection<Map<String, Object>> testDatapoints;
        testDatapoints = builder.getDatapoints();
        builder.printData(System.out);
    }
}

