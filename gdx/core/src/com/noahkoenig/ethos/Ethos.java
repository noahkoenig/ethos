package com.noahkoenig.ethos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.noahkoenig.ethos.gamemap.GameMap;
import com.noahkoenig.ethos.gamemap.grid.Tile;

public class Ethos extends ApplicationAdapter {
	
    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    GameMap gameMap;
    float deltaX, deltaY;
    boolean renderMap = false;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.getCamera().far = .5f;
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        gameMap = new GameMap();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                float updatedZoom = camera.zoom + amountY * 0.05f;
                if (0.1 < updatedZoom && updatedZoom < 2) {
                    camera.zoom = updatedZoom;
                    camera.update();
                    return true;
                }
                return false;
            }
        });
        System.out.println("Welcome to Ethos! A .tmx map has been generated. Press L to load the map into the viewport.");
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        if (renderMap) {
            gameMap.render(camera, batch);
        }
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    private void handleInput() {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            camera.translate(-Gdx.input.getDeltaX() * camera.zoom, -Gdx.input.getDeltaY() * camera.zoom);
            camera.update();
        } else if (Gdx.input.justTouched()) {
            Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Tile tile = gameMap.getGrid().getTileByPosition((int) (pos.x / Tile.SIZE), (int) (pos.y / Tile.SIZE));
            if (tile != null) {
                System.out.println("Clicked Tile: "
                + "Position " + tile.X + " " + tile.Y
                + ", Biome " + tile.getBiome()
                + ", Elevation " + tile.getElevation().INDEX
                + ", Terrain " + tile.getTerrain());
            }
        } else if (Gdx.input.isKeyPressed(Keys.L)) {
            gameMap.loadTmxFile();
            renderMap = true;
        }
    }
}
