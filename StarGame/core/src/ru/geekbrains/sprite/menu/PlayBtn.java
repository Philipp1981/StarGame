package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.screen.MenuScreen;

public class PlayBtn extends ScaledTouchUpButton {

    private Rect worldBounds;
//    private Vector2 point = new Vector2();
    private Game game;

    public PlayBtn(TextureAtlas atlas, Game game) {

        super(atlas.findRegion("btnPlay"));
        this.game=game;
        setHeightProportion(0.2f);
//        point.set(0.35f, 0.47f);
//        pos.mulAdd(point, 1f);
    }


    @Override
    public void update(float delta) {
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setTop(worldBounds.getTop()-0.35f);
        setRight(worldBounds.getRight()-0.1f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }


//    @Override
//    public boolean touchUp(Vector2 touch, int pointer) {
////        stopBtn.touchUp(touch, pointer);
////        playBtn.touchUp(touch, pointer);
//        return super.touchUp(touch, pointer);
//    }
}

