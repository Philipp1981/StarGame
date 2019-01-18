package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    SpriteBatch batch;
    Texture img;
    Texture img1;

    Vector2 pos, pos1, v, v2, v3, v4, v5, v6, v7, v8, v22, v33, touchD;


    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("1.jpg");
        img1 = new Texture("badlogic.jpg");
        pos = new Vector2(2,2);
        pos1=new Vector2(0, 0);
        v=new Vector2(1,1);
        v2=new Vector2(0,2);
        v22=new Vector2(2, 0);
        v3=new Vector2(0,-2);
        v33=new Vector2(-2, 0);
        v4=new Vector2();
        v5=new Vector2();
        v6=new Vector2();
        v7=new Vector2();
        v8=new Vector2();
        touchD=new Vector2();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setColor(0.7f, 0.7f, 0.7f, 0.9f);
        batch.draw(img, 0, 0);
        batch.draw(img1, pos.x, pos.y);

        batch.end();


 //       pos.add(v);
        pos.add(v4);
        pos.add(v5);
        pos.add(v6);
        pos.add(v7);
        pos.add(v8);


//        if (Gdx.graphics.getWidth() - 256 > pos.x && Gdx.graphics.getHeight() - 256 > pos.y) {
//            pos.add(v);
//        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown: "+screenX+" "+(Gdx.graphics.getHeight()-screenY));
        touchD=new Vector2(screenX, (Gdx.graphics.getHeight()-screenY));
        v4 = touchD.sub(pos);
        v4.nor();
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode= " + keycode);

            switch (keycode)
            {
                case Input.Keys.UP:
                v5 = v2.sub(pos1);
                v5.nor();
                break;
                case Input.Keys.DOWN:
                    v6 = v3.sub(pos1);
                    v6.nor();
                    break;
                case Input.Keys.RIGHT:
                    v7 = v22.sub(pos1);
                    v7.nor();
                    break;
                case Input.Keys.LEFT:
                    v8 = v33.sub(pos1);
                    v8.nor();
                    break;
            }
            return true;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        img1.dispose();
        super.dispose();
    }


}

