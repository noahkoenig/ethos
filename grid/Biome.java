package grid;

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
}
