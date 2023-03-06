package com.noahkoenig.ethos.grid;

public enum Biome {
    
    ICE((byte) 1, (byte) 65, (byte) 90, (byte) 1, (byte) 7),
    TUNDRA((byte) 2, (byte) 55, (byte)75, (byte) 1, (byte) 6),
    BOREAL((byte) 3, (byte) 50, (byte) 60, (byte) 1, (byte) 4),
    FOREST((byte) 4, (byte) 25, (byte) 50, (byte) 1, (byte) 4),
    PRAIRIE((byte) 5, (byte) 15, (byte) 45, (byte) 1, (byte) 4),
    DESERT((byte) 6, (byte) 15, (byte) 30, (byte) 1, (byte) 6),
    SAVANNA((byte) 7, (byte) 5, (byte) 15, (byte) 1, (byte) 5),
    RAINFOREST((byte) 8, (byte) 0, (byte) 10, (byte) 1, (byte) 3),
    LAKE((byte) 9, (byte) 0, (byte) 90, (byte) 1, (byte) 7),
    OCEAN((byte) 10, (byte) 0, (byte) 90, (byte) -2, (byte) 0);

    public final static int TILE_SIZE = 16;

    public final byte ID;
    public final byte MIN_LATITUDE;
    public final byte MAX_LATITUDE;
    public final byte MIN_ELEVATION;
    public final byte MAX_ELEVATION;

    Biome(byte id, byte minLatitude, byte maxLatitude, byte minElevation, byte maxElevation) {
        this.ID = id;
        this.MIN_LATITUDE = minLatitude;
        this.MAX_LATITUDE = maxLatitude;
        this.MIN_ELEVATION = minElevation;
        this.MAX_ELEVATION = maxElevation;
    }

    public static Biome getBiomeById (byte id) {
		for (Biome biome : values()) {
            if (biome.ID == id) {
                return biome;
            }
        }
        return null;
	}
}
