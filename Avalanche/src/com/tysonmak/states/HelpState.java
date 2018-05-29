package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.audio.AudioPlayer;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.FontLoader;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;
import com.tysonmak.tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {

    private AudioPlayer collect;
    private TileMap map;

    public HelpState(Application app, GameStateManager gsm) {
        super(app, gsm);

        collect = new AudioPlayer("/sound/collect.wav");
        map = new TileMap(app, "/menu_map_final.txt");
    }

    @Override
    public void update() {
        if(app.getKeys().keyJustPressed(KeyEvent.VK_ENTER)) {
            gsm.setState(GameStateManager.MENU);
            collect.play();
        }
    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);

        FontLoader.drawString(g, "How to Play", app.getWidth() / 2, 35, true, Color.BLACK, Assets.fontLarge);
        FontLoader.drawString(g, "How to Play", app.getWidth() / 2, 32, true, new Color(255, 83, 83), Assets.fontLarge);

        g.setColor(new Color(0, 0, 0, 0.8f));
        g.fillRect(32, 64, 448, 400);

        FontLoader.drawString(g, "Menu Controls", app.getWidth() / 2, 108, true, new Color(255, 83, 83), Assets.font);
        FontLoader.drawString(g, "UP = Menu option up", 60, 148, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "DOWN = Menu option down", 60, 168, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "ENTER = Select option", 60, 188, false, Color.WHITE, Assets.font);

        FontLoader.drawString(g, "Game Controls", app.getWidth() / 2, 228, true, new Color(255, 83, 83), Assets.font);

        FontLoader.drawString(g, "LEFT = (TAP) Turn left", 60, 268, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "RIGHT = (TAP) Turn right", 60, 288, false, Color.WHITE, Assets.font);

        FontLoader.drawString(g, "SPACE = (HOLD) Wide Turns", 60, 308, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "SHIFT = (HOLD) Small Turns", 60, 328, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "Have fun!", 60, 420, false, new Color(255, 83, 83), Assets.font);

        FontLoader.drawString(g, "Press ENTER to return to menu", app.getWidth() / 2, 512, true, Color.BLACK, Assets.font);
    }
}
