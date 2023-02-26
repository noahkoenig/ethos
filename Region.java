import java.util.ArrayList;
import java.util.List;

public class Region {

    String name;
    List<Settlement> settlements = new ArrayList<Settlement>();
    List<Tile> tiles = new ArrayList<Tile>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Settlement> getSettlements() { return settlements; }

    public List<Tile> getTiles() { return tiles; }
}
