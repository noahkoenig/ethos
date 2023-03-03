package grid;

public enum Biome {
    
    ICE("I", 65, 90, (byte) -4, (byte) 7),
    TUNDRA("T", 55, 75, (byte) -4, (byte) 6),
    BOREAL("B", 50, 60, (byte) -4, (byte) 4),
    FOREST("F", 25, 50, (byte) -4, (byte) 4),
    PRAIRIE("P", 15, 45, (byte) -4, (byte) 4),
    DESERT("D", 15, 30, (byte) -4, (byte) 6),
    SAVANNA("S", 5, 15, (byte) -4, (byte) 5),
    RAINFOREST("R", 0, 10, (byte) -4, (byte) 3),
    LAKE("L", 0, 90, (byte) -4, (byte) 7),
    OCEAN(".", 0, 90, (byte) -4, (byte) 0);

    private String icon;
    private int minLatitude;
    private int maxLatitude;
    private byte minElevation;
    private byte maxElevation;

    Biome(String icon, int minLatitude, int maxLatitude, byte minElevation, byte maxElevation) {
        this.icon = icon;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;
    }

    public String getIcon() { return icon; }

    public int getMinLatitude() { return minLatitude; }

    public int getMaxLatitude() { return maxLatitude; }

    public byte getMinElevation() { return minElevation; }

    public byte getMaxElevation() { return maxElevation; }
}
