import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Biome {
    ICE("I", 65, 90),
    TUNDRA("T", 55, 75),
    BOREAL("B", 50, 60),
    FOREST("F", 25, 50),
    PRAIRIE("P", 15, 45),
    DESERT("D", 15, 30),
    SAVANNA("S", 5, 15),
    RAINFOREST("R", 0, 10),
    WATER(".", 0, 90);

    String icon;
    int minLatitude;
    int maxLatitude;

    Biome(String icon, int minLatitude, int maxLatitude) {
        this.icon = icon;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
    }

    public String getIcon() { return icon; }
    public int getMinLatitude() { return minLatitude; }
    public int getMaxLatitude() { return maxLatitude; }

    public static Biome getRandomLandBiome() {
        Random random = new Random();
        return values()[random.nextInt(values().length - 1)];
    }

    public static Biome getRandomLandBiomeByLatitude(int latitude, int tolerance) {
        latitude = Math.abs(latitude);
        List<Biome> biomes = new ArrayList<Biome>();
        for (int i = 0; i < values().length - 1; i++) {
            Biome biome = values()[i];
            if (biome.getMinLatitude() - tolerance <= latitude && latitude <= biome.getMaxLatitude() + tolerance) {
                biomes.add(biome);
            }
        }
        Random random = new Random();
        return biomes.get(random.nextInt(biomes.size()));
    }
}
