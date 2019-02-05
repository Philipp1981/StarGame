package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.menu.ScaledTouchUpButton;

public class NewGame extends Sprite {

    private Rect worldBounds;

    public NewGame(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.1f);

    }

    @Override
    public void update(float delta) {
    }


    @Override
    public void resize(Rect worldBounds) {
       this.worldBounds = worldBounds;
        setTop(worldBounds.getTop() - 0.12f);
        setLeft(worldBounds.getLeft() + 0.05f);
    }
}