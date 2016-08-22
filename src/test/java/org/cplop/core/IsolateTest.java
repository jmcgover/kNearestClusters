package org.cplop.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Hashtable;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class IsolateTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IsolateTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( IsolateTest.class );
    }

    /**
     * Equality Test
     */
    public void testIsolateEquals() {
        Isolate i1 = new Isolate("testIsolateId", "testHostId", "testSpeciesName",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i1Copy = new Isolate("testIsolateId", "testHostId", "testSpeciesName",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i1Same = new Isolate("testIsolateId", "testHostId1", "testSpeciesName1",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i2 = new Isolate("testIsolateId2", "testHostId2", "testSpeciesName2",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());

        Double objectOfDifferentClass = 0.0;

        /** Test Equality of Isolates */
        assertTrue(i1.equals(i1));
        assertTrue(i1.equals(i1Copy));
        assertTrue(i1.equals(i1Same));
        /** Test Inequality of Isolates */
        assertFalse(i1.equals(i2));

        /** Test Inequality of Different Java Objects */
        assertFalse(i1.equals(objectOfDifferentClass));
        assertFalse(i2.equals(objectOfDifferentClass));
    }

    /**
     * Hashing Test
     */
    public void testIsolateHashing() {
        Isolate i1 = new Isolate("testIsolateId", "testHostId", "testSpeciesName",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i1Copy = new Isolate("testIsolateId", "testHostId", "testSpeciesName",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i1Same = new Isolate("testIsolateId", "testHostId1", "testSpeciesName1",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());
        Isolate i2 = new Isolate("testIsolateId2", "testHostId2", "testSpeciesName2",
                new Hashtable<Integer, List<Double>>(), new Hashtable<Integer, List<Double>>());

        Set<Isolate> isolateSet = new HashSet<Isolate>();

        isolateSet.add(i1);

        assertTrue(isolateSet.contains(i1));
        assertTrue(isolateSet.contains(i1Copy));
        assertTrue(isolateSet.contains(i1Same));
        assertFalse(isolateSet.contains(i2));
    }
}

