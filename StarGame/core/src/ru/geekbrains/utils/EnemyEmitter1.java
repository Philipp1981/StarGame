package ru.geekbrains.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.game.Enemy;

public class EnemyEmitter1 {
    private static final float ENEMY_MID_HEIGHT = 0.1f;
    private static final float ENEMY_MID_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_MID_BULLET_VY = -0.45f;
    private static final int ENEMY_MID_DAMAGE = 3;
    private static final float ENEMY_MID_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MID_HP = 2;

    private Vector2 enemyMidV = new Vector2(0, -0.15f);
    private TextureRegion[] enemyMidRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter1(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy1");
        this.enemyMidRegion = Regions.split(textureRegion, 1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }
    public void dispose() {
        this.dispose();
    }
    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    enemyMidRegion,
                    enemyMidV,
                    bulletRegion,
                    ENEMY_MID_BULLET_HEIGHT,
                    ENEMY_MID_BULLET_VY,
                    ENEMY_MID_DAMAGE,
                    ENEMY_MID_RELOAD_INTERVAL,
                    ENEMY_MID_HEIGHT,
                    ENEMY_MID_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}

