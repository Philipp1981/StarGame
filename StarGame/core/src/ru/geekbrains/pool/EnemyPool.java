package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.game.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
    private Sound shootSound1;
    private Sound shipSound1;
    private BulletPool bulletPool;

    public EnemyPool(BulletPool bulletPool) {
        this.shootSound1 = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.shipSound1 = Gdx.audio.newSound(Gdx.files.internal("shipSound.mp3"));
        long d = shipSound1.play(1.9f);
        shipSound1.setPitch(d, 0.2f);
        this.bulletPool = bulletPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shootSound1, shipSound1, bulletPool);
    }

    @Override
    public void dispose() {
        shootSound1.dispose();
        shipSound1.dispose();
        super.dispose();

    }
}

