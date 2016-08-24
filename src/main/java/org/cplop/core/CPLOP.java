package org.cplop.core;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.*;

public class CPLOP {

    private final static Logger logger = Logger.getLogger(CPLOP.class);

    private Collection<Isolate> isolates;

    private Collection<String> hostSpecies;
    private Collection<String> hosts;

    private Map<Integer, List<Double>> pyroprintsITS1;
    private Map<Integer, List<Double>> pyroprintsITS2;

    private Map<String, Integer> perSpeciesIsolates;
    private Map<String, Integer> perHostIsolates;
    private Map<String, Set<String>> perSpeciesHosts;
    public CPLOP(Collection<Isolate> isolates) {
        this.isolates = Collections.synchronizedSet(new HashSet<Isolate>(isolates));
        initializeStats();
    }
    private void initializeStats() {
        this.hostSpecies    = Collections.synchronizedSet(new HashSet<String>());
        this.hosts          = Collections.synchronizedSet(new HashSet<String>());
        this.pyroprintsITS1 = new Hashtable<Integer, List<Double>>();
        this.pyroprintsITS2 = new Hashtable<Integer, List<Double>>();

        this.perSpeciesIsolates     = new Hashtable<String, Integer>();
        this.perHostIsolates        = new Hashtable<String, Integer>();
        this.perSpeciesHosts        = new Hashtable<String, Set<String>>();

        for (Isolate i : isolates) {
            this.hostSpecies.add(i.getHostSpeciesName());
            this.hosts.add(i.getHostId());
            this.pyroprintsITS1.putAll(i.getITS1Pyroprints());
            this.pyroprintsITS2.putAll(i.getITS2Pyroprints());

            /** Count Stuff */
            incrementCounter(this.perSpeciesIsolates, i.getHostSpeciesName());
            incrementCounter(this.perHostIsolates, i.getHostId());
            if (!this.perSpeciesHosts.containsKey(i.getHostSpeciesName())) {
                this.perSpeciesHosts.put(i.getHostSpeciesName(), new HashSet<String>());
            }
            this.perSpeciesHosts.get(i.getHostSpeciesName()).add(i.getHostId());
        }
    }
    public void logStats() {
        /**
         * Build Sorted Maps
         */
        Map<String, Integer> speciesCountsByCount = new LinkedHashMap<String, Integer>();
        Map<String, Integer> speciesCountsByName = new LinkedHashMap<String, Integer>();

        /**
         * Iterate through in a sorted fashion, adding to the sorted maps above.
         */
        Stream<Map.Entry<String, Integer>> byCountStream
            = this.perSpeciesIsolates.entrySet().stream();
        byCountStream.sorted(
                Map.Entry.comparingByValue()
                ).forEachOrdered(
                    e -> speciesCountsByCount.put(e.getKey(), e.getValue()));

        Stream<Map.Entry<String, Integer>> byNameStream
            = this.perSpeciesIsolates.entrySet().stream();
        byNameStream.sorted(
                Map.Entry.comparingByKey()
                ).forEachOrdered(
                    e -> speciesCountsByName.put(e.getKey(), e.getValue()));

        /**
         * Print Number of Isolates per Species Sorted by Name
         */
        for (String key : speciesCountsByName.keySet()) {
            logger.info(String.format("'%s': %d Isolates", key, speciesCountsByName.get(key)));
        }

        /**
         * Print Number of Isolates per Species Sorted by Count
         */
        for (String key : speciesCountsByCount.keySet()) {
            logger.info(String.format("%4d: '%s' Isolates", speciesCountsByCount.get(key), key));
        }
        /**
         * Print Number of Hosts per Species Sorted by Name
         */
        for (String key : new TreeSet<String>(this.hostSpecies)) {
            logger.info(String.format("'%s': %d Hosts", key, perSpeciesHosts.get(key).size()));
        }
        logger.info(String.format("Number of Species: %d",    this.hostSpecies.size()));
        logger.info(String.format("Number of Hosts: %d",      this.hosts.size()));
        logger.info(String.format("Number of Isolates: %d",   this.isolates.size()));
    }

    /**
     * Property Getters
     */
    public Collection<Isolate> getIsolates() {
        return this.isolates;
    }
    public Collection<String> getHosts() {
        return this.hosts;
    }
    public Collection<String> getHostSpecies() {
        return this.hostSpecies;
    }
    public Map<Integer, List<Double>> getITS1Pyroprints() {
        return this.pyroprintsITS1;
    }
    public Map<Integer, List<Double>> getITS2Pyroprints() {
        return this.pyroprintsITS2;
    }
    public Map<String, Set<String>> getHostSpeciesHosts() {
        return this.perSpeciesHosts;
    }

    /**
     * Counters
     */
    public Map<String, Integer> getHostIsolateCounts() {
        return this.perHostIsolates;
    }
    public Map<String, Integer> getHostSpeciesIsolateCounts() {
        return this.perSpeciesIsolates;
    }
    /**
     * Utility function to help with counting objects
     */
    private boolean incrementCounter(Map<String, Integer> counter, String key) {
        return incrementCounterBy(counter, key, 1);
    }
    private boolean incrementCounterBy(Map<String, Integer> counter, String key, Integer num) {
        if (!counter.containsKey(key)) {
            counter.put(key, num);
            return false;
        } else {
            counter.put(key, counter.get(key) + num);
            return true;
        }
    }
}
