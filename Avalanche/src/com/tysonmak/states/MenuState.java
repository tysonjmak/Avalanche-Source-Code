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

public class MenuState extends GameState {

    private final int fadeIn = 20, fadeOut = 20;
    private int count;
    private int alpha;

    private int currentOption;
    private String[] options;

    private AudioPlayer menuOption, collect;

    private TileMap map;

    public MenuState(Application app, GameStateManager gsm) {
        super(app, gsm);

        app.getCam().setX(0);
        app.getCam().setY(0);

        currentOption = 0;
        options = new String[] {
            "START",
            "HOW TO PLAY",
            "QUIT"
        };

        menuOption = new AudioPlayer("/sound/menuoption.wav");
        collect = new AudioPlayer("/sound/collect.wav");

        map = new TileMap(app, "/menu_map_final.txt");
    }

    @Override
    public void update() {
        count++;

        if(app.getKeys().keyJustPressed(KeyEvent.VK_DOWN) && currentOption < options.length - 1) {
            menuOption.play();
            currentOption++;
            count = 0;
        }

        if(app.getKeys().keyJustPressed(KeyEvent.VK_UP) && currentOption > 0) {
            menuOption.play();
            currentOption--;
            count = 0;
        }

        if(app.getKeys().keyJustPressed(KeyEvent.VK_ENTER)) {
            collect.play();
            if(currentOption == 0)
                gsm.setState(GameStateManager.PLAY);
            if(currentOption == 1)
                gsm.setState(GameStateManager.HELP);
            if(currentOption == 2)
                System.exit(0);
        }

        if(count < fadeIn) {
            alpha = (int) (255 - 255 * (1.0f * count / fadeIn));
            if(alpha < 0)
                alpha = 0;
        }

        if(count > fadeIn) {
            alpha = (int) (255 * (1.0f * count - fadeIn) / fadeOut);
            if(alpha > 255)
                alpha = 255;
        }

        if(count == fadeIn + fadeOut)
            count = 0;
    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);

        FontLoader.drawString(g, "Avalanche!", app.getWidth() / 2, 105, true, Color.BLACK, Assets.fontExtraLarge);
        FontLoader.drawString(g, "Avalanche!", app.getWidth() / 2, 100, true, new Color(255, 83, 83), Assets.fontExtraLarge);
        FontLoader.drawString(g, "Escape the avalanche through", app.getWidth() / 2, 150, true, Color.BLACK, Assets.font);
        FontLoader.drawString(g, "an epic tree-skiing adventure.", app.getWidth() / 2, 170, true, Color.BLACK, Assets.font);

        g.setColor(new Color(0, 0, 0, 0.8f));
        g.fillRect(32, 250, 448, 240);

        FontLoader.drawString(g, "Start Game", 100, 298, false, Color.GRAY, Assets.fontLarge);
        FontLoader.drawString(g, "How To Play", 100, 378, false, Color.GRAY, Assets.fontLarge);
        FontLoader.drawString(g, "Quit To Desktop", 100, 458, false, Color.GRAY, Assets.fontLarge);

        if (currentOption == 0) {
            FontLoader.drawString(g, ">", 60, 298, false, new Color(255, 83, 83), Assets.fontLarge);
            FontLoader.drawString(g, "Start Game", 100, 298, false, new Color(255, 255, 255, alpha), Assets.fontLarge);
        } else if (currentOption == 1) {
            FontLoader.drawString(g, ">", 60, 378, false, new Color(255, 83, 83), Assets.fontLarge);
            FontLoader.drawString(g, "How to Play", 100, 378, false, new Color(255, 255, 255, alpha), Assets.fontLarge);
        } else if(currentOption == 2) {
            FontLoader.drawString(g, ">", 60, 458, false, new Color(255, 83, 83), Assets.fontLarge);
            FontLoader.drawString(g, "Quit to Desktop", 100, 458, false, new Color(255, 255, 255, alpha), Assets.fontLarge);
        }
    }
}
