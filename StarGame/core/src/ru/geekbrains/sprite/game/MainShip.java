package ru.geekbrains.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;

    private final Vector2 v0 = new Vector2(0.5f, 0);

    private boolean isPressedLeft;
    private boolean isPressedRight;
    Music music;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private boolean isDestroyed;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.reloadInterval = 0.2f;
 //       this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));

        music = Gdx.audio.newMusic(Gdx.files.internal("shipSound.mp3"));
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
   //     Sound shipSound = Gdx.audio.newSound(Gdx.files.internal("shipSound.mp3"));
        setHeightProportion(0.15f);
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.hp = 10;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());

            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
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
        }
        return false;
    }

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
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();

        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return super.touchUp(touch, pointer);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
        isDestroyed=true;
        music.stop();
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

}



//
//import com.badlogic.gdx.Gdx;
//
//import com.badlogic.gdx.Input;
//
//import com.badlogic.gdx.audio.Sound;
//
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//
//import com.badlogic.gdx.math.Matrix3;
//
//import com.badlogic.gdx.math.Vector2;
//
//
//
//import ru.geekbrains.base.Sprite;
//
//import ru.geekbrains.math.Rect;
//
//import ru.geekbrains.pool.BulletPool;
//
//
//
//public class MainShip extends Ship {
//
//
//
//
//
//    private Rect worldBounds;
//
//
//
//    private final Vector2 v0 = new Vector2(0.5f, 0);
//
//    private final Vector2 v1 = new Vector2(0, 0.5f);
//
//    private final Vector2 v2 = new Vector2(0, 0);
//
//    private Vector2 v = new Vector2();
//
//    private boolean isPressedLeft;
//
//    private boolean isPressedRight;
//
//    private boolean isPressedUp;
//
//    private boolean isPressedDown;
//
//    private Vector2 touch = new Vector2();
//
//    private BulletPool bulletPool;
//
//    Sound shootSound;
//
//    Sound shipSound;
//
//    private TextureRegion bulletRegion;
//
//
//
//
//
//    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
//
//        super(atlas.findRegion("main_ship"), 1, 2, 2);
//
//        this.bulletRegion=atlas.findRegion("bulletMainShip");
//
//        this.bulletPool=bulletPool;
//
//        setHeightProportion(0.15f);
//
//        shootSound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));
//
//        Sound shipSound = Gdx.audio.newSound(Gdx.files.internal("shipSound.mp3"));
//
//        //    shipSound.play(0.5f);
//
//        long d = shipSound.play(0.5f);
//
//        shipSound.setPitch(d, 1.5f);
//
//        shipSound.setLooping(d, true);
//
//    }
//
//
//
//    @Override
//
//    public void resize(Rect worldBounds) {
//
//        super.resize(worldBounds);
//
//        this.worldBounds = worldBounds;
//
//        setBottom(worldBounds.getBottom() + 0.05f);
//
//    }
//
//
//
//    @Override
//
//    public void update(float delta) {
//
//        super.update(delta);
//
//        pos.mulAdd(v, delta);
//
//        if(getRight() > worldBounds.getRight()) setRight(worldBounds.getRight());
//
//        if(getLeft() < worldBounds.getLeft()) setLeft(worldBounds.getLeft());
//
////        if(getTop() > worldBounds.getTop()-0.2f) setTop(worldBounds.getTop()-0.2f);
//
////        if(getBottom() < worldBounds.getBottom()) setBottom(worldBounds.getBottom());
//
//    }
//
//
//
//    public boolean keyDown(int keycode) {
//
//
//
//        switch (keycode) {
//
//            case Input.Keys.A:
//
//            case Input.Keys.LEFT:
//
//                isPressedLeft = true;
//
//                moveLeft();
//
//                break;
//
//            case Input.Keys.D:
//
//            case Input.Keys.RIGHT:
//
//                isPressedRight = true;
//
//                moveRight();
//
//                break;
//
//
//
////            case Input.Keys.W:
//
////            case Input.Keys.UP:
//
////                isPressedUp = true;
//
////                moveUp();
//
////                break;
//
////            case Input.Keys.Z:
//
////            case Input.Keys.DOWN:
//
////                isPressedDown = true;
//
////                moveDown();
//
////                break;
//
//
//
//            case Input.Keys.E:
//
//            case Input.Keys.P:
//
//                shoot();
//
//                break;
//
//        }
//
//        return false;
//
//    }
//
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//
//        shoot();
//
//        return false;
//
//    }
//
//
//
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//
//        if(pos.x<0.1f) moveRight();
//
//        if(pos.x>0.1f) moveLeft();
//
//        return touchDragged(touch, pointer);
//
//    }
//
//
//
//    public boolean touchDragged(Vector2 touch, int pointer) {
//
//        return false;
//
//    }
//
//
//
//
//
////    public boolean touchDragged(int screenX, int screenY, int pointer) {
//
////        shoot();
//
////        return false;
//
////    }
//
//
//
//    public boolean keyUp(int keycode) {
//
//        switch (keycode) {
//
//            case Input.Keys.A:
//
//            case Input.Keys.LEFT:
//
//                isPressedLeft = false;
//
//                if (isPressedRight) {
//
//                    moveRight();
//
//                } else {
//
//                    stop();
//
//                }
//
//                break;
//
//            case Input.Keys.D:
//
//            case Input.Keys.RIGHT:
//
//                isPressedRight = false;
//
//                if (isPressedLeft) {
//
//                    moveLeft();
//
//                } else {
//
//                    stop();
//
//                }
//
//                break;
//
//            case Input.Keys.W:
//
//            case Input.Keys.UP:
//
//                isPressedUp = false;
//
//                if (isPressedDown) {
//
//                    moveDown();
//
//                } else {
//
//                    stop();
//
//                }
//
//                break;
//
//            case Input.Keys.Z:
//
//            case Input.Keys.DOWN:
//
//                isPressedDown = false;
//
//                if (isPressedUp) {
//
//                    moveUp();
//
//                } else {
//
//                    stop();
//
//                }
//
//                break;
//
//        }
//
//        return false;
//
//    }
//
//
//
//    private void moveRight() {
//
//        v.set(v0);
//
//    }
//
//
//
//    private void moveLeft() {
//
//        v.set(v0).rotate(180);
//
//    }
//
//
//
//    private void moveUp() {
//
//        v.set(v1);
//
//
//
//    }
//
//
//
//    private void moveDown() {
//
//        v.set(v1).rotate(180);
//
//    }
//
//
//
//    private void stop() {
//
//        v.setZero();
//
//    }
//
//
//
//    public void shoot(){
//
//        Bullet bullet=bulletPool.obtain();
//
//        bullet.set(this, bulletRegion, pos, new Vector2(0, 0.95f), 0.01f, worldBounds, 1);
//
//        shootSound.play(0.7f);
//
//    }
//
//}
