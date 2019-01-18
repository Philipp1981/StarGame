package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img1;
	TextureRegion region;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("1.jpg");
		img1 = new Texture("badlogic.jpg");
		region=new TextureRegion(img1, 45, 55, 150, 150);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(0.3f, 0.4f, 0.5f, 0.9f);
		batch.draw(img, 0, 0);
		batch.draw(img1, 380, 220);
		batch.draw(img1, 0, 0);
		batch.draw(region, 180, 300);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		img1.dispose();
	}
}
