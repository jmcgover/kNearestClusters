package org.calpoly.cplop.library;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.cplop.builders.*;

/**
 * Unit test for simple App.
 */
public class CPLOPTest
    extends TestCase {

    private DataBuilder dataBuilder;
    private Builder     cplopBuilder;
    private CPLOP       cplop;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CPLOPTest( String testName ) {
        super( testName );
        this.dataBuilder = new DataBuilder();
        this.cplopBuilder = new Builder(dataBuilder);
        this.cplop = cplopBuilder.getLibrary();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( CPLOPTest.class );
    }

    /**
     * Statistics Test
     */
    public void testCPLOPStats() {
        Collection<String> species = cplop.getHostSpecies();
        Collection<Isolate> isolates = cplop.getIsolates();
        Map<String, Integer> speciesCounts = cplop.getHostSpeciesIsolateCounts();

        Integer numberOfIsolates = 0;
        for (String key : species) {
            numberOfIsolates += speciesCounts.get(key);
        }

        assertTrue(numberOfIsolates == isolates.size());
        cplop.logStats();
    }
}


