package org.cplop.builders;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Ignore;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.cplop.core.CPLOP;

/**
 * Unit test for the  Builder
 */
@Ignore("CPLOPTest.java will test these")
public class BuilderTest
    extends TestCase {

    private DataBuilder dataBuilder;
    private Builder cplopBuilder;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BuilderTest( String testName ) {
        super( testName );
        this.dataBuilder = new DataBuilder();
        this.cplopBuilder = new Builder(dataBuilder);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( BuilderTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testHostIdConsistency() {
        Map<String, LinkedList<Datapoint>> perIsolateData;
        perIsolateData = this.cplopBuilder.getPerIsolateData();

        for (String isolateId : perIsolateData.keySet()) {
            String hostId = null;
            for (Datapoint d : perIsolateData.get(isolateId)) {
                /**
                 * Host ID Consistency Check
                 */
                if (hostId != null) {
                    assertTrue(hostId.equals(d.getHostId()));
                }
                hostId = d.getHostId();
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSpeciesNameConsistency() {
        Map<String, LinkedList<Datapoint>> perIsolateData;
        perIsolateData = this.cplopBuilder.getPerIsolateData();

        for (String isolateId : perIsolateData.keySet()) {
            String hostSpeciesName = null;
            for (Datapoint d : perIsolateData.get(isolateId)) {
                /**
                 * Host Species Name Consistency Check
                 */
                if (hostSpeciesName != null) {
                    assertTrue(hostSpeciesName.equals(d.getHostSpeciesName()));
                }
                hostSpeciesName = d.getHostSpeciesName();

            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testAppliedRegionConsistency() {
        Map<String, LinkedList<Datapoint>> perIsolateData;
        perIsolateData = this.cplopBuilder.getPerIsolateData();

        for (String isolateId : perIsolateData.keySet()) {
            String hostSpeciesName = null;
            for (Datapoint d : perIsolateData.get(isolateId)) {
                String appliedRegion = d.getAppliedRegion();
                assertTrue(DataBuilder.its1RegionValue.equals(appliedRegion)
                        || DataBuilder.its2RegionValue.equals(appliedRegion));
            }
        }
    }
}


