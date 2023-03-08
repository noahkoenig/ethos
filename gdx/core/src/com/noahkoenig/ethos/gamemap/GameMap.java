package com.noahkoenig.ethos.gamemap;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.noahkoenig.ethos.gamemap.grid.Continents;
import com.noahkoenig.ethos.gamemap.grid.Grid;

public class GameMap {
	
	private static int counter = 0;
	public static final int TILE_SIZE = 16; // in pixels
	private static String fileName;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private Grid grid;
	
	public GameMap () {
		fileName = generateTimestamp() + "_" + counter++;
		grid = new Continents(128, 72, (float) 0.66, (float) 0.5, 128, (float) 0.8);
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

	private static String generateTimestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(date);
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
			xmlWriter.writeAttribute("width", String.valueOf(grid.WIDTH));
			xmlWriter.writeAttribute("height", String.valueOf(grid.HEIGHT));
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
            xmlWriter.writeAttribute("width", String.valueOf(grid.WIDTH));
			xmlWriter.writeAttribute("height", String.valueOf(grid.HEIGHT));
			xmlWriter.writeCharacters("\n");

			xmlWriter.writeStartElement("data");
			xmlWriter.writeAttribute("encoding", "csv");
			StringBuilder csvBuilder = new StringBuilder();
			csvBuilder.append("\n");
			for (int y = grid.HEIGHT - 1; y >= 0; y--) { // csv has 0,0 in the bottom left while our grid has 0,0 at the top left, forcing us to reverse the x here
				for (int x = 0; x < grid.WIDTH; x++) {
					String appendix = (x == grid.WIDTH - 1 && y == 0) ? "" : ",";
                    csvBuilder.append(grid.getTileByPosition(x, y).getBiome().ID + appendix);
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

	public void loadTmxFile() {
		tiledMap = new TmxMapLoader().load("maps/" + fileName + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

    // Getters and Setters ============================================================================================================

	public String getFileName() { return fileName; }

	public Grid getGrid() { return grid; }
}
