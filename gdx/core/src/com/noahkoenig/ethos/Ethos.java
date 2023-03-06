package com.noahkoenig.ethos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.noahkoenig.ethos.grid.Biome;
import com.noahkoenig.ethos.grid.GameMap;

public class Ethos extends ApplicationAdapter {
	
	SpriteBatch batch;
    OrthographicCamera camera;
    GameMap gameMap;
    float deltaX, deltaY;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        gameMap = new GameMap();
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        gameMap.render(camera, batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}

    private void handleInput() {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }
        if (Gdx.input.justTouched()) {
            Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Biome biome = gameMap.getBiomeByLocation(1, pos.x, pos.y);
            if (biome != null) {
                System.out.println("You clicked on tile with id " + biome.getId() + " " + biome.toString());
            }
        }
    }
}
