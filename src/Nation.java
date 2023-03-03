package src;

import java.util.ArrayList;
import java.util.List;

public class Nation {
    
    final NationType type;
    Settlement capital;
    List<Region> regions = new ArrayList<Region>();

    Nation(NationType type) {
        this.type = type;
    }

    public NationType getType() { return type; }
    
    public List<Region> getRegions() { return regions; }
}
