package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class StopBtn extends ScaledTouchUpButton {

    private Rect worldBounds;
    private Vector2 point = new Vector2();

    public StopBtn(TextureAtlas atlas) {
        super(atlas.findRegion("btnStop"));
        setHeightProportion(0.2f);
//        point.set(-0.35f, 0.47f);
//        pos.mulAdd(point, 1f);
    }

    @Override
    public void update(float delta) {
    }



    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setTop(worldBounds.getTop()-0.35f);
        setLeft(worldBounds.getLeft()+0.1f);
    }

    @Override
    public void action() {
    //    Gdx.audio();
        Gdx.app.exit();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

//    public boolean touchUp(Vector2 touch, int pointer) {
////        stopBtn.touchUp(touch, pointer);
////        playBtn.touchUp(touch, pointer);
//        return super.touchUp(touch, pointer);
//    }
}

