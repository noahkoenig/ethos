package src.grid;

public enum Elevation {

    /**
     * Glacial zone refers to areas above the permanent snowline where glaciers are found.
     * The height range of the glacial zone typically starts at around 4,500 meters and can go up to the highest peaks of mountains.
     * These areas are characterized by extreme cold temperatures and harsh weather conditions, and support only specialized forms of plant and animal life.
     */
    GLACIAL((byte) 7),
    /**
     * The nival zone is the area above the upper limit of continuous snow cover, where snow persists throughout the year.
     * The height range of the nival zone typically starts at around 3,500 meters and can extend up to the permanent snowline.
     * The nival zone is characterized by low temperatures and harsh climatic conditions that support only a limited range of vegetation and animal life.
     */
    NIVAL((byte) 6),
    /**
     * The alpine zone is the area above the treeline but below the permanent snowline, typically ranging from 2,500 to 3,500 meters in height.
     * The alpine zone is characterized by harsh climatic conditions with low temperatures and strong winds, and supports a limited range of vegetation
     * and animal life adapted to the challenging conditions.
     */
    ALPINE((byte) 5),
    /**
     * The subalpine zone is the area just below the treeline, ranging from 1,500 to 2,500 meters in height.
     * The subalpine zone is characterized by a cooler and moister climate than the lower slopes, supporting a diverse range of
     * plant and animal life, including coniferous forests and meadows.
     */
    SUBALPINE((byte) 4),
    /**
     * The montane zone typically ranges from 500 to 1,500 meters in height. This zone is characterized by moderate temperatures and precipitation,
     * and supports a diverse range of vegetation, including deciduous and coniferous forests, as well as grasslands.
     */
    MONTANE((byte) 3),
    /**
     * The upland zone refers to elevated areas above the surrounding landscape, typically ranging from 200 to 500 meters in height.
     * This zone is characterized by cooler temperatures and higher rainfall than the lowlands, and supports a range of vegetation,
     * including heath, scrub, and deciduous woodland.
     */
    UPLAND((byte) 2),
    /**
     * The lowland zone is the area at the base of mountains or hills, typically below 200 meters in height.
     * This zone is characterized by warmer temperatures and lower rainfall than upland areas, and supports a range of vegetation,
     * including grasslands, wetlands, and deciduous forests. Lowland areas often have fertile soils and are used for agriculture and human settlements.
     */
    LOWLAND((byte) 1),
    /**
     * Also known as the sunlight or euphotic zone, this is the topmost layer of the ocean, extending from the surface down to about 200 meters.
     * It is characterized by abundant sunlight, high oxygen levels, and a wide range of marine life, including plankton, fish, and sea birds.
     */
    EPIPELAGIC((byte) 0),
    /**
     * Also known as the twilight or disphotic zone, this layer extends from 200 to 1000 meters below the surface.
     * It is characterized by decreasing sunlight, cooler temperatures, and lower oxygen levels.
     * Marine life in this zone includes a variety of small fish, squid, and other organisms adapted to low light conditions.
     */
    MESOPELAGIC((byte) -1),
    /**
     * Also known as the midnight or aphotic zone, this layer extends from 1000 to 4000 meters below the surface.
     * It is characterized by complete darkness, cold temperatures, and extremely high pressure.
     * Marine life in this zone is dominated by bioluminescent creatures such as lanternfish, anglerfish, and jellyfish.
     */
    BATHYPELAGIC((byte) -2),
    /**
     * This layer extends from 4000 to 6000 meters below the surface.
     * It is characterized by very low temperatures, high pressure, and a lack of nutrients.
     * Marine life in this zone is limited to a few specialized species such as deep-sea amphipods and copepods.
     */
    ABYSSOPELAGIC((byte) -3),
    /**
     * Also known as the trenches or ultra-abyssal zone, this layer extends from 6000 meters to the bottom of the ocean.
     * It includes the deepest parts of the ocean, such as the Mariana Trench, and is characterized by extreme cold, pressure, and darkness.
     * Only a few species of specialized creatures such as amphipods, polychaete worms, and bacteria are known to live in this zone.
     */
    HADOPELAGIC((byte) -4);

    private byte index;

    Elevation(byte index) {
        this.index = index;
    }

    public byte getIndex() { return index; }
}
