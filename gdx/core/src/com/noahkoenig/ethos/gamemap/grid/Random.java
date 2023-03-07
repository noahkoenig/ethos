package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.List;
import com.noahkoenig.ethos.gamemap.enums.Biome;
import com.noahkoenig.ethos.gamemap.enums.GridType;

public class Random extends Grid {

    public Random(int width, int height, int oceanPercentage, int latitudeTolerance) {
        super(GridType.RANDOM, width, height, oceanPercentage, latitudeTolerance);
        generateGrid();
    }

    @Override
    protected void generateGrid() {
        for(int x = 0; x < height; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < width; y++) {
                Biome biome = (int) (Math.random() * 100 + 1) > oceanPercentage
                    ? getRandomLandBiomeByLatitude(getLatitudeOfRow(x), latitudeTolerance) : Biome.OCEAN;
                row.add(new Tile(getRandomElevationByBiome(biome), biome, getRandomTerrain(), x, y));
            }
            tiles.put(x, row);
        }
    }
}