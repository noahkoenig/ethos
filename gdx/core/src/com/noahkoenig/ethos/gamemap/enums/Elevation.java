package com.noahkoenig.ethos.gamemap.enums;

public enum Elevation {

    /**
     * Glacial zone refers to areas above the permanent snowline where glaciers are found.
     * The height range of the glacial zone typically starts at around 4,500 meters and can go up to the highest peaks of mountains.
     * These areas are characterized by extreme cold temperatures and harsh weather conditions, and support only specialized forms of plant and animal life.
     */
    GLACIAL(6),
    /**
     * The nival zone is the area above the upper limit of continuous snow cover, where snow persists throughout the year.
     * The height range of the nival zone typically starts at around 3,500 meters and can extend up to the permanent snowline.
     * The nival zone is characterized by low temperatures and harsh climatic conditions that support only a limited range of vegetation and animal life.
     */
    NIVAL(5),
    /**
     * The alpine zone is the area above the treeline but below the permanent snowline, typically ranging from 2,500 to 3,500 meters in height.
     * The alpine zone is characterized by harsh climatic conditions with low temperatures and strong winds, and supports a limited range of vegetation
     * and animal life adapted to the challenging conditions.
     */
    ALPINE(4),
    /**
     * The subalpine zone is the area just below the treeline, ranging from 1,500 to 2,500 meters in height.
     * The subalpine zone is characterized by a cooler and moister climate than the lower slopes, supporting a diverse range of
     * plant and animal life, including coniferous forests and meadows.
     */
    SUBALPINE(3),
    /**
     * The montane zone typically ranges from 500 to 1,500 meters in height. This zone is characterized by moderate temperatures and precipitation,
     * and supports a diverse range of vegetation, including deciduous and coniferous forests, as well as grasslands.
     */
    MONTANE(2),
    /**
     * The upland zone refers to elevated areas above the surrounding landscape, typically ranging from 200 to 500 meters in height.
     * This zone is characterized by cooler temperatures and higher rainfall than the lowlands, and supports a range of vegetation,
     * including heath, scrub, and deciduous woodland.
     */
    UPLAND(1),
    /**
     * The lowland zone is the area at the base of mountains or hills, typically below 200 meters in height.
     * This zone is characterized by warmer temperatures and lower rainfall than upland areas, and supports a range of vegetation,
     * including grasslands, wetlands, and deciduous forests. Lowland areas often have fertile soils and are used for agriculture and human settlements.
     */
    LOWLAND(0),
    /**
     * This is the uppermost layer of the ocean that receives enough sunlight to support photosynthesis by phytoplankton.
     * The euphotic zone extends from the surface down to about 200 meters in depth and is the most biologically productive part of the ocean.
     */
    EUPHOTIC(-1),
    /**
     * The dysphotic zone is the layer of the ocean where there is not enough sunlight for photosynthesis to occur, but there is still some light penetration.
     * The depth of the dysphotic zone varies depending on factors like water clarity and location, but it generally extends from about 200 to 1000 meters deep.
     */
    DYSPHOTIC(-2),
    /**
     * This is the deepest layer of the ocean where no sunlight penetrates, making it completely dark.
     * The aphotic zone starts at a depth of around 1000 meters and extends down to the seafloor.
     * Life in the aphotic zone must rely on alternative energy sources, such as chemosynthesis, which is the process of using chemicals instead of sunlight to produce energy.
     */
    APHOTIC(-3);

    public final int INDEX;
    public final static int HIGHEST_INDEX = getHighestIndex();
    public final static int LOWEST_INDEX = getLowestIndex();

    Elevation(int index) {
        this.INDEX = index;
    }

    public static int getHighestIndex() {
        int highestIndex = -Integer.MAX_VALUE;
        for (Elevation elevation : values()) {
            if (elevation.INDEX > highestIndex) {
                highestIndex = elevation.INDEX;
            }
        }
        return highestIndex;
    }

    public static int getLowestIndex() {
        int lowestIndex = Integer.MAX_VALUE;
        for (Elevation elevation : values()) {
            if (elevation.INDEX < lowestIndex) {
                lowestIndex = elevation.INDEX;
            }
        }
        return lowestIndex;
    }
}
