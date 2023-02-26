import java.util.ArrayList;
import java.util.List;

public class Nation {
    
    final NationType type;
    List<State> states = new ArrayList<State>();

    Nation(NationType type) {
        this.type = type;
    }

    public NationType getType() { return type; }
    public List<State> getStates() { return states; }
}
