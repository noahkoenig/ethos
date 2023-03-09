package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.Collections;
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
public class Grid {

    public final GridType TYPE;
    public final int WIDTH;
    public final int HEIGHT;
    public final float OCEAN_PERCENTAGE;
    public final float LATITUDE_TOLERANCE;
    private final int GENERATION_TILES_AMOUNT;
    private final float DETAIL;
    private List<Tile> generationTiles = new ArrayList<Tile>();
    private Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    /**
     * @param type : See GridType.
     * @param width : Minimum value is 2. Should be about twice the height.
     * @param height : Minimum value is 2. Should be about half the width.
     * @param oceanPercentage : Between [0, 1[. A value around 0.66 generates the best results in my opinion.
     * @param latitudeTolerance : Between 0.0 and 90.0. How much the Biomes latitude value can differ from min- and maxLatitude.
     */
    public Grid(GridType type, int width, int height, float oceanPercentage, float latitudeTolerance) {
        this.TYPE = type;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.OCEAN_PERCENTAGE = oceanPercentage;
        this.LATITUDE_TOLERANCE = latitudeTolerance;
        this.GENERATION_TILES_AMOUNT = getRandomGenerationTilesAmountByGridType(TYPE);
        this.DETAIL = getRandomDetailByGridType(TYPE);
        initGrid();
    }

    private int getRandomGenerationTilesAmountByGridType(GridType type) {
        switch(type) {
            case RANDOM: return getRandomIntInRange(5, WIDTH * 5);
            case CONTINENTS: return getRandomIntInRange((int) (WIDTH * 0.25), (int) (WIDTH * 0.5));
            case ISLANDS: return getRandomIntInRange(WIDTH, WIDTH * 2);
            case CONTINENTS_AND_ISLANDS: return getRandomIntInRange((int) (WIDTH * 0.75), (int) (WIDTH * 1.25));
            default: return WIDTH;
        }
    }

    private float getRandomDetailByGridType(GridType type) {
        switch(type) {
            case RANDOM: return getRandomFloatInRange(0, 1);
            case CONTINENTS: return getRandomFloatInRange((float) 0.8, (float) 0.9);
            case ISLANDS: return getRandomFloatInRange((float) 0.9, (float) 0.99);
            case CONTINENTS_AND_ISLANDS: return getRandomFloatInRange((float) 0.8, (float) 0.9);
            default: return (float) 0.5;
        }
    }

    /**
     * Use this constructor if you want to create a Grid with GridType.CUSTOM. Here you can set the generationTiles and detail yourself.
     * @param type : See GridType.
     * @param width : Minimum value is 2. Should be about twice the height.
     * @param height : Minimum value is 2. Should be about half the width.
     * @param oceanPercentage : Between [0, 1[.
     * @param latitudeTolerance : Between 0.0 and 90.0. How much the Biomes latitude value can differ from min- and maxLatitude.
     * @param generationTiles : Approximate, not exact. The number of tiles on the map the script tries to generate a continent from.
     * I recommend values somewhere between 0.5 * width and 2 * width
     * @param detail : Between [0, 1[. The closer to 1, the more detail the map has, but it takes longer to generate.
     */
    public Grid(int width, int height, float oceanPercentage, float latitudeTolerance, int generationTiles, float detail) {
        this.TYPE = GridType.CUSTOM;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.OCEAN_PERCENTAGE = oceanPercentage;
        this.LATITUDE_TOLERANCE = latitudeTolerance;
        this.GENERATION_TILES_AMOUNT = generationTiles;
        this.DETAIL = detail;
        initGrid();
    }

    private void initGrid() {
        initGridWithTiles(Elevation.EUPHOTIC, Biome.OCEAN, Terrain.FLAT);
        initGenerationTiles();
        initBiomes();
    }

    private void initGridWithTiles(Elevation elevation, Biome biome, Terrain terrain) {
        for(int x = 0; x < WIDTH; x++) {
            List<Tile> column = new ArrayList<Tile>();
            for(int y = 0; y < HEIGHT; y++) {
                column.add(new Tile(elevation, biome, terrain, x, y));
            }
            tiles.put(x, column);
        }
    }

    private void initGenerationTiles() {
        for (int i = 0; i < GENERATION_TILES_AMOUNT; i++) {
            generationTiles.add(getTileByPosition((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT)));
        }
    }

    private void initBiomes() {
        while (getWaterTileAmount() > WIDTH * HEIGHT * OCEAN_PERCENTAGE) {
            List<Tile> newTiles = new ArrayList<Tile>();
            List<Tile> removedTiles = new ArrayList<Tile>();
            Collections.shuffle(generationTiles);
            for (Tile continentOrigin : generationTiles) {
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
                generationTiles.remove(removedTile);
            }
            generationTiles.addAll(newTiles);
        }
    }

    public Elevation getRandomElevationByBiome(Biome biome) {
        return getElevationByIndex(getRandomIntInRange(biome.MIN_ELEVATION, biome.MAX_ELEVATION));
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
        for (int x = -1; x <= 1; x++) {
            if (isColumnOnGrid(width + x)) {
                List<Tile> column = tiles.get(width + x);
                for (int y = -1; y <= 1; y++) {
                    if (x != 0 || y != 0) {
                        neighboringTiles.add(column.get(
                            isRowOnGrid(height + y) ? height + y : getModulo(height + y, HEIGHT)
                        ));
                    }
                }
            }
        }
        return neighboringTiles;
    }

    public Elevation getElevationByIndex(int index) {
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

    public int getRandomIntInRange(int minimum, int maximum) {
        return (int) (Math.random() * (maximum - minimum) + minimum);
    }

    public float getRandomFloatInRange(float minimum, float maximum) {
        return (float) (Math.random() * (maximum - minimum) + minimum);
    }

    public Tile getTileByPosition(int x, int y) {
        return tiles.get(x).get(y);
    }

    public int getLandTileAmount() {
        int landTileAmount = 0;
        for(int x = 0; x < tiles.size(); x++) {
            for(Tile tile : tiles.get(x)) {
                if (!tile.getBiome().IS_WATER) {
                    landTileAmount++;
                }
            }
        }
        return landTileAmount;
    }

    public int getWaterTileAmount() {
        int waterTileAmount = 0;
        for(int x = 0; x < tiles.size(); x++) {
            for(Tile tile : tiles.get(x)) {
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
