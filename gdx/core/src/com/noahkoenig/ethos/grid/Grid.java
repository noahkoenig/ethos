package com.noahkoenig.ethos.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.noahkoenig.ethos.grid.enums.Biome;
import com.noahkoenig.ethos.grid.enums.Elevation;
import com.noahkoenig.ethos.grid.enums.Terrain;

public class Grid {

    private int width;
    private int height;
    private Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    /**
     * @param width
     * @param height
     * @param waterPercentage : approximate, not exact
     * @param latitudeTolerance : how much the latitude value can differ from the min- and maxLatitude values
     */
    public Grid(int width, int height, int oceanPercentage, int latitudeTolerance) {
        this.width = width;
        this.height = height;
        generateGrid(width, height, oceanPercentage, latitudeTolerance);
    }

    public void generateGrid(int width, int height, int oceanPercentage, int latitudeTolerance) {
        for(int x = 0; x < height; x++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int y = 0; y < width; y++) {
                Biome biome = (int) (Math.random() * 100 + 1) > oceanPercentage
                    ? getRandomLandBiomeByLatitude(getLatitudeOfRow(x), latitudeTolerance) : Biome.OCEAN;
                row.add(new Tile(getRandomElevationByBiome(biome), biome, getRandomTerrain(), x, y));
            }
            tiles.put(x, row);
        }
        // TODO: need a function here that aligns all elevations
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
            if (biome.equals(Biome.OCEAN) || biome.equals(Biome.LAKE)) {
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
                            isColumnOnGrid(width + x) ? width + x : getModulo(width + x, width)
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

    private int getLatitudeOfRow(int row) {
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
        return tiles.get(y).get(x);
    }

    // Getters and Setters ============================================================================================================

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public Map<Integer, List<Tile>> getTiles() { return tiles; }
}