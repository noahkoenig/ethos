package com.noahkoenig.ethos.grid;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public GameMap () {
		tiledMap = new TmxMapLoader().load("tiles/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void render (OrthographicCamera camera, SpriteBatch batch) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
	}

	public void dispose () {
		tiledMap.dispose();
	}

	public Biome getTileTypeByCoordinate (int layer, int col, int row) {
		System.out.println(layer + " " + row + " " + col);
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getCell(col, row);
		
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			if (tile != null) {
				return Biome.getBiomeById((byte) tile.getId());
			}
		}
		return null;
	}

	public Biome getBiomeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int) (x / Biome.TILE_SIZE), (int) (y / Biome.TILE_SIZE));
	}

	public int getWidth () {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}

	public int getHeight () {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}

	public int getLayers() {
		return tiledMap.getLayers().getCount();
	}
}
