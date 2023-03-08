package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.List;
import com.noahkoenig.ethos.gamemap.enums.Biome;
import com.noahkoenig.ethos.gamemap.enums.GridType;

/**
 * A script that generates a completely random map. Not suitable for gameplay in my opinion.
 */
public class Random extends Grid {

    public Random(int width, int height, float oceanPercentage, float latitudeTolerance) {
        super(GridType.RANDOM, width, height, oceanPercentage, latitudeTolerance);
        generateGrid();
    }

    @Override
    protected void generateGrid() {
        for(int x = 0; x < HEIGHT; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < WIDTH; y++) {
                Biome biome = (int) (Math.random() * 100 + 1) > OCEAN_PERCENTAGE
                    ? getRandomLandBiomeByLatitude(getLatitudeOfRow(x)) : Biome.OCEAN;
                row.add(new Tile(getRandomElevationByBiome(biome), biome, getRandomTerrain(), x, y));
            }
            getTiles().put(x, row);
        }
    }
}