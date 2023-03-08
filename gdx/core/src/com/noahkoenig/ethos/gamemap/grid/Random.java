package com.noahkoenig.ethos.gamemap.grid;

import com.noahkoenig.ethos.gamemap.enums.Biome;
import com.noahkoenig.ethos.gamemap.enums.GridType;

/**
 * A script that generates a completely random map. Not suitable for gameplay in most cases.
 */
public class Random extends Grid {

    public Random(int width, int height, float oceanPercentage, float latitudeTolerance) {
        super(GridType.RANDOM, width, height, oceanPercentage, latitudeTolerance);
        generateGrid();
    }

    @Override
    protected void generateGrid() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Biome biome = (int) (Math.random() * 100 + 1) > OCEAN_PERCENTAGE
                    ? getRandomLandBiomeByLatitude(getLatitudeOfRow(x)) : Biome.OCEAN;

                Tile tile = getTiles().get(x).get(y);
                tile.setBiome(biome);
                tile.setElevation(getRandomElevationByBiome(biome));
                tile.setTerrain(getRandomTerrain());
            }
        }
    }
}