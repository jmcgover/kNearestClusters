package org.calpoly.cplop.library;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Ignore;

import java.util.Collection;
import java.util.Map;

/**
 * Unit test for the Data Builder
 */
@Ignore("BuilderTest.java takes care of these tests")
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

        Collection<Datapoint> testDatapoints;
        testDatapoints = builder.getDatapoints();
        //builder.printData(System.out);
    }
}

