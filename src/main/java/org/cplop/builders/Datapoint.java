package org.cplop.builders;

import java.util.*;

public class Datapoint {
    private String          hostSpeciesName;
    private String          hostId;
    private String          isolateId;
    private Integer         pyroprintId;
    private String          appliedRegion;
    private Boolean         isErroneous;
    private List<Double>    peakHeights;
    public Datapoint(Datapoint d) {
        this.hostSpeciesName    = d.hostSpeciesName;
        this.hostId             = d.hostId;
        this.isolateId          = d.isolateId;
        this.pyroprintId        = d.pyroprintId;
        this.appliedRegion      = d.appliedRegion;
        this.isErroneous        = d.isErroneous;
        this.peakHeights        = Collections.synchronizedList(new ArrayList<Double>(d.peakHeights));
    }
    public Datapoint(
            String          hostSpeciesName,
            String          hostId,
            String          isolateId,
            Integer         pyroprintId,
            String          appliedRegion,
            Boolean         isErroneous,
            List<Double>    peakHeights) {
        this.hostSpeciesName    = hostSpeciesName;
        this.hostId             = hostId;
        this.isolateId          = isolateId;
        this.pyroprintId        = pyroprintId;
        this.appliedRegion      = appliedRegion;
        this.isErroneous        = isErroneous;
        this.peakHeights        = peakHeights;
    }
    public String getHostSpeciesName(){
        return this.hostSpeciesName;
    }
    public String getHostId(){
        return this.hostId;
    }
    public String getIsolateId(){
        return this.isolateId;
    }
    public Integer getPyroprintId(){
        return this.pyroprintId;
    }
    public String getAppliedRegion(){
        return this.appliedRegion;
    }
    public Boolean getIsErroneous(){
        return this.isErroneous;
    }
    public List<Double> getPeakHeights(){
        return this.peakHeights;
    }
}
