package com.noahkoenig.ethos.gamemap.grid;

import java.util.HashMap;
import java.util.Map;

import com.noahkoenig.ethos.gamemap.enums.GridType;


public class Continents extends Grid {

    private final int CONTINENT_AMOUNT;
    private Map<Integer, Integer> continentOrigins = new HashMap<Integer, Integer>();

    /**
     * @param continentAmount : approximate, not exact
     */
    public Continents(int continentAmount, int width, int height, int oceanPercentage, int latitudeTolerance) {
        super(GridType.CONTINENTS, width, height, oceanPercentage, latitudeTolerance);
        this.CONTINENT_AMOUNT = continentAmount;
        generateGrid();
    }

    @Override
    protected void generateGrid() {
        generateContinentOrigins();
        generateContinentClusters();
    }

    private void generateContinentOrigins() {
        for (int i = 0; i < CONTINENT_AMOUNT; i++) {
            continentOrigins.put(
                (int) (Math.random() * width),
                (int) (Math.random() * height)
            );
        }
    }

    private void generateContinentClusters() {
        while (getWaterTileAmount() > width * height * ((float) oceanPercentage / 100)) {
            Map<Integer, Integer> newEntries = new HashMap<Integer, Integer>();
            Map<Integer, Integer> removedEntries = new HashMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : continentOrigins.entrySet()) {
                for (Tile tile : getNeighboringTilesByPosition(entry.getKey(), entry.getValue())) {
                    if (Math.random() > 0.5) {
                        tile.setBiome(getRandomLandBiomeByLatitude(getLatitudeOfRow(tile.Y), latitudeTolerance));
                        removedEntries.put(entry.getKey(), entry.getValue());
                        newEntries.put(tile.X, tile.Y);
                    }
                }
            }
            for (Map.Entry<Integer, Integer> entry : removedEntries.entrySet()) {
                continentOrigins.remove(entry.getKey(), entry.getValue());
            }
            continentOrigins.putAll(newEntries);
        }
    }
}