package org.cplop.builders;

import java.util.Collections;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import org.cplop.core.Isolate;
import org.cplop.core.CPLOP;

public class Builder {
    private final static Logger logger = Logger.getLogger(Builder.class);
    private Filter filter;
    private Collection<Datapoint> rawData;
    private CPLOP library;
    private Map<String, LinkedList<Datapoint>> perIsolateData;
    public Builder(DataBuilder dataBuilder) {
        this.filter = new Filter();
        this.rawData = dataBuilder.getDatapoints();
        this.library = buildLibrary();
    }
    private CPLOP buildLibrary() {
        logger.info("Building library...");
        /**
         * Holds a table where keys are isoId's that point to a list of the
         * raw datapoints.
         */
        Map<String, LinkedList<Datapoint>> perIsolateData = this.getPerIsolateData();


        /**
         * A list of the Isolates, collapsed from the table of raw datapoints.
         */
        Collection<Isolate> isolates = new LinkedList<Isolate>();

        /**
         * Go through each raw datapoint, aggregating the Pyroprints into
         * their appropriate Isolate.
         */
        Map<Integer, List<Double>> pyroprintsITS1;
        Map<Integer, List<Double>> pyroprintsITS2;
        for (String isolateId : perIsolateData.keySet()) {
            Isolate newIsolate;

            String hostId = null;
            String hostSpeciesName = null;
            pyroprintsITS1 = new Hashtable<Integer, List<Double>>();
            pyroprintsITS2 = new Hashtable<Integer, List<Double>>();

            /**
             * Look at each pyroprint and collect according to its applied
             * (ITS) region, hashing on the pyroId.
             */
            for (Datapoint rawIsolateDatum : perIsolateData.get(isolateId)) {

                hostId                      = rawIsolateDatum.getHostId();
                hostSpeciesName             = rawIsolateDatum.getHostSpeciesName();
                Integer pyroprintId         = rawIsolateDatum.getPyroprintId();
                String appliedRegion        = rawIsolateDatum.getAppliedRegion();
                List<Double> peakHeights    = rawIsolateDatum.getPeakHeights();

                /**
                 * Skip if Pyroprint erroneous
                 */
                if (rawIsolateDatum.getIsErroneous()) {
                    logger.debug(String.format("Erroneous pyroprint %d for %s, a %s",
                            pyroprintId,
                            isolateId,
                            hostSpeciesName));
                    continue;
                }

                /** Check which region it's from. */
                if (DataBuilder.its1RegionValue.equals(appliedRegion)) {
                    pyroprintsITS1.put(pyroprintId, peakHeights);
                } else if (DataBuilder.its2RegionValue.equals(appliedRegion)) {
                    pyroprintsITS2.put(pyroprintId, peakHeights);
                } else {
                    throw new RuntimeException(
                            String.format("Region makes no sense: %s", appliedRegion));
                }
            }

            /**
             * Ensure we have at least 1 Pyroprint in each Region
             */
            if (pyroprintsITS1.size() == 0 || pyroprintsITS2.size() == 0) {
                logger.debug(String.format("Not enough pyroprints in regions for %s a %s",
                        isolateId,
                        hostSpeciesName));
                continue;
            }

            /**
             * Ensure this is a species we actually want to do stuff with.
             */
            String formattedHostSpeciesName = this.filter.filterHostSpeciesName(hostSpeciesName);
            if (formattedHostSpeciesName == null) {
                logger.debug(String.format(
                            "Ignoring '%s' for isolate %s",
                            hostSpeciesName, isolateId));
                continue;
            }
            if (!formattedHostSpeciesName.equals(hostSpeciesName)) {
                logger.debug(String.format("Changed '%s' to '%s' for isolate %s",
                            hostSpeciesName,
                            formattedHostSpeciesName,
                            isolateId));
            }

            /**
             * Build a new Isolate from the previously parsed data.
             */
            newIsolate = new Isolate(
                    isolateId,
                    hostId,
                    formattedHostSpeciesName,
                    pyroprintsITS1,
                    pyroprintsITS2);
            isolates.add(newIsolate);
        }
        logger.info("Building library finished.");
        return new CPLOP(isolates);
    }
    public CPLOP getLibrary() {
        return this.library;
    }
    public Map<String, LinkedList<Datapoint>> getPerIsolateData() {
        if (this.perIsolateData == null) {
            /**
             * Iterate through the datapoints, hashing the pyroprints of each
             * isolate, so that they can be aggregated later into an Isolate
             * object.
             */
            //Map<String, LinkedList<Datapoint>> perIsolateData;
            this.perIsolateData = new Hashtable<String, LinkedList<Datapoint>>();
            for (Datapoint point : this.rawData) {

                String isolateId = point.getIsolateId();
                LinkedList<Datapoint> rawIsolateDatum;

                if (!this.perIsolateData.containsKey(isolateId)) {
                    this.perIsolateData.put(isolateId, new LinkedList<Datapoint>());
                }
                rawIsolateDatum = this.perIsolateData.get(isolateId);
                rawIsolateDatum.add(point);
            }
            //this.perIsolateData = perIsolateData;
        }
        return Collections.synchronizedMap(
                new Hashtable<String, LinkedList<Datapoint>>(this.perIsolateData));
    }
}
