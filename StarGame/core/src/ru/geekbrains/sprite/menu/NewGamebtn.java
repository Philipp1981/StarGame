package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.screen.MenuScreen;

public class NewGamebtn extends ScaledTouchUpButton {
    private Rect worldBounds;
    private Game game;


    public NewGamebtn(TextureAtlas atlas, Game game) {

        super(atlas.findRegion("button_new_game"));
        this.game=game;
        setHeightProportion(0.1f);

    }


    @Override
    public void update(float delta) {
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setTop(worldBounds.getTop()-0.45f);
        setRight(worldBounds.getRight()-0.05f);
    }

    @Override
    public void action() { game.setScreen(new MenuScreen(game));}


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

//    @Override
//    public boolean touchUp(Vector2 touch, int pointer) {
//        return super.touchUp(touch, pointer);
//    }

}
