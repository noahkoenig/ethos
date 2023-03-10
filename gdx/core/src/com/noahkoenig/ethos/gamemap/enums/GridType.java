package com.noahkoenig.ethos.gamemap.enums;

public enum GridType {
    /**
     * A completely random map. Not suitable for gameplay in many cases.
     */
    RANDOM,
    /**
     * Create your custom Grid! Here you can play around with the generationTiles and detail values.
     */
    CUSTOM,
    /**
     * A map with one big continent in the middle of the map and a vast ocean.
     * If the ocean percentage is too high, the continent might block the sea route above and / or below the continent.
     */
    PANGAEA,
    /**
     * A map with different sized continents with vast oceans and almost no islands.
     */
     CONTINENTS,
    /**
     * A map with many different sized and tattered islands and oceans.
     */
    ISLANDS,
    /**
     * A mix of CONTINENTS and ISLANDS. The most suitable for gameplay in my opinion.
     */
    CONTINENTS_AND_ISLANDS,
    /**
     * TODO: a map that is basically CONTINENTS_AND_ISLANDS but the size of the oceans and continents vary a lot.
     * I will have to rewrite the generation script for that.
     */
    TERRA_NOVA;
}
