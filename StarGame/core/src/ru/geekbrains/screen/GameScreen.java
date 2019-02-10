package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.game.Bullet;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.Explosion;
import ru.geekbrains.sprite.game.MainShip;
import ru.geekbrains.sprite.menu.NewGamebtn;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.Font;


public class GameScreen extends Base2DScreen {
    private static final String FRAGS = "SCORES: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "LEVEL: ";

    private Game game;
    private TextureAtlas atlas, atlas1;
    private Texture bg, health;
    private Background background;
    private Star star[];
    private MainShip mainShip;
    private GameOver gameOver;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private NewGamebtn newGamebtn;
    private EnemyEmitter enemyEmitter;
    private GameScreen gameScreen;
    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    int frags = 0;

        @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        health=new Texture("textures/2.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlas1 = new TextureAtlas("textures/test.pack");
        music1.stop();
        newGamebtn = new NewGamebtn(atlas, this);
        star = new Star[64];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
        gameOver = new GameOver(atlas);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
            this.font = new Font("font/font.fnt", "font/font.png");
            this.font.setSize(0.015f);

        }


    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if(mainShip.isDestroyed()) {
                enemy.destroy();
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if(mainShip.isDestroyed()) {
                bullet.destroy();
            }
        }
        draw();
    }

    private void update(float delta) {
        for (Star aStar : star) {
            aStar.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
        }

        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed())
        enemyEmitter.generate(delta, frags);
    }

    private void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                mainShip.boom();
                frags=frags+enemy.getDamage();
                return;
            }

        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                mainShip.boom();

                bullet.destroy();
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(mainShip.getDamage());
                    if(enemy.isDestroyed()) frags=frags+enemy.getDamage();
                    bullet.destroy();
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
        }else if(mainShip.isDestroyed()){
            gameOver.draw(batch);
            newGamebtn.draw(batch);
        }

        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        printInfo();
        float thick = mainShip.getHp()*0.0009f;
        batch.draw(health,-0.009f,worldBounds.getTop()-0.026f, thick, 0.02f);
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft()+0.01f, worldBounds.getTop()-0.01f);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x-0.05f, worldBounds.getTop()-0.01f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight()-0.07f, worldBounds.getTop()-0.01f, Align.right);

    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
        }
        gameOver.resize(worldBounds);
        newGamebtn.resize(worldBounds);
        mainShip.resize(worldBounds);

    }

    @Override
    public void dispose() {
        bg.dispose();
        health.dispose();
        atlas.dispose();
        atlas1.dispose();
        bulletPool.dispose();
        enemyEmitter.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        music1.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        }
        newGamebtn.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        } else if(mainShip.isDestroyed()) {newGamebtn.touchUp(touch, pointer); }
        return super.touchUp(touch, pointer);
    }

    public void NewGamebtn() {
        mainShip.newGamebtn();
        frags = 0;
        enemyEmitter.setLevel(1);
//        pos.x = worldBounds.pos.x;
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}



//    private TextureAtlas atlas, atlas1;
//    private Texture bg;
//    private Background background;
//    private Star star[];
//    private MainShip mainShip;
//    private BulletPool bulletPool;
//    Sound shootSound;
//    Sound shipSound;
//    private Vector2 touch=new Vector2();
//    private Matrix3 screenToWorlds;
//    private Rect screenBounds;
//    private ExplosionPool explosionPool;
//    private EnemyPool enemyPool;
//    private EnemyEmitter enemyEmitter;
//    private EnemyEmitter1 enemyEmitter1;
//    private EnemyEmitter2 enemyEmitter2;
//
//    @Override
//    public void show() {
//        super.show();
//        bg = new Texture("textures/bg.png");
//        background = new Background(new TextureRegion(bg));
//        atlas = new TextureAtlas("textures/mainAtlas.tpack");
//        atlas1 = new TextureAtlas("textures/test.pack");
//        star = new Star[100];
//        for (int i = 0; i < star.length; i++) {
//            star[i] = new Star(atlas1);
//        }
//        bulletPool=new BulletPool();
//        explosionPool=new ExplosionPool(atlas);
//        enemyPool=new EnemyPool(bulletPool);
//        mainShip=new MainShip(atlas, bulletPool);
//        enemyEmitter=new EnemyEmitter(atlas, enemyPool, worldBounds);
//        enemyEmitter1=new EnemyEmitter(atlas, enemyPool, worldBounds);
//        enemyEmitter2=new EnemyEmitter(atlas, enemyPool, worldBounds);
//    }
//
//    @Override
//    public void render(float delta) {
//        super.render(delta);
//        update(delta);
//        deleteAllDestroyed();
//        draw();
//    }
//
//    public void update(float delta) {
//        for (int i = 0; i < star.length; i++) {
//            star[i].update(delta);
//        }
//        mainShip.update(delta);
//        bulletPool.updateActiveSprites(delta);
//        explosionPool.updateActiveSprites(delta);
//        enemyPool.updateActiveSprites(delta);
//        enemyEmitter.generate(delta);
//        enemyEmitter1.generate(delta);
//        enemyEmitter2.generate(delta);
//        enemyEmitter2.update(delta);
//
//
//    }
//
//    public void deleteAllDestroyed() {
//        bulletPool.freeAllDestroyedActiveSprites();
//        explosionPool.freeAllDestroyedActiveSprites();
//        enemyPool.freeAllDestroyedActiveSprites();
//
//    }
//
//    public void draw() {
//        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        background.draw(batch);
//        for (int i = 0; i < star.length; i++) {
//            star[i].draw(batch);
//        }
//        mainShip.draw(batch);
//        bulletPool.drawActiveSprites(batch);
//        explosionPool.drawActiveSprites(batch);
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
//        explosionPool.dispose();
//        enemyPool.dispose();
//        mainShip.dispose();
//        enemyEmitter.dispose();
//        enemyEmitter1.dispose();
//        enemyEmitter2.dispose();
//        super.dispose();
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        mainShip.keyDown(keycode);
//        return super.keyDown(keycode);
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        mainShip.keyUp(keycode);
//        return super.keyUp(keycode);
//    }
//
//    @Override
//    public boolean touchDown(Vector2 touch, int pointer) {
//        Explosion explosion = explosionPool.obtain();
//        explosion.set(0.15f, touch);
//        mainShip.touchDown(touch, pointer);
//        return super.touchDown(touch, pointer);
//    }
//
//    @Override
//    public boolean touchUp(Vector2 touch, int pointer) {
//        mainShip.touchUp(touch, pointer);
//        return super.touchUp(touch, pointer);
//    }
//}
//







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

