package com.noahkoenig.ethos.gamemap.enums;

public enum Biome {

    /**
     * The polar biome refers to the areas around the North and South poles, characterized by frigid temperatures, strong winds, and little to no vegetation.
     */
    POLAR(1, false, 73, 90, 0, 6),
    /**
     * The tundra biome is a cold and treeless landscape found in the Arctic and alpine regions of the world.
     * The soil is permanently frozen, and there is little rainfall. Plants that are adapted to the tundra include mosses, lichens, and shrubs.
     */
    TUNDRA(2, false, 62, 79, 0, 5),
    /**
     * The boreal forest biome, also known as the taiga, is a vast forested region found in the subarctic regions of the world.
     * The climate is cold, with long winters and short summers, and the vegetation consists mainly of coniferous trees such as spruce, pine, and fir.
     */
    BOREAL(3, false, 51, 68, 0, 3),
    /**
     * The forest biome includes a variety of forest types found in different regions of the world, including tropical rainforests, temperate deciduous forests,
     * and coniferous forests. Forests are characterized by a high density of trees and a diverse array of plant and animal species.
     */
    FOREST(4, false, 40, 57, 0, 3),
    /**
     * The steppe biome is a large, flat grassland region found in the temperate regions of the world.
     * The climate is dry, with little rainfall, and the vegetation consists mainly of grasses and a few scattered trees.
     */
    STEPPE(5, false, 29, 46, 0, 3),
    /**
     * The mediterranean biome is a region of woodlands and shrublands surrounding the Mediterranean Sea.
     * It has a warm and dry climate with mild, wet winters and hot, dry summers.
     * This biome is characterized by plants such as olive trees, grape vines, and lavender.
     */
    MEDITERRANEAN(6, false, 29, 46, 0, 3),
    /**
     * The desert biome is a region characterized by extreme dryness, with little to no rainfall and hot temperatures during the day and cold temperatures at night.
     * The vegetation in deserts is sparse, with cacti, succulents, and shrubs being common.
     */
    DESERT(7, false, 16, 35, 0, 5),
    /**
     * The savanna biome is a tropical grassland region characterized by a wet season and a dry season.
     * The vegetation in this biome consists of tall grasses, scattered trees, and shrubs.
     */
    SAVANNA(8, false, 7, 22, 0, 4),
    /**
     * The rainforest biome is a lush, tropical region characterized by high levels of rainfall and warm temperatures year-round.
     * The vegetation in this biome is dense and diverse, with tall trees, vines, and epiphytes.
     */
    RAINFOREST(9, false, 0, 13, 0, 2),
    /**
     * The lake biome refers to freshwater bodies that are surrounded by land, such as lakes, ponds, and wetlands.
     * These biomes are characterized by their still or slow-moving water and the variety of aquatic plants and animals that live within them.
     */
    LAKE(10, true,0, 90, 0, 6),
    /**
     * The ocean biome is the largest biome on Earth, covering over 70% of the planet's surface.
     * It is characterized by saltwater, high pressure, and low temperatures in the deep sea.
     * The ocean is home to a diverse array of organisms, from microscopic plankton to large whales,
     * and plays a critical role in regulating the Earth's climate and providing resources for human society.
     */
    OCEAN(11, true, 0, 90, -3, -1);

    public final int ID;
    public final boolean IS_WATER;
    public final int MIN_LATITUDE;
    public final int MAX_LATITUDE;
    public final int MIN_ELEVATION;
    public final int MAX_ELEVATION;

    Biome(int id, boolean isWater, int minLatitude, int maxLatitude, int minElevation, int maxElevation) {
        this.ID = id;
        this.IS_WATER = isWater;
        this.MIN_LATITUDE = minLatitude;
        this.MAX_LATITUDE = maxLatitude;
        this.MIN_ELEVATION = minElevation;
        this.MAX_ELEVATION = maxElevation;
    }

    /**
     * @param biome
     * @return A sorted Biome Array with preferred replacements if the given Biome is not allowed for the Elevation.
     * @throws IllegalArgumentException
     */
    public static Biome[] getReplacementsByBiome (Biome biome) throws IllegalArgumentException {
        switch (biome) {
            case TUNDRA:
                Biome[] tundraReplacements = {POLAR};
                return tundraReplacements;
            case BOREAL:
                Biome[] borealReplacements = {FOREST, TUNDRA, POLAR};
                return borealReplacements;
            case FOREST:
                Biome[] forestReplacements = {STEPPE, BOREAL, TUNDRA, POLAR};
                return forestReplacements;
            case STEPPE:
                Biome[] steppeReplacements = {FOREST, DESERT, TUNDRA, POLAR};
                return steppeReplacements;
            case MEDITERRANEAN:
                Biome[] mediterraneanReplacements = {STEPPE, FOREST, SAVANNA, DESERT, TUNDRA, POLAR};
                return mediterraneanReplacements;
            case DESERT:
                Biome[] desertReplacements = {SAVANNA, STEPPE, FOREST, RAINFOREST, TUNDRA, POLAR};
                return desertReplacements;
            case SAVANNA:
                Biome[] savannaReplacements = {STEPPE, DESERT, FOREST, RAINFOREST, TUNDRA, POLAR};
                return savannaReplacements;
            case RAINFOREST:
                Biome[] rainforestReplacements = {FOREST, SAVANNA, STEPPE, TUNDRA, POLAR};
                return rainforestReplacements;
            default:
                throw new IllegalArgumentException("The given Biome does not have any replacements: " + biome.toString());
        }
    }
}
