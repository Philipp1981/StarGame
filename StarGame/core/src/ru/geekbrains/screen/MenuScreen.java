package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.NewGame;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.menu.PlayBtn;
import ru.geekbrains.sprite.menu.StopBtn;

public class MenuScreen extends Base2DScreen {

    private TextureAtlas atlas, atlas1;
    private Texture bg;
    private NewGame newGame;
    private Background background;
    private Star star[];
    private StopBtn stopBtn;
    private PlayBtn playBtn;
    private boolean playPressed;
    private boolean stopPressed;
    private Game game;


    public MenuScreen (Game game){

        this.game=game;

    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/test.pack");
        atlas1 = new TextureAtlas("textures/mainAtlas.tpack");
        newGame = new NewGame(atlas1);
        stopBtn = new StopBtn(atlas);
        playBtn = new PlayBtn(atlas, game);

        star = new Star[156];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);

        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        newGame.draw(batch);
        stopBtn.draw(batch);
        playBtn.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        stopBtn.resize(worldBounds);
        playBtn.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlas1.dispose();
        music1.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        stopBtn.touchDown(touch, pointer);
        playBtn.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        stopBtn.touchUp(touch, pointer);
        playBtn.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

}
