package com.noahkoenig.ethos.gamemap.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    public final int MAX_ELEVATION_MARGIN;
    public final int GENERATION_TILES_AMOUNT;
    public final float DETAIL;
    public final float MIN_OCEAN_STEPPE_DISTANCE = (float) 3.0;
    private Map<Integer, List<Tile>> tiles = new HashMap<Integer, List<Tile>>();

    // Constructors ============================================================================================================

    /**
     * @param type : See GridType.
     * @param width : Minimum value is 2. Should be about twice the height.
     * @param height : Minimum value is 2. Should be about half the width.
     * @param oceanPercentage : Between [0, 1[. A value around 0.66 generates the best results in my opinion.
     * @param latitudeTolerance : Between 0.0 and 90.0. How much the Biomes latitude value can differ from min- and maxLatitude.
     * @param maxElevationMargin : The maximum Elevation difference between two neighboring tiles. 2 or 3 works best, 0 will crash the script.
     */
    public Grid(GridType type, int width, int height, float oceanPercentage, float latitudeTolerance, int maxElevationMargin) {
        this.TYPE = type;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.OCEAN_PERCENTAGE = oceanPercentage;
        this.LATITUDE_TOLERANCE = latitudeTolerance;
        this.MAX_ELEVATION_MARGIN = maxElevationMargin;
        this.GENERATION_TILES_AMOUNT = getRandomGenerationTilesAmountByGridType(TYPE);
        this.DETAIL = getRandomDetailByGridType(TYPE);
        initGrid();
    }

    /**
     * Use this constructor if you want to create a Grid with GridType.CUSTOM. Here you can set the generationTiles and detail yourself.
     * @param width : Minimum value is 2. Should be about twice the height.
     * @param height : Minimum value is 2. Should be about half the width.
     * @param oceanPercentage : Between [0, 1[.
     * @param latitudeTolerance : Between 0.0 and 90.0. How much the Biomes latitude value can differ from min- and maxLatitude.
     * @param maxElevationMargin : The maximum Elevation difference between two neighboring tiles. 2 or 3 works best, 0 will crash the script.
     * @param generationTiles : Approximate, not exact. The number of tiles on the map the script tries to generate a continent from.
     * I recommend values somewhere between 0.5 * width and 2 * width
     * @param detail : Between [0, 1[. The closer to 1, the more detail the map has, but it takes longer to generate.
     */
    public Grid(int width, int height, float oceanPercentage, float latitudeTolerance, int generationTilesAmount, float detail, int maxElevationMargin) {
        this.TYPE = GridType.CUSTOM;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.OCEAN_PERCENTAGE = oceanPercentage;
        this.LATITUDE_TOLERANCE = latitudeTolerance;
        this.MAX_ELEVATION_MARGIN = maxElevationMargin;
        this.GENERATION_TILES_AMOUNT = generationTilesAmount;
        this.DETAIL = detail;
        initGrid();
    }

    // Grid Init Functions ============================================================================================================

    private void initGrid() {
        initGridWithTiles(getRandomElevationByBiome(Biome.OCEAN), Biome.OCEAN, Terrain.FLAT);
        initBiomes();
        initElevations();
        initTerrains();
    }

    /**
     * Adds a new Tile with the given attributes at every position on the grid.
     * @param elevation : See Elevation
     * @param biome : See Biome
     * @param terrain : See Terrain
     */
    private void initGridWithTiles(Elevation elevation, Biome biome, Terrain terrain) {
        for(int x = 0; x < WIDTH; x++) {
            List<Tile> column = new ArrayList<Tile>();
            for(int y = 0; y < HEIGHT; y++) {
                column.add(new Tile(elevation, biome, terrain, x, y));
            }
            tiles.put(x, column);
        }
    }

    private void initBiomes() {
        List<Tile> generationTiles = getGenerationTiles();
        List<Tile> newTiles = new ArrayList<Tile>();
        List<Tile> removedTiles = new ArrayList<Tile>();
        while (getWaterTileAmount() > WIDTH * HEIGHT * OCEAN_PERCENTAGE) {
            newTiles.clear();
            removedTiles.clear();
            Collections.shuffle(generationTiles);
            for (Tile generationTile : generationTiles) {
                List<Tile> neighboringTiles = getNeighboringTilesByPosition(generationTile.X, generationTile.Y);
                Collections.shuffle(neighboringTiles);
                for (Tile neighbor : neighboringTiles) {
                    if (Math.random() > DETAIL) {
                        neighbor.setBiome(getRandomLandBiomeByLatitude(getLatitudeOfRow(neighbor.Y)));
                        removedTiles.add(generationTile);
                        newTiles.add(neighbor);
                    }
                }
            }
            for (Tile removedTile : removedTiles) {
                generationTiles.remove(removedTile);
            }
            generationTiles.addAll(newTiles);
        }
        adjustLakesAndOceans();
        adjustTemperateBiomes();
    }

    private List<Tile> getGenerationTiles() {
        List<Tile> generationTiles = new ArrayList<Tile>();
        if (TYPE.equals(GridType.PANGAEA)) {
            generationTiles.add(getTileByPosition((int) (WIDTH * 0.5), (int) (HEIGHT * 0.5)));
        } else {
            for (int i = 0; i < GENERATION_TILES_AMOUNT; i++) {
                generationTiles.add(getTileByPosition((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT)));
            }
        }
        return generationTiles;
    }

    /**
     * Turns all landlocked OCEAN Biomes into LAKE Biomes.
     */
    private void adjustLakesAndOceans() {
        //TODO
    }

    /**
     * Adjusts the the temperate zone (FOREST, STEPPE and MEDITERRANEAN) parts of the map.
     * MEDITERRANEAN Biomes are only found in coastal areas, while STEPPE is found in continental areas. FOREST is found in both areas.
     */
    private void adjustTemperateBiomes() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Tile tile = getTileByPosition(x, y);
                if (tile.getBiome().equals(Biome.MEDITERRANEAN)) {
                    if (getDistanceToNearestTileWithBiome(tile, Biome.OCEAN) > MIN_OCEAN_STEPPE_DISTANCE) {
                        tile.setBiome(Biome.STEPPE);
                    }
                } else if (tile.getBiome().equals(Biome.STEPPE)) {
                    if (getDistanceToNearestTileWithBiome(tile, Biome.OCEAN) < MIN_OCEAN_STEPPE_DISTANCE) {
                        tile.setBiome(Biome.MEDITERRANEAN);
                    }
                } else if (tile.getBiome().equals(Biome.FOREST)) {
                    if (getDistanceToNearestTileWithBiome(tile, Biome.OCEAN) > MIN_OCEAN_STEPPE_DISTANCE * 2) {
                        if (Math.random() > 0.5) {
                            tile.setBiome(Biome.STEPPE);
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes and aligns all Elevations based on MAX_ELEVATION_MARGIN.
     */
    private void initElevations() {
        assignRandomElevations();
        while(!isMaxElevationMarginFulfilled()) {
            for(int x = 0; x < WIDTH; x++) {
                for(int y = 0; y < HEIGHT; y++) {
                    Tile tile = getTileByPosition(x, y);
                    for (Tile neighbor : getNeighboringTilesByPosition(tile.X, tile.Y)) {
                        int neighborMinElevation = neighbor.getElevation().INDEX - MAX_ELEVATION_MARGIN;
                        int neighborMaxElevation = neighbor.getElevation().INDEX + MAX_ELEVATION_MARGIN;
                        if (!isIntInRange(tile.getElevation().INDEX, neighborMinElevation, neighborMaxElevation)) {
                            tile.setElevation(getRandomElevationByBiome(tile.getBiome()));
                        }
                    }
                }
            }
        }
        adjustBiomesToElevations();
    }

    /**
     * Assigns random Elevations to all Land Tiles, no matter the Biome.
     * Ocean Tiles get are assigned an Elevation in their min- and maxElevation range.
     */
    private void assignRandomElevations() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Tile tile = getTileByPosition(x, y);
                tile.setElevation(
                    tile.getBiome().equals(Biome.OCEAN) ? getRandomElevationByBiome(tile.getBiome()) : getRandomElevationInRange(0, 6)
                );
            }
        }
    }

    /**
     * Checks if all Tiles have a Biome and Elevation combo that is allowed, and adjusts the Biome if not.
     */
    private void adjustBiomesToElevations() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Tile tile = getTileByPosition(x, y);
                if (!tile.isElevationAllowedForBiome()) {
                    for (Biome biome : Biome.getReplacementsByBiome(tile.getBiome())) {
                        tile.setBiome(biome);
                        if (tile.isElevationAllowedForBiome()) { break; }
                    }
                }
            }
        }
    }

    /**
     * Assigns completely random Terrains to all Tiles. We should make this dependent on the Elevation in the future.
     */
    private void initTerrains() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                getTileByPosition(x, y).setTerrain(getRandomTerrain());
            }
        }
    }

    // Randomized Functions ============================================================================================================

    /**
     * Generation tiles are the tiles on the map the script tries to generate a land mass from.
     */
    private int getRandomGenerationTilesAmountByGridType(GridType type) {
        int widthHeightAverage = (int) ((WIDTH + HEIGHT) * 0.5);
        switch(type) {
            case RANDOM: return getRandomIntInRange(5, widthHeightAverage * 5);
            case PANGAEA: return 1;
            case CONTINENTS: return getRandomIntInRange((int) (widthHeightAverage * 0.5), (int) (widthHeightAverage * 1));
            case ISLANDS: return getRandomIntInRange((int) (widthHeightAverage * 1.5), (int) (widthHeightAverage * 2.5));
            case CONTINENTS_AND_ISLANDS: return getRandomIntInRange((int) (widthHeightAverage * 1), (int) (widthHeightAverage * 1.5));
            default: return widthHeightAverage;
        }
    }

    private float getRandomDetailByGridType(GridType type) {
        switch(type) {
            case RANDOM: return getRandomFloatInRange(0, (float) 0.99);
            case PANGAEA: return getRandomFloatInRange((float) 0.75, (float) 0.85);
            case CONTINENTS: return getRandomFloatInRange((float) 0.8, (float) 0.9);
            case ISLANDS: return getRandomFloatInRange((float) 0.9, (float) 0.99);
            case CONTINENTS_AND_ISLANDS: return getRandomFloatInRange((float) 0.8, (float) 0.9);
            default: return (float) 0.8;
        }
    }

    private Elevation getRandomElevationInRange(int minimum, int maximum) {
        return getElevationByIndex(getRandomIntInRange(minimum, maximum));
    }

    private Elevation getRandomElevationByBiome(Biome biome) {
        return getElevationByIndex(getRandomIntInRange(biome.MIN_ELEVATION, biome.MAX_ELEVATION));
    }
    
    private Biome getRandomLandBiomeByLatitude(float latitude) throws IllegalArgumentException {
        if (isFloatInRange(latitude, (float) 0.0, (float) 90.0)) {
            List<Biome> biomes = new ArrayList<Biome>();
            for (Biome biome : Biome.values()) {
                if (!biome.IS_WATER) {
                    if (biome.MIN_LATITUDE - LATITUDE_TOLERANCE <= latitude && latitude <= biome.MAX_LATITUDE + LATITUDE_TOLERANCE) {
                        biomes.add(biome);
                    }
                }
            }
            return biomes.get(getRandomIntInRange(0, biomes.size() - 1));
        }
        throw new IllegalArgumentException("Expected latitude between 0.0 and 90.0, got " + latitude);
    }

    private Terrain getRandomTerrain() {
        return Terrain.values()[(int) (Math.random() * Terrain.values().length)];
    }

    // Helper Functions ============================================================================================================

    private float getDistanceToNearestTileWithBiome(Tile tile, Biome biome) {
        float distance = (float) Integer.MAX_VALUE;
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Tile newTile = getTileByPosition(x, y);
                if (newTile.getBiome().equals(biome)) {
                    float newDistance = getDistanceBetweenTiles(tile, newTile);
                    distance = newDistance < distance ? newDistance : distance;
                }
            }
        }
        return distance;
    }

    private float getDistanceBetweenTiles(Tile tileA, Tile tileB) {
        float xDifference = tileB.X - tileA.X;
        float yDifference = tileB.Y - tileA.Y;
        float euclideanDistance = (float) Math.sqrt(xDifference * xDifference + yDifference * yDifference);
        int minHorizontalDistance = Math.abs(Math.min(tileA.X, tileB.X) - Math.max(tileA.X, tileB.X) + WIDTH) % WIDTH;
        float distance = (float) Math.sqrt(euclideanDistance * euclideanDistance + minHorizontalDistance * minHorizontalDistance);
        return distance;
    }

    public List<Tile> getNeighboringTilesByPosition(int xPos, int yPos) {
        List<Tile> neighboringTiles = new ArrayList<Tile>();
        for (int x = -1; x <= 1; x++) {
            if (isColumnOnGrid(xPos + x)) {
                List<Tile> column = tiles.get(xPos + x);
                for (int y = -1; y <= 1; y++) {
                    if (x != 0 || y != 0) {
                        neighboringTiles.add(column.get(
                            isRowOnGrid(yPos + y) ? yPos + y : getModulo(yPos + y, HEIGHT)
                        ));
                    }
                }
            }
        }
        return neighboringTiles;
    }

    public Elevation getElevationByIndex(int index) throws IllegalArgumentException {
        for (Elevation elevation : Elevation.values()) {
            if (elevation.INDEX == index) {
                return elevation;
            }
        }
        throw new IllegalArgumentException("Index " + index + " cannot be found in Elevation.");
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
        return ThreadLocalRandom.current().nextInt(minimum, maximum + 1);
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

    private boolean isIntInRange(int number, int mininum, int maximum) {
        return mininum <= number && number <= maximum;
    }

    private boolean isFloatInRange(float number, float minimum, float maximum) {
        return minimum <= number && number <= maximum;
    }

    private boolean isMaxElevationMarginFulfilled() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Tile tile = getTileByPosition(x, y);
                int tileIndex = tile.getElevation().INDEX;
                for (Tile neighbor : getNeighboringTilesByPosition(tile.X, tile.Y)) {
                    int neighborIndex = neighbor.getElevation().INDEX;
                    if (!isIntInRange(tileIndex, neighborIndex - MAX_ELEVATION_MARGIN, neighborIndex + MAX_ELEVATION_MARGIN)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Getters and Setters ============================================================================================================

    public Map<Integer, List<Tile>> getTiles() { return tiles; }
}
