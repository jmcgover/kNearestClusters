package org.cplop.builders;

import java.util.*;

public class Filter {
    private Map<String, String> hostSpeciesReplacement;
    private Set<String> hostSpeciesToRemove;
    public Filter() {
        this.hostSpeciesReplacement = new Hashtable<String, String>();
        this.hostSpeciesToRemove = new HashSet<String>();
    }
    public String filterHostSpeciesName(String hostSpecies) {
        if (hostSpeciesToRemove.contains(hostSpecies)) {
            return null;
        }
        if (hostSpeciesReplacement.containsKey(hostSpecies)) {
            return hostSpeciesReplacement.get(hostSpecies);
        }
        return hostSpecies;
    }
}

