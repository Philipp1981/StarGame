package ru.geekbrains.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends Sprite {


    private Rect worldBounds;

    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 v1 = new Vector2(0, 0.5f);
    private final Vector2 v2 = new Vector2(0, 0);
    private Vector2 v = new Vector2();
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private boolean isPressedUp;
    private boolean isPressedDown;
    private Vector2 touch = new Vector2();
    private BulletPool bulletPool;
    Sound shootSound;
    Sound shipSound;
    private TextureRegion bulletRegion;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion=atlas.findRegion("bulletMainShip");
        this.bulletPool=bulletPool;
        setHeightProportion(0.15f);
        shootSound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));
        Sound shipSound = Gdx.audio.newSound(Gdx.files.internal("shipSound.mp3"));
    //    shipSound.play(0.5f);
        long d = shipSound.play(0.5f);
        shipSound.setPitch(d, 1.5f);
        shipSound.setLooping(d, true);


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if(getRight() > worldBounds.getRight()) setRight(worldBounds.getRight());
        if(getLeft() < worldBounds.getLeft()) setLeft(worldBounds.getLeft());
//        if(getTop() > worldBounds.getTop()-0.2f) setTop(worldBounds.getTop()-0.2f);
//        if(getBottom() < worldBounds.getBottom()) setBottom(worldBounds.getBottom());
    }

    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;

//            case Input.Keys.W:
//            case Input.Keys.UP:
//                isPressedUp = true;
//                moveUp();
//                break;
//            case Input.Keys.Z:
//            case Input.Keys.DOWN:
//                isPressedDown = true;
//                moveDown();
//                break;

            case Input.Keys.E:
                case Input.Keys.P:
                shoot();
                break;
        }
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       shoot();
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pos.x<0.1f) moveRight();
        if(pos.x>0.1f) moveLeft();
        return touchDragged(touch, pointer);
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }


//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        shoot();
//        return false;
//    }

        public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                isPressedUp = false;
                if (isPressedDown) {
                    moveDown();
                } else {
                    stop();
                }
                break;
            case Input.Keys.Z:
            case Input.Keys.DOWN:
                isPressedDown = false;
                if (isPressedUp) {
                    moveUp();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
      v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveUp() {
         v.set(v1);

    }

    private void moveDown() {
        v.set(v1).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot(){
        Bullet bullet=bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, new Vector2(0, 0.95f), 0.01f, worldBounds, 1);
        shootSound.play(0.7f);
    }
}
