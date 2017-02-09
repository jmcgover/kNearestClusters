package org.calpoly.cplop.knearest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    /**
     * Double Metric
     */
    public class SimpleDoubleMetric implements SimilarityMetric<ClassifiableDouble> {
        public Similaritable similarity(
                ClassifiableDouble a,
                ClassifiableDouble b) {
            return new SimilaritableDouble(
                    Math.abs(a.doubleValue() - b.doubleValue()));
        }
    }
    /**
     * Pearson Correlation Metric
     */
    public class SimplePearsonMetric implements 
        SimilarityMetric<ClassifiableDoubleList> {
        public Similaritable similarity(
                ClassifiableDoubleList a,
                ClassifiableDoubleList b) {
            return new SimilaritablePearson(PearsonCorrelationTest.pearsonCorrelation(a.getList(), b.getList()));
        }
    }
    /**
     * Double with String Classification
     */
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
     * Double List with String Classification
     */
    public class ClassifiableDoubleList
            implements Classifiable<String> {
        private List<Double> list;
        private String name;
        public ClassifiableDoubleList(List<Double> list, String name) {
            this.list = list;
            this.name = name;
        }
        public String getClassification() {
            return this.name;
        }
        public List<Double> getList() {
            return this.list;
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
        assertEquals(classification, "1.000");
    }
    public void testPearsonKNNClassifier() {
        ClassifiableDoubleList unknown = new ClassifiableDoubleList(getDoubleArrayList(1.0, 2.0, 3.0), "correct");
        ClassifiableDoubleList closest1 = new ClassifiableDoubleList(getDoubleArrayList(1.0, 2.0, 2.999), "correct");
        ClassifiableDoubleList closest2 = new ClassifiableDoubleList(getDoubleArrayList(1.0, 1.999, 3.0), "correct");
        ClassifiableDoubleList closest3 = new ClassifiableDoubleList(getDoubleArrayList(0.999, 2.0, 3.0), "correct");
        ClassifiableDoubleList farther1 = new ClassifiableDoubleList(getDoubleArrayList(0.999, 2.0, -3.0), "wrong");
        ClassifiableDoubleList farther2 = new ClassifiableDoubleList(getDoubleArrayList(0.999, -2.0, 3.0), "wrong");
        ClassifiableDoubleList farther3 = new ClassifiableDoubleList(getDoubleArrayList(3.0, 2.0, 1.0), "very wrong");

        List<ClassifiableDoubleList> otherStuff = new ArrayList<ClassifiableDoubleList>();
        otherStuff.add(closest1);
        otherStuff.add(closest2);
        otherStuff.add(closest3);
        otherStuff.add(farther1);
        otherStuff.add(farther2);
        otherStuff.add(farther3);

        /** Build RangeQuery */
        RangeQuery<ClassifiableDoubleList> rangeQuery = new
            LinearCachedMetricQuery<ClassifiableDoubleList>(
                    unknown,
                    otherStuff,
                    new SimplePearsonMetric());
        /** Build Classifier */
        KNearestClassifier<ClassifiableDoubleList, String> classifier =
            new SingleKNNListClassifier<ClassifiableDoubleList, String>(rangeQuery);
        String classification = 
            classifier.classifyInstance(unknown, 4, null);
        assertEquals(classification, "correct");
    }
    private ArrayList<Double> getDoubleArrayList(Double... values) {
        return new ArrayList<Double>(Arrays.asList(values));
    }
}
