package com.noahkoenig.ethos.gamemap.grid;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.noahkoenig.ethos.gamemap.enums.GridType;

/**
 * A script that generates a map with small and big continents and islands.
 */
public class ContinentsAndIslands extends Grid {

    private final int CONTINENT_AMOUNT;
    private final float DETAIL;
    private Map<Integer, Integer> continentOrigins = new HashMap<Integer, Integer>();;

    /**
     * @param contintentAmount : Approximate, not exact. The number of tiles on the map the script tries to generate a continent from.
     * I recommend using the width value if you want continents with small and medium sized islands.
     * @param detail : Between [0, 1[. The closer to 1, the more detail the map has, but it takes longer to generate.
     */
    public ContinentsAndIslands(int width, int height, float oceanPercentage, float latitudeTolerance, int continentAmount, float detail) {
        super(GridType.CONTINENTS, width, height, oceanPercentage, latitudeTolerance);
        this.CONTINENT_AMOUNT = continentAmount;
        this.DETAIL = detail;
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
                (int) (Math.random() * WIDTH),
                (int) (Math.random() * HEIGHT)
            );
        }
    }

    private void generateContinentClusters() {
        while (getWaterTileAmount() > WIDTH * HEIGHT * OCEAN_PERCENTAGE) {
            Map<Integer, Integer> newEntries = new HashMap<Integer, Integer>();
            Map<Integer, Integer> removedEntries = new HashMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : continentOrigins.entrySet()) {
                List<Tile> neighbors = getNeighboringTilesByPosition(entry.getKey(), entry.getValue());
                Collections.shuffle(neighbors);
                for (Tile tile : neighbors) {
                    if (Math.random() > DETAIL) {
                        tile.setBiome(getRandomLandBiomeByLatitude(getLatitudeOfRow(tile.Y)));
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