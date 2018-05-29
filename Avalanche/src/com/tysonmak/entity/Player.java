package com.tysonmak.entity;

import com.tysonmak.application.Application;
import com.tysonmak.audio.AudioPlayer;
import com.tysonmak.entity.manager.Entity;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.FontLoader;
import com.tysonmak.states.manager.GameStateManager;
import com.tysonmak.tilemap.Tile;
import com.tysonmak.tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private boolean left;
    private boolean right;

    private float velocityX;
    private float downhillVelocity;

    private Rectangle bounds;

    private BufferedImage animation;

    private int count;

    private AudioPlayer crash, collect;
    private boolean alive = true;
    private boolean finished = false;
    private int score;

    private int timesPlayed;

    private float multiplier;

    public Player(Application app, GameStateManager gsm, TileMap map) {
        super(app, gsm, map, app.getWidth() / 2 + 32, 0, 64, 128);
        this.map = map;

        left = true;
        right = false;

        downhillVelocity = 4;

        bounds = new Rectangle();

        bounds.x = 24;
        bounds.y = 84;
        bounds.width = 20;
        bounds.height = 12;

        animation = Assets.playerDown;

        count = 0;

        crash = new AudioPlayer("/sound/tilechange.wav");
        collect = new AudioPlayer("/sound/collect.wav");
        timesPlayed = 0;

        score = 0;
    }

    @Override
    public void update() {
        x += velocityX;
        y += downhillVelocity;

        if(app.getKeys().keyPressed(KeyEvent.VK_SPACE)) {
            downhillVelocity = 1f;
        } else {
            downhillVelocity = 2f;
        }

        if(app.getKeys().keyJustPressed(KeyEvent.VK_LEFT) && right && alive) {
            count = 0;
            left = true;
            right = false;
            animation = Assets.playerDown;
        }

        if(app.getKeys().keyJustPressed(KeyEvent.VK_RIGHT) && left && alive) {
            count = 0;
            right = true;
            left = false;
            animation = Assets.playerDown;
        }

        if(left) {
            count++;

            if(app.getKeys().keyPressed(KeyEvent.VK_SHIFT))
                velocityX = -1;
            else
                velocityX = -2;

            if(count == 8) {
                animation = Assets.playerLeft;
                count = 0;
            }
        }

        if(right) {
            count++;

            if(app.getKeys().keyPressed(KeyEvent.VK_SHIFT))
                velocityX = 1;
            else
                velocityX = 2;

            if(count == 8) {
                animation = Assets.playerRight;
                count = 0;
            }
        }

        moveX();
        moveY();

        if(alive)
            score++;

        app.getCam().centerOnEntity(this);
        app.getCam().checkBlankSpace();

        if(!alive && timesPlayed == 0) {
            crash.play();
            timesPlayed++;
        }

        if(!alive) {
            if(app.getKeys().keyJustPressed(KeyEvent.VK_BACK_SPACE)) {
                collect.play();
                gsm.setState(GameStateManager.MENU);
            }

            if(app.getKeys().keyJustPressed(KeyEvent.VK_ENTER)) {
                collect.play();
                gsm.setState(GameStateManager.PLAY);
            }
        }

        if(y >= 16090) {
            velocityX = 0;
            downhillVelocity = 0;
            alive = true;
            finished = true;
        }
    }

    public void moveX(){
        if(velocityX > 0){//Moving right
            int tx = (int) (x + velocityX + bounds.x + bounds.width) / Tile.WIDTH;

            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.HEIGHT) && alive){
                x += velocityX;
            }else{
                velocityX = 0;
                downhillVelocity = 0;
                alive = false;
            }
        }else if(velocityX < 0){//Moving left
            int tx = (int) (x + velocityX + bounds.x) / Tile.WIDTH;

            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.HEIGHT) && alive){
                x += velocityX;
            } else {
                velocityX = 0;
                downhillVelocity = 0;
                alive = false;
            }
        }
    }

    public void moveY(){
        if(downhillVelocity < 0){//Up
            int ty = (int) (y + downhillVelocity + bounds.y) / Tile.HEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.WIDTH, ty) && alive){
                y += downhillVelocity;
            } else {
                velocityX = 0;
                downhillVelocity = 0;
                alive = false;
            }

        }else if(downhillVelocity > 0){//Down
            int ty = (int) (y + downhillVelocity + bounds.y + bounds.height) / Tile.HEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.WIDTH, ty) && alive){
                y += downhillVelocity;
            }else{
                velocityX = 0;
                downhillVelocity = 0;
                alive = false;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y){
        return map.getTile(x, y).isSolid();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation, (int) (x - app.getCam().getX()), (int) (y - app.getCam().getY()), width, height, null);

        if(alive) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(16, 16, 440, 32);

            FontLoader.drawString(g, "Score: " + score, 25, 40, false, Color.WHITE, Assets.font);
        }

        if(!alive) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, app.getWidth(), app.getHeight());

            g.setColor(new Color(0, 0, 0, 0.8f));
            g.fillRect(118, 118, 286, 340);

            FontLoader.drawString(g, "You Crashed!", app.getWidth() / 2, 150, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "Your Score:", app.getWidth() / 2, 170, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "" + score, app.getWidth() / 2, 225, true, Color.LIGHT_GRAY, Assets.fontExtraLarge);

            FontLoader.drawString(g, "You Lasted:", app.getWidth() / 2, 280, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, (score / 60) + " seconds!", app.getWidth() / 2, 300, true, Color.WHITE, Assets.font);

            FontLoader.drawString(g, "Press enter to", app.getWidth() / 2, 340, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "play again", app.getWidth() / 2, 360, true, Color.WHITE, Assets.font);

            FontLoader.drawString(g, "Press backspc to", app.getWidth() / 2, 400, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "return to menu", app.getWidth() / 2, 420, true, Color.WHITE, Assets.font);
        }

        if(finished) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, app.getWidth(), app.getHeight());

            g.setColor(new Color(0, 0, 0, 0.8f));
            g.fillRect(118, 118, 286, 340);

            FontLoader.drawString(g, "You Finished!", app.getWidth() / 2, 150, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "Final Score:", app.getWidth() / 2, 170, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "" + score, app.getWidth() / 2, 225, true, new Color(255, 83, 83), Assets.fontExtraLarge);

            FontLoader.drawString(g, "You Lasted:", app.getWidth() / 2, 280, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, (score / 60) + " seconds!", app.getWidth() / 2, 300, true, Color.WHITE, Assets.font);

            FontLoader.drawString(g, "Press enter to", app.getWidth() / 2, 340, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "play again", app.getWidth() / 2, 360, true, Color.WHITE, Assets.font);

            FontLoader.drawString(g, "Press backspc to", app.getWidth() / 2, 400, true, Color.WHITE, Assets.font);
            FontLoader.drawString(g, "return to menu", app.getWidth() / 2, 420, true, Color.WHITE, Assets.font);
        }
    }

}
