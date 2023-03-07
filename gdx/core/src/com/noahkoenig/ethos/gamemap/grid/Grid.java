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
    protected int width;
    protected int height;
    protected int oceanPercentage;
    protected int latitudeTolerance;
    protected Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    /**
     * @param width : minimum value is 2
     * @param height : minimum value is 2
     * @param oceanPercentage : approximate, not exact
     * @param latitudeTolerance : how much the latitude value can differ from the min- and maxLatitude values. the actual effect of the tolerance is highly dependent on the width and height values
     */
    protected Grid(GridType type, int width, int height, int oceanPercentage, int latitudeTolerance) {
        this.TYPE = type;
        this.width = width;
        this.height = height;
        this.oceanPercentage = oceanPercentage;
        this.latitudeTolerance = latitudeTolerance;
        fillGridWith(Elevation.EUPHOTIC, Biome.OCEAN, Terrain.FLAT);
    }

    protected abstract void generateGrid();

    private void fillGridWith(Elevation elevation, Biome biome, Terrain terrain) {
        for(int x = 0; x < height; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < width; y++) {
                row.add(new Tile(elevation, biome, terrain, x, y));
            }
            tiles.put(x, row);
        }
    }

    public void printGrid() {
        for(int i = 0; i < tiles.size(); i++) {
            String row = "";
            for(Tile tile : tiles.get(i)) {
                row += tile.getBiome().ID + "  ";
            }
            System.out.println(row);
        }
    }

    public Elevation getRandomElevationByBiome(Biome biome) {
        byte index = (byte) ((Math.random() * (biome.MAX_ELEVATION - biome.MIN_ELEVATION) + biome.MIN_ELEVATION));
        return getElevationByIndex(index);
    }
    
    /**
     * @param latitude : needs to be between 0 and 90
     * @param tolerance : how much the latitude value can differ from the min- and maxLatitude values
     */
    public Biome getRandomLandBiomeByLatitude(int latitude, int tolerance) {
        List<Biome> biomes = new ArrayList<Biome>();
        for (Biome biome : Biome.values()) {
            if (biome.IS_WATER) {
                continue;
            } else if (biome.MIN_LATITUDE - tolerance <= latitude && latitude <= biome.MAX_LATITUDE + tolerance) {
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
                            isColumnOnGrid(width + x) ? width + x : getModulo(width + x, this.width)
                        ));
                    }
                }
            }
        }
        return neighboringTiles;
    }

    public static Elevation getElevationByIndex(byte index) {
        for (Elevation elevation : Elevation.values()) {
            if (elevation.INDEX == index) {
                return elevation;
            }
        }
        return null;
    }

    protected int getLatitudeOfRow(int row) {
        return Math.abs((int) Math.round(90 - (row * 180 / (height - 1))));
    }

    private boolean isRowOnGrid(int row) {
        return 0 <= row && row < height;
    }

    private boolean isColumnOnGrid(int column) {
        return 0 <= column && column < width;
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
        try {
            return tiles.get(y).get(x);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getOceanPercentage() { return oceanPercentage; }

    public int getLatitudeTolerance() { return latitudeTolerance; }

    public Map<Integer, List<Tile>> getTiles() { return tiles; }
}
