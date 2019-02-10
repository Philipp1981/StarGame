package ru.geekbrains.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.Explosion;
import ru.geekbrains.sprite.game.Ship;

public class EnemyEmitter extends Ship {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.4f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_SMALL_HP = 1;
    Sound shootSound1 = Gdx.audio.newSound(Gdx.files.internal("shootSound1.mp3"));
    Music shipSound1 = Gdx.audio.newMusic(Gdx.files.internal("shipSound1.mp3"));

    private static final float ENEMY_MIDDLE_HEIGHT = 0.12f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.35f;
    private static final int ENEMY_MIDDLE_DAMAGE = 5;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MIDDLE_HP = 5;
    Sound shootSound2 = Gdx.audio.newSound(Gdx.files.internal("shootSound2.mp3"));
    Music shipSound2 = Gdx.audio.newMusic(Gdx.files.internal("shipSound2.mp3"));



    private static final float ENEMY_BIG_HEIGHT = 0.17f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.035f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_BIG_HP = 10;
    Sound shootSound3 = Gdx.audio.newSound(Gdx.files.internal("shootSound3.mp3"));
    Music shipSound3 = Gdx.audio.newMusic(Gdx.files.internal("shipSound3.mp3"));

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMiddleV = new Vector2(0, -0.15f);
    private Vector2 enemyBigV = new Vector2(0, -0.1f);
    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 3f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private int level;
    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion0, 1,2,2);
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.enemyMiddleRegion = Regions.split(textureRegion1, 1,2,2);
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureRegion2, 1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;


    }
    public void boom() {
            Explosion explosion = explosionPool.obtain();
            explosion.set(getHeight(), pos);
        }



    public void destroy() {
        super.destroy();
        boom();
    }

    public void generate(float delta, int frags) {
        level = frags / 100 + 1;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.4f) {
                shipSound1.play();
                shipSound1.setVolume(1f);
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE+level-1,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP,
                        shootSound1,
                        shipSound1
                );
            } else if (type <0.8f)
            {
                shipSound2.play();
                shipSound2.setVolume(1f);
                enemy.set(
                        enemyMiddleRegion,
                        enemyMiddleV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_DAMAGE+level-1,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HEIGHT,
                        ENEMY_MIDDLE_HP,
                        shootSound2,
                        shipSound2
                );
            } else {
                shipSound3.play();
                shipSound3.setVolume(1f);
                enemy.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE+level-1,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP,
                        shootSound3,
                        shipSound3
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

