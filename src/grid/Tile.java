package src.grid;

public class Tile {

    Elevation elevation;
    Biome biome;
    Terrain terrain;
    int x;
    int y;

    Tile(Elevation elevation, Biome biome, Terrain terrain, int y, int x) {
        this.elevation = elevation;
        this.biome = biome;
        this.terrain = terrain;
        this.x = x;
        this.y = y;
    }

    public Elevation getElevation() { return elevation; }
    public void setElevation(Elevation elevation) { this.elevation = elevation; }

    public Biome getBiome() { return biome; }
    public void setBiome(Biome biome) { this.biome = biome; }

    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }

    public int getX() { return x; }

    public int getY() { return y; }
}
