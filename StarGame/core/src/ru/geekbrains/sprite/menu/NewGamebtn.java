package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.screen.MenuScreen;

public class NewGamebtn extends ScaledTouchUpButton {
//    private Rect worldBounds;
    private Game game;
    private GameScreen gameScreen;
   private MenuScreen menuScreen;

    public NewGamebtn(TextureAtlas atlas, GameScreen gameScreen) {

        super(atlas.findRegion("button_new_game"));
       this.gameScreen = gameScreen;
        setHeightProportion(0.04f);
        setTop(-0.012f);
    }


//    @Override
//    public void update(float delta) {
//    }
//
//
//    @Override
//    public void resize(Rect worldBounds) {
//        this.worldBounds = worldBounds;
//        setTop(worldBounds.getTop()-0.45f);
//        setRight(worldBounds.getRight()-0.05f);
//    }

    @Override
    public void action() { gameScreen.NewGamebtn();}


//    @Override
//    public boolean touchDown(Vector2 touch, int pointer) {
//        return super.touchDown(touch, pointer);
//    }




//    @Override
//    public boolean touchUp(Vector2 touch, int pointer) {
//        return super.touchUp(touch, pointer);
//    }

}
