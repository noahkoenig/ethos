package com.noahkoenig.ethos.grid;

public enum Biome {
    
    ICE((byte) 1, (byte) 65, (byte) 90, (byte) -4, (byte) 7),
    TUNDRA((byte) 2, (byte) 55, (byte)75, (byte) -4, (byte) 6),
    BOREAL((byte) 3, (byte) 50, (byte) 60, (byte) -4, (byte) 4),
    FOREST((byte) 4, (byte) 25, (byte) 50, (byte) -4, (byte) 4),
    PRAIRIE((byte) 5, (byte) 15, (byte) 45, (byte) -4, (byte) 4),
    DESERT((byte) 6, (byte) 15, (byte) 30, (byte) -4, (byte) 6),
    SAVANNA((byte) 7, (byte) 5, (byte) 15, (byte) -4, (byte) 5),
    RAINFOREST((byte) 8, (byte) 0, (byte) 10, (byte) -4, (byte) 3),
    LAKE((byte) 9, (byte) 0, (byte) 90, (byte) -4, (byte) 7),
    OCEAN((byte) 10, (byte) 0, (byte) 90, (byte) -4, (byte) 0);

    public static final int TILE_SIZE = 16;

    private byte id;
    private byte minLatitude;
    private byte maxLatitude;
    private byte minElevation;
    private byte maxElevation;

    Biome(byte id, byte minLatitude, byte maxLatitude, byte minElevation, byte maxElevation) {
        this.id = id;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;
    }

    public byte getId() { return id; }

    public byte getMinLatitude() { return minLatitude; }

    public byte getMaxLatitude() { return maxLatitude; }

    public byte getMinElevation() { return minElevation; }

    public byte getMaxElevation() { return maxElevation; }

    public static Biome getBiomeById (byte id) {
		for (Biome biome : values()) {
            if (biome.getId() == id) {
                return biome;
            }
        }
        return null;
	}
}
