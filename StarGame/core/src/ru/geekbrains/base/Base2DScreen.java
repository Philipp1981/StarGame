package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.MatrixUtils;
import ru.geekbrains.math.Rect;

public class Base2DScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    protected Rect screenBounds; // задаем границы  области рисования в пикселях
    protected Rect worldBounds; // задаем границы  области мировых координат
    protected Rect glBounds; // задаем дефолтные границы  проекции мир-gl
    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorlds;

    protected Vector2 touch;

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorlds = new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height){
        System.out.println("resize w= "+width +"h= "+ height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorlds, screenBounds, worldBounds);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }


    @Override
    public void resume(){
        System.out.println("resume ");
    }

    @Override
    public void hide(){
        System.out.println("hide ");
        dispose();
    }

    @Override
    public void dispose(){
        System.out.println("dispose ");

    }

    @Override
    public boolean keyDown(int keycode){
        System.out.println("keyDown keycode= "+keycode);
        return false;

    }
    @Override
    public boolean keyUp(int keycode){
        System.out.println("keyUp keycode= "+keycode);
        return false;
    }
    @Override
    public boolean keyTyped(char character){
        System.out.println("keyType keycode= "+character);
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);
        touchDown(touch, pointer);
        return false;
    }


    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
