package com.tysonmak.entity.manager;

import com.tysonmak.application.Application;
import com.tysonmak.states.manager.GameStateManager;
import com.tysonmak.tilemap.Tile;
import com.tysonmak.tilemap.TileMap;

import java.awt.*;

public abstract class Entity {

    protected Application app;
    protected GameStateManager gsm;

    protected float x;
    protected float y;

    protected float velX;
    protected float velY;

    protected int width;
    protected int height;

    protected TileMap map;

    public Entity(Application app, GameStateManager gsm, TileMap map, float x, float y, int width, int height) {
        this.app = app;
        this.gsm = gsm;
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
        this.width = width;
        this.height = height;
        this.map = map;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public Rectangle getRectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public boolean intersects(Entity entity) {
        return getRectangle().intersects(entity.getRectangle());
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public void setGsm(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
