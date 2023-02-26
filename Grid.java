import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Grid {

    static Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    public static void main(String args[]) {
        Grid grid = new Grid();
        grid.generateGrid(30, 20, 66);
        Grid.printGrid();
    }

    public void generateGrid(int width, int height, int waterPercentage) {
        Random random = new Random();
        for(int x = 0; x < height; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < width; y++) {
                int latitude = (int) Math.round(90 - (x * 180 / (height - 1)));
                Biome biome = random.nextInt(100) > waterPercentage
                    ? Biome.getRandomLandBiomeByLatitude(latitude, 10) : Biome.WATER;
                row.add(new Tile(1, biome, Terrain.FLAT));
            }
            tiles.put(x, row);
        }
    }

    public static void printGrid() {
        for(int i = 0; i < tiles.size(); i++) {
            String row = isEven(i) ? " " : "";
            for(Tile tile : tiles.get(i)) {
                row += tile.getBiome().getIcon() + " ";
            }
            System.out.println(row);
        }
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public Map<Integer, List<Tile>> getTiles() { return tiles; }
}