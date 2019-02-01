package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.Ship;

public class EnemyEmitter2 extends Ship {
    private static final float ENEMY_BIG_HEIGHT = 0.13f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.019f;
    private static final float ENEMY_BIG_BULLET_VY = -0.5f;
    private static final int ENEMY_BIG_DAMAGE = 5;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 6f;
    private static final int ENEMY_BIG_HP = 3;

    private Vector2 enemyBigV = new Vector2(0, -0.1f);
    private TextureRegion[] enemyBigRegion;

    private TextureRegion bulletRegion;
    private float generateInterval = 5f;
    private float generateTimer;
    private Enemy enemy;
    private EnemyPool enemyPool;
 //   private boolean isDestroyed;
    private Rect worldBounds;

    public EnemyEmitter2(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureRegion, 1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }



//    @Override
//    public void update(float delta) {
//        reloadTimer += delta;
//        if (reloadTimer >= reloadInterval) {
//            reloadTimer = 0f;
//            shoot();
//        }
//        if (getBottom() > worldBounds.getBottom()+1f) {
//            destroy();
//        }
//    }

    public void dispose() {
        this.dispose();
    }
    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            enemy = enemyPool.obtain();
            enemy.set(
                    enemyBigRegion,
                    enemyBigV,
                    bulletRegion,
                    ENEMY_BIG_BULLET_HEIGHT,
                    ENEMY_BIG_BULLET_VY,
                    ENEMY_BIG_DAMAGE,
                    ENEMY_BIG_RELOAD_INTERVAL,
                    ENEMY_BIG_HEIGHT,
                    ENEMY_BIG_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());

        }
    }
}