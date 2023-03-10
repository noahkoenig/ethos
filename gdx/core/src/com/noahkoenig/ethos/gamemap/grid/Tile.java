package com.noahkoenig.ethos.gamemap.grid;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.noahkoenig.ethos.gamemap.enums.Biome;
import com.noahkoenig.ethos.gamemap.enums.Elevation;
import com.noahkoenig.ethos.gamemap.enums.Terrain;

public class Tile implements TiledMapTile {

    private Elevation elevation;
    private Biome biome;
    private Terrain terrain;
    public final int X;
    public final int Y;
    public static final int SIZE = 16; // in pixels

    public Tile(Elevation elevation, Biome biome, Terrain terrain, int x, int y) {
        this.elevation = elevation;
        this.biome = biome;
        this.terrain = terrain;
        this.X = x;
        this.Y = y;
    }

    public boolean isElevationAllowedForBiome() { 
        return biome.MIN_ELEVATION <= elevation.INDEX && elevation.INDEX <= biome.MAX_ELEVATION;
    }

    // Getters and Setters ============================================================================================================

    public Elevation getElevation() { return elevation; }
    public void setElevation(Elevation elevation) { this.elevation = elevation; }

    public Biome getBiome() { return biome; }
    public void setBiome(Biome biome) { this.biome = biome; }

    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }

    // Unused TiledMapTile functions ============================================================================================================

    @Override public int getId() { return 0; }
    @Override public void setId(int id) {}
    @Override public BlendMode getBlendMode() { return null; }
    @Override public void setBlendMode(BlendMode blendMode) {}
    @Override public TextureRegion getTextureRegion() { return null; }
    @Override public void setTextureRegion(TextureRegion textureRegion) {}
    @Override public float getOffsetX() { return 0; }
    @Override public void setOffsetX(float offsetX) {}
    @Override public float getOffsetY() { return 0; }
    @Override public void setOffsetY(float offsetY) {}
    @Override public MapProperties getProperties() {return null; }
    @Override public MapObjects getObjects() { return null; }
}
