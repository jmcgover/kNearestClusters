package org.calpoly.cplop.library;

import java.util.*;

public class Filter {
    private Map<String, String> hostSpeciesReplacement;
    private Set<String> hostSpeciesToIgnore;
    public Filter() {
        this.hostSpeciesReplacement = getReplacements();
        this.hostSpeciesToIgnore = getIgnored();
    }
    private Map<String, String> getReplacements() {
        Map<String, String> replacements = new Hashtable<String, String>();
        replacements.put("cougar", "Cougar");
        replacements.put("Cw", "Cow");
        replacements.put("Dg", "Dog");
        replacements.put("Hu", "Human");
        replacements.put("Human UTI", "Human");
        replacements.put("Neonatal Human", "Human");
        replacements.put("Barn Owl", "Owl");
        replacements.put("Long-eared Owl", "Owl");
        replacements.put("orangutan", "Orangutan");
        replacements.put("Pig/Swine", "Pig");
        replacements.put("Wild Pig", "Pig");
        replacements.put("Bond tail Pigeon", "Pigeon");
        replacements.put("Red Wind Blackbird", "Red-Winged Blackbird");
        return replacements;
    }
    private Set<String> getIgnored() {
        Set<String> ignored = new HashSet<String>();
        ignored.add("SLO Creek Water");
        ignored.add("Pennington Creek Water");
        ignored.add("Pacific Ocean Water");
        ignored.add("Hu and Cw");
        ignored.add("Hu and Dg");
        ignored.add("Cw and Dg");
        ignored.add("Hu, Cw and Dg");
        return ignored;
    }
    public String filterHostSpeciesName(String hostSpecies) {
        if (null == hostSpecies) {
            return null;
        }
        String returnHostSpecies = hostSpecies.trim();
        /**
         * Format the Species Name
         */
        if (hostSpeciesReplacement.containsKey(returnHostSpecies)) {
            returnHostSpecies = hostSpeciesReplacement.get(hostSpecies);
        }
        /**
         * Check if, after formatting, the species needs to be removed
         */
        if (hostSpeciesToIgnore.contains(returnHostSpecies)) {
            return null;
        }
        return returnHostSpecies;
    }
}

