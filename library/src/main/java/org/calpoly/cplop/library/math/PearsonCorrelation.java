package org.calpoly.cplop.library;

import org.apache.log4j.Logger;

import java.util.*;

public class PearsonCorrelation {
    private final static Logger logger = Logger.getLogger(CPLOP.class);
    public static final Double delta = 0.000001;
    /**
     * Average Variable Length
     */
    public static Double average(List<? extends Number> x, Integer len) {
        assert(len <= x.size());
        Double sum = 0.0;
        for (Integer i = 0; i < len; i++) {
            sum += x.get(i).doubleValue();
        }
        return sum / len;
    }
    /**
     * Covariance Variable Length
     */
    public static Double covariance(
            List<? extends Number> x, List<? extends Number> y, Integer len) {
        assert(len <= x.size());
        assert(len <= y.size());

        Double sum = 0.0;
        Double xAvg = average(x, len);
        Double yAvg = average(y, len);
        for (Integer i = 0; i < len; i++) {
            for (Integer j = 0; j < len; j++) {
                sum += (x.get(i).doubleValue() - xAvg) * (y.get(i).doubleValue() - yAvg);
            }
        }
        return sum / len;
    }
    /**
     * Standard Deviation Variable Length
     */
    public static Double standardDeviation(
            List<? extends Number> x, Integer len) {
        return Math.sqrt(covariance(x, x, len));
    }
    /**
     * Pearson Variable Length
     */
    public static Double pearsonCorrelation(
            List<? extends Number> x, List<? extends Number> y, Integer len) {
        Double covar = covariance(x, y, len);
        Double stddevX = standardDeviation(x, len);
        Double stddevY = standardDeviation(y, len);
        Double returnValue = covar / (stddevX * stddevY);
        if (Math.abs(stddevX - 0.0) < delta) {
            logger.warn(String.format(
                        "LHS length %d has standard deviation of %.3f:%s",
                        len, stddevX, x));
            returnValue = 0.0;
        }
        if (Math.abs(stddevY - 0.0) < delta) {
            logger.warn(String.format(
                        "RHS length %d has standard deviation of %.3f:%s",
                        len, stddevY, y));
            returnValue = 0.0;
        }
        return returnValue;
    }

    /** Same Size Vectors */
    /**
     * Covariance Default
     */
    public static Double covariance(
            List<? extends Number> x, List<? extends Number> y) {
        assert(x.size() == y.size());
        return covariance(x, y, x.size());
    }
    /**
     * Standard Deviation Default
     */
    public static Double standardDeviation(
            List<? extends Number> x) {
        return standardDeviation(x, x.size());
    }
    /**
     * Pearson Default
     */
    public static Double pearsonCorrelation(
            List<? extends Number> x, List<? extends Number> y) {
        assert(x.size() == y.size());
        return pearsonCorrelation(x, y, x.size());
    }
    /**
     * Average Default
     */
    public static Double average(List<? extends Number> x) {
        return average(x, x.size());
    }
}
