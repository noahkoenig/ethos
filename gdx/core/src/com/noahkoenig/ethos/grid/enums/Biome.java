package com.noahkoenig.ethos.grid.enums;

public enum Biome {

    POLAR(          1,    73,   90,   1,    7),
    TUNDRA(         2,    62,   79,   1,    6),
    BOREAL(         3,    51,   68,   1,    4),
    FOREST(         4,    40,   57,   1,    4),
    STEPPE(         5,    29,   46,   1,    4),
    MEDITERRANEAN(  6,    29,   46,   1,    4),
    DESERT(         7,    18,   35,   1,    6),
    SAVANNA(        8,    7,    24,   1,    5),
    RAINFOREST(     9,    0,    13,   1,    3),
    LAKE(           10,   0,    90,   1,    7),
    OCEAN(          11,   0,    90,   -2,   0);

    public final int ID;
    public final int MIN_LATITUDE;
    public final int MAX_LATITUDE;
    public final int MIN_ELEVATION;
    public final int MAX_ELEVATION;

    Biome(int id, int minLatitude, int maxLatitude, int minElevation, int maxElevation) {
        this.ID = id;
        this.MIN_LATITUDE = minLatitude;
        this.MAX_LATITUDE = maxLatitude;
        this.MIN_ELEVATION = minElevation;
        this.MAX_ELEVATION = maxElevation;
    }

    public static Biome getBiomeById (int id) {
        return values()[id - 1]; // TODO we should let the IDs start from 0
	}
}
