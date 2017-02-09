package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.ArrayList;

/**
 * Unit test for simple SingleKNNListClassifier.
 */
public class SingleKNNListClassifierTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SingleKNNListClassifierTest( String testName )
    {
        super( testName );
    }
    public class SimpleDoubleMetric implements SimilarityMetric<ClassifiableDouble> {
        public Similaritable similarity(
                ClassifiableDouble a,
                ClassifiableDouble b) {
            return new SimilaritableDouble(
                    Math.abs(a.doubleValue() - b.doubleValue()));
        }
    }
    public class ClassifiableDouble
            implements Classifiable<String> {
        private Double value;
        public ClassifiableDouble(Double value) {
            this.value = value;
        }
        public String getClassification() {
            return String.format("%.3f", this.value.doubleValue());
        }
        public double doubleValue() {
            return this.value.doubleValue();
        }
        public int hashCode() {
            return this.value.hashCode();
        }
    }
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SingleKNNListClassifierTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSingleKNNListClassifier()
    {

        /**
         * Build a center
         */
        ClassifiableDouble center = new ClassifiableDouble(0.0);
        /**
         * Make some other Doubles
         */
        List<ClassifiableDouble> otherStuff = new ArrayList<ClassifiableDouble>();
        otherStuff.add(new ClassifiableDouble(1.0)); /**Produces the smallest similarity*/
        otherStuff.add(new ClassifiableDouble(1.0));
        otherStuff.add(new ClassifiableDouble(1.0));
        otherStuff.add(new ClassifiableDouble(1.0));
        otherStuff.add(new ClassifiableDouble(2.0)); /**Produces the second smallest similarity*/
        otherStuff.add(new ClassifiableDouble(2.0));
        otherStuff.add(new ClassifiableDouble(2.0));
        otherStuff.add(new ClassifiableDouble(3.0)); /**Produces the third smallest similarity*/
        otherStuff.add(new ClassifiableDouble(3.0));
        otherStuff.add(new ClassifiableDouble(3.0));
        otherStuff.add(new ClassifiableDouble(3.0));

        /** Build RangeQuery */
        RangeQuery<ClassifiableDouble> rangeQuery = new
            LinearCachedMetricQuery<ClassifiableDouble>(
                    center,
                    otherStuff,
                    new SimpleDoubleMetric());
        /** Build Classifier */
        KNearestClassifier<ClassifiableDouble, String> classifier =
            new SingleKNNListClassifier<ClassifiableDouble, String>(rangeQuery);
        String classification = 
            classifier.classifyInstance(center, 4, null);
        System.out.println("FUUUCK " + classification);
        assertEquals(classification, "1.000");
    }
}
