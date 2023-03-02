package grid;

public class Tile {

    /* between -5 and 10? 0 should be sea level
     * land tiles can also be at 0 or lower, but only if they don't neighbour a water tile
     * water tiles (like lakes) can also be at 1 or higher
     * higher index should also let the biome be cooler?
     */
    int elevation;
    Biome biome;
    Terrain terrain;
    int x;
    int y;

    Tile(int elevation, Biome biome, Terrain terrain, int y, int x) {
        this.elevation = elevation;
        this.biome = biome;
        this.terrain = terrain;
        this.x = x;
        this.y = y;
    }

    public int getElevation() { return elevation; }
    public void setElevation(int elevation) { this.elevation = elevation; }

    public Biome getBiome() { return biome; }
    public void setBiome(Biome biome) { this.biome = biome; }

    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }

    public int getX() { return x; }

    public int getY() { return y; }
}
