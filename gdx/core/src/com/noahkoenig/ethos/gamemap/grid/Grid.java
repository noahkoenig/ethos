package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.noahkoenig.ethos.gamemap.enums.Biome;
import com.noahkoenig.ethos.gamemap.enums.Elevation;
import com.noahkoenig.ethos.gamemap.enums.GridType;
import com.noahkoenig.ethos.gamemap.enums.Terrain;

/**
 * contains all information relevant for the GameMap
 */
public abstract class Grid {

    public final GridType TYPE;
    public final int WIDTH;
    public final int HEIGHT;
    public final float OCEAN_PERCENTAGE;
    public final float LATITUDE_TOLERANCE;
    private Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    /**
     * @param type
     * @param width : Minimum value is 2. Should be about twice the height.
     * @param height : Minimum value is 2. Should be about half the width.
     * @param oceanPercentage : Between [0, 1[.
     * @param latitudeTolerance : Between 0.0 and 90.0. How much the Biomes latitude value can differ from min- and maxLatitude.
     */
    protected Grid(GridType type, int width, int height, float oceanPercentage, float latitudeTolerance) {
        this.TYPE = type;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.OCEAN_PERCENTAGE = oceanPercentage;
        this.LATITUDE_TOLERANCE = latitudeTolerance;
        fillGridWith(Elevation.EUPHOTIC, Biome.OCEAN, Terrain.FLAT);
    }

    protected abstract void generateGrid();

    private void fillGridWith(Elevation elevation, Biome biome, Terrain terrain) {
        for(int x = 0; x < HEIGHT; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < WIDTH; y++) {
                row.add(new Tile(elevation, biome, terrain, x, y));
            }
            tiles.put(x, row);
        }
    }

    public Elevation getRandomElevationByBiome(Biome biome) {
        byte index = (byte) ((Math.random() * (biome.MAX_ELEVATION - biome.MIN_ELEVATION) + biome.MIN_ELEVATION));
        return getElevationByIndex(index);
    }
    
    /**
     * @param latitude : needs to be between 0.0 and 90.0
     */
    public Biome getRandomLandBiomeByLatitude(float latitude) {
        List<Biome> biomes = new ArrayList<Biome>();
        for (Biome biome : Biome.values()) {
            if (biome.IS_WATER) {
                continue;
            } else if (biome.MIN_LATITUDE - LATITUDE_TOLERANCE <= latitude && latitude <= biome.MAX_LATITUDE + LATITUDE_TOLERANCE) {
                biomes.add(biome);
            }
        }
        return biomes.get((int) (Math.random() * biomes.size()));
    }

    public Terrain getRandomTerrain() {
        return Terrain.values()[(int) (Math.random() * Terrain.values().length)];
    }

    // Helper functions ============================================================================================================

    public List<Tile> getNeighboringTilesByPosition(int width, int height) {
        List<Tile> neighboringTiles = new ArrayList<Tile>();
        for (int y = -1; y <= 1; y++) {
            if (isRowOnGrid(height + y)) {
                List<Tile> row = tiles.get(height + y);
                for (int x = -1; x <= 1; x++) {
                    if (x != 0 || y != 0) {
                        neighboringTiles.add(row.get(
                            isColumnOnGrid(width + x) ? width + x : getModulo(width + x, WIDTH)
                        ));
                    }
                }
            }
        }
        return neighboringTiles;
    }

    public static Elevation getElevationByIndex(int index) {
        for (Elevation elevation : Elevation.values()) {
            if (elevation.INDEX == index) {
                return elevation;
            }
        }
        return null;
    }

    protected float getLatitudeOfRow(int row) {
        return Math.abs(Math.round(90 - (row * 180 / (HEIGHT - 1))));
    }

    private boolean isRowOnGrid(int row) {
        return 0 <= row && row < HEIGHT;
    }

    private boolean isColumnOnGrid(int column) {
        return 0 <= column && column < WIDTH    ;
    }

    /**
     * The Java % operator returns the remainder, not the mathematical modulo, which is why we need this function.
     * @param n
     * @param m
     * @return n mod m
     */
    private int getModulo(int n, int m) {
        return n < 0 ? (n % m) + m : n % m;
    }

    public Tile getTileByPosition(int x, int y) {
        return tiles.get(y).get(x);
    }

    public int getLandTileAmount() {
        int landTileAmount = 0;
        for(int y = 0; y < tiles.size(); y++) {
            for(Tile tile : tiles.get(y)) {
                if (!tile.getBiome().IS_WATER) {
                    landTileAmount++;
                }
            }
        }
        return landTileAmount;
    }

    public int getWaterTileAmount() {
        int waterTileAmount = 0;
        for(int y = 0; y < tiles.size(); y++) {
            for(Tile tile : tiles.get(y)) {
                if (tile.getBiome().IS_WATER) {
                    waterTileAmount++;
                }
            }
        }
        return waterTileAmount;
    }

    // Getters and Setters ============================================================================================================

    public Map<Integer, List<Tile>> getTiles() { return tiles; }
}
