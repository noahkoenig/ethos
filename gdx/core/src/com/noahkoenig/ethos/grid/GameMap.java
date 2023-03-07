package com.noahkoenig.ethos.grid;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.noahkoenig.ethos.grid.enums.Biome;

public class GameMap {
	
	private static int counter = 0;
	public static final int TILE_SIZE = 16; // in pixels
	private static String fileName;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private Grid grid;
	
	public GameMap () {
		fileName = generateTimestamp() + "_" + counter++;
		grid = new Grid(128, 72, 66, 0);
		generateTmxFileFromGrid(grid);
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

	private static void generateTmxFileFromGrid (Grid grid) {
		try {
			OutputStream os = new FileOutputStream(new File("gdx/assets/maps/" + fileName + ".tmx"));
    		XMLOutputFactory xmlOutFactory = XMLOutputFactory.newInstance();
    		XMLStreamWriter xmlWriter = xmlOutFactory.createXMLStreamWriter(os, "UTF-8");

			xmlWriter.writeStartDocument("UTF-8", "1.0");
			xmlWriter.writeCharacters("\n");

			xmlWriter.writeStartElement("map");
			xmlWriter.writeAttribute("version", "1.0");
			xmlWriter.writeAttribute("tiledversion", "1.9.2");
			xmlWriter.writeAttribute("orientation", "orthogonal");
			xmlWriter.writeAttribute("renderorder", "right-down");
			xmlWriter.writeAttribute("width", String.valueOf(grid.getWidth()));
			xmlWriter.writeAttribute("height", String.valueOf(grid.getHeight()));
			xmlWriter.writeAttribute("tilewidth", String.valueOf(TILE_SIZE));
			xmlWriter.writeAttribute("tileheight", String.valueOf(TILE_SIZE));
			xmlWriter.writeAttribute("infinite", "0");
			xmlWriter.writeAttribute("nextlayerid", "2");
			xmlWriter.writeAttribute("nextobjectid", "1");
			xmlWriter.writeCharacters("\n");

			xmlWriter.writeEmptyElement("tileset");
            xmlWriter.writeAttribute("firstgid", "1");
            xmlWriter.writeAttribute("source", "tileset.tsx");
			xmlWriter.writeCharacters("\n");

			xmlWriter.writeStartElement("layer");
            xmlWriter.writeAttribute("id", "1");
			xmlWriter.writeAttribute("name", "Tile Layer 1");
            xmlWriter.writeAttribute("width", String.valueOf(grid.getWidth()));
			xmlWriter.writeAttribute("height", String.valueOf(grid.getHeight()));
			xmlWriter.writeCharacters("\n");

			xmlWriter.writeStartElement("data");
			xmlWriter.writeAttribute("encoding", "csv");
			StringBuilder csvBuilder = new StringBuilder();
			csvBuilder.append("\n");
			for (int y = 0; y < grid.getHeight(); y++) {
				List<Tile> tiles = grid.getTiles().get(y);
				for (int x = 0; x < grid.getWidth(); x++) {
					String appendix = (x == grid.getWidth() - 1 && y == grid.getHeight() - 1) ? "" : ",";
                    csvBuilder.append(tiles.get(x).getBiome().ID + appendix);
                }
				csvBuilder.append("\n");
            }
			xmlWriter.writeCharacters(csvBuilder.toString());

			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
			xmlWriter.close();
            os.close();

		} catch (Exception e) {
            e.printStackTrace();
        }
	}

	private static String generateTimestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(date);
    }

	public void loadTmxFile() {
		tiledMap = new TmxMapLoader().load("maps/" + fileName + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	public Biome getBiomeByCoordinate (int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getCell(col, row);
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			if (tile != null) {
				return Biome.getBiomeById((byte) tile.getId());
			}
		}
		return null;
	}

	public Biome getBiomeByLocation (int layer, float x, float y) {
		return getBiomeByCoordinate(layer, (int) (x / TILE_SIZE), (int) (y / TILE_SIZE));
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

	public Grid getGrid() { return grid; }
}
