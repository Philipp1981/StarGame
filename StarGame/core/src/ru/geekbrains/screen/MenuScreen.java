package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {
private static final float V_LEN=2.5f;
 //   SpriteBatch batch;
    Texture img;
    Texture img1;

    Vector2 pos, v, pos1, v2, v3, v4, v5, v6, v7, v8, v22, v33, touchD, touch, buf;


    @Override
    public void show() {
        super.show();
        img = new Texture("1.jpg");
        img1 = new Texture("badlogic.jpg");
        pos = new Vector2(-0.5f, -0.5f);
        v = new Vector2(0.001f, 0.001f);
//        batch = new SpriteBatch();
//        batch.getProjectionMatrix().idt();

        pos1=new Vector2(0, 0);
        v2=new Vector2(0,0.0002f);
        v22=new Vector2(0.0002f, 0);
        v3=new Vector2(0,-0.0002f);
        v33=new Vector2(-0.0002f, 0);
        v4=new Vector2();
        v5=new Vector2();
        v6=new Vector2();
        v7=new Vector2();
        v8=new Vector2();
        touchD=new Vector2();
        touch=new Vector2();
        buf=new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, -1f, -1f, 2f, 2f);
        batch.draw(img1, pos.x, pos.y, 0.25f, 0.25f);
        batch.end();
        pos.add(v);


        // следуюзий блок-часть ДЗ2, где нужно было реализовать остановку в точке тачдаун, я не осилил, дописал после вебинара
        buf.set(touch);
        if(buf.cpy().sub(pos).len()>V_LEN) {
            pos.add(v);
        }else {
            pos.set(buf);
        }


        pos.add(v4);
        pos.add(v5);
        pos.add(v6);
        pos.add(v7);
        pos.add(v8);


//        if (screenBounds.getWidth() - 0.00025f > pos.x && screenBounds.getHeight() - 0.00025f > pos.y) {
//            pos.add(v);
//        }
    }


// - мой вариант реализации движения к точке тачдауна в дз2 (+после урока дописал Ваш вариант)
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("touchDown: "+screenX+" "+(Gdx.graphics.getHeight()-screenY));
////        touchD=new Vector2(screenX, (Gdx.graphics.getHeight()-screenY));
////        v4 = touchD.sub(pos);
//        touchD.set(screenX-256, (Gdx.graphics.getHeight()-screenY));
//        v4.set(touchD.cpy().sub(pos).setLength(V_LEN));
// //       v4.nor();
//        return super.touchDown(screenX, screenY, pointer, button);
//    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown screenX = " + pos.x + " screenY = " + pos.y);
        touch.set(pos.x, screenBounds.getHeight() - pos.y).mul(screenToWorlds);
        v4 =touch.sub(pos);
        v4.nor();
        return true;//super.touchDown(touch, pointer);
    }

//  не понял как, но получается таскать картинку по экрану тачдрагом
    // следующий блок- управление кнопками-реализовал коряво)))
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
        img.dispose();
        super.dispose();
    }

}

//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//    }
//
//    @Override
//    public void dispose() {
// //       batch.dispose();
//        img.dispose();
//        img1.dispose();
//        super.dispose();
//    }




