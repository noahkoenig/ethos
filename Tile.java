public class Tile {
    /* between -5 and 10? 0 should be sea level
     * land tiles can also be at 0 or lower, but only if they don't neighbour a water tile
     * water tiles (like lakes) can also be at 1 or higher
     * higher index should also let the biome be cooler?
     */
    int heightIndex;
    Biome biome;
    Terrain terrain;

    Tile(int heightIndex, Biome biome, Terrain terrain) {
        this.heightIndex = heightIndex;
        this.biome = biome;
        this.terrain = terrain;
    }

    public int getHeightIndex() { return heightIndex; }
    public void setHeightIndex(int heightIndex) { this.heightIndex = heightIndex; }

    public Biome getBiome() { return biome; }
    public void setBiome(Biome biome) { this.biome = biome; }

    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }
}
