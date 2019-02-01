package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.game.Explosion;
import ru.geekbrains.sprite.game.MainShip;
import ru.geekbrains.sprite.menu.PlayBtn;
import ru.geekbrains.sprite.menu.StopBtn;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.EnemyEmitter1;
import ru.geekbrains.utils.EnemyEmitter2;

public class GameScreen extends Base2DScreen {

    private TextureAtlas atlas, atlas1;
    private Texture bg;
    private Background background;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    Sound shootSound;
    Sound shipSound;
    private Vector2 touch=new Vector2();
    private Matrix3 screenToWorlds;
    private Rect screenBounds;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private EnemyEmitter1 enemyEmitter1;
    private EnemyEmitter2 enemyEmitter2;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlas1 = new TextureAtlas("textures/test.pack");
        star = new Star[100];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas1);
        }
        bulletPool=new BulletPool();
        explosionPool=new ExplosionPool(atlas);
        enemyPool=new EnemyPool(bulletPool);
        mainShip=new MainShip(atlas, bulletPool);
        enemyEmitter=new EnemyEmitter(atlas, enemyPool, worldBounds);
        enemyEmitter1=new EnemyEmitter1(atlas, enemyPool, worldBounds);
        enemyEmitter2=new EnemyEmitter2(atlas, enemyPool, worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        enemyEmitter1.generate(delta);
        enemyEmitter2.generate(delta);
        enemyEmitter2.update(delta);


    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();

    }

    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        enemyEmitter.dispose();
        enemyEmitter1.dispose();
        enemyEmitter2.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.15f, touch);
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}



//    @Override
//    public void render(float delta) {
//        super.render(delta);
//        update(delta);
//        deleteAllDestroyed();
//        draw();
//
//    }
//
//
//    public void update(float delta){
//        for (int i = 0; i < star.length; i++){  star[i].update(delta);}
//        mainShip.update(delta);
//        bulletPool.updateActiveSprites(delta);
//        explosionPool.updateActiveSprites(delta);
//        enemyPool.updateActiveSprites(delta);
//        enemyEmitter.generate(delta);
//        enemyEmitter1.generate(delta);
//        enemyEmitter2.generate(delta);
//    }
//
//    public void deleteAllDestroyed(){
//        bulletPool.freeAllDestroyedActiveSprites();
//        explosionPool.freeAllDestroyedActiveSprites();
//        enemyPool.freeAllDestroyedActiveSprites();
//    }
//
//    public void draw(){
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        background.draw(batch);
//        for (int i = 0; i < star.length; i++) {
//            star[i].draw(batch);
//        }
//        bulletPool.drawActiveSprites(batch);
//        explosionPool.drawActiveSprites(batch);
//        mainShip.draw(batch);
//        enemyPool.drawActiveSprites(batch);
//        batch.end();
//    }
//
//    @Override
//    public void resize(Rect worldBounds) {
//        super.resize(worldBounds);
//        background.resize(worldBounds);
//        for (int i = 0; i < star.length; i++) {
//            star[i].resize(worldBounds);
//        }
//        mainShip.resize(worldBounds);
//    }
//
//    @Override
//    public void dispose() {
//        bg.dispose();
//        atlas.dispose();
//        bulletPool.dispose();
//        shootSound.dispose();
//        shipSound.dispose();
//        explosionPool.dispose();
//        enemyPool.dispose();
//        super.dispose();
//    }
//    @Override
//    public boolean keyDown(int keycode) {
//        mainShip.keyDown(keycode);
//        return super.keyDown(keycode);
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        mainShip.keyUp(keycode);
//
//        return super.keyUp(keycode);
//    }
//    @Override
//    public boolean keyTyped(char character) {
//        return super.keyTyped(character);
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
////        Explosion explosion=explosionPool.obtain();
////        explosion.set(0.15f, touch);
//        mainShip.touchDown(touch, pointer);
//        return mainShip.touchDown(screenX, screenY, pointer, button);
//    }
//
//
//    @Override
//    public boolean touchDown(Vector2 touch, int pointer) {
//        Explosion explosion=explosionPool.obtain();
//        explosion.set(0.15f, touch);
//        mainShip.touchDown(touch,pointer);
//        return super.touchDown(touch, pointer);
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        return super.touchUp(screenX, screenY, pointer, button);
//    }
//
//    @Override
//    public boolean touchUp(Vector2 touch, int pointer) {
//        return super.touchUp(touch, pointer);
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return mainShip.touchDragged(screenX, screenY, pointer);
//    }
//    public boolean touchDragged(Vector2 touch, int pointer) {
//        return false;
//    }
//
//    public GameScreen() {
//
//    }
//}

