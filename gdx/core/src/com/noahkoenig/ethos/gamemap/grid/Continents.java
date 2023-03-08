package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.noahkoenig.ethos.gamemap.enums.GridType;

/**
 * A script that generates roughly equally sized continents and no islands.
 */
public class Continents extends Grid {

    private final int CONTINENT_AMOUNT;
    private final float DETAIL;
    private List<Tile> continentOrigins = new ArrayList<Tile>();

    /**
     * @param contintentAmount : Approximate, not exact. The number of tiles on the map the script tries to generate a continent from.
     * @param detail : Between [0, 1[. The closer to 1, the more detail the map has, but it takes longer to generate.
     */
    public Continents(int width, int height, float oceanPercentage, float latitudeTolerance, int continentAmount, float detail) {
        super(GridType.CONTINENTS, width, height, oceanPercentage, latitudeTolerance);
        this.CONTINENT_AMOUNT = continentAmount;
        this.DETAIL = detail;
        generateGrid();
    }

    @Override
    protected void generateGrid() {
        generateContinentOrigins();
        generateContinents();
    }

    private void generateContinentOrigins() {
        for (int i = 0; i < CONTINENT_AMOUNT; i++) {
            continentOrigins.add(getTileByPosition((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT)));
        }
    }

    private void generateContinents() {
        while (getWaterTileAmount() > WIDTH * HEIGHT * OCEAN_PERCENTAGE) {
            List<Tile> newTiles = new ArrayList<Tile>();
            List<Tile> removedTiles = new ArrayList<Tile>();
            Collections.shuffle(continentOrigins);
            for (Tile continentOrigin : continentOrigins) {
                List<Tile> neighboringTiles = getNeighboringTilesByPosition(continentOrigin.X, continentOrigin.Y);
                Collections.shuffle(neighboringTiles);
                for (Tile neighbor : neighboringTiles) {
                    if (Math.random() > DETAIL) {
                        neighbor.setBiome(getRandomLandBiomeByLatitude(getLatitudeOfRow(neighbor.Y)));
                        removedTiles.add(continentOrigin);
                        newTiles.add(neighbor);
                    }
                }
            }
            for (Tile removedTile : removedTiles) {
                continentOrigins.remove(removedTile);
            }
            continentOrigins.addAll(newTiles);
        }
    }
}