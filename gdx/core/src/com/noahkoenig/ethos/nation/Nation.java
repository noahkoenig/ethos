package com.noahkoenig.ethos.nation;

import java.util.ArrayList;
import java.util.List;

public class Nation {
    
    public final NationType TYPE;
    private Settlement capital;
    private List<Region> regions = new ArrayList<Region>();

    Nation(NationType type) {
        this.TYPE = type;
    }

    public Settlement getCapital() { return capital; }
    
    public List<Region> getRegions() { return regions; }
}
