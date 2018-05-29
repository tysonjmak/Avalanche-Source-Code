package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.audio.AudioPlayer;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.FontLoader;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TutorialState extends GameState {

    private final int fadeIn = 20, fadeOut = 20;
    private int count;
    private int alpha;

    private int currentOption;
    private String[] options;

    private AudioPlayer menuOption, collect;

    public TutorialState(Application app, GameStateManager gsm) {
        super(app, gsm);

        currentOption = 0;
        options = new String[] {
                "START GAME",
                "BACK TO MENU"
        };

        menuOption = new AudioPlayer("/sound/menuoption.wav");
        collect = new AudioPlayer("/sound/collect.wav");
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
                gsm.setState(GameStateManager.MENU);
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
        g.setColor(new Color(125, 112, 113));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());

        FontLoader.drawString(g, "Mission Overview", app.getWidth() / 2, 35, true, Color.BLACK, Assets.fontLarge);
        FontLoader.drawString(g, "Mission Overview", app.getWidth() / 2, 32, true, Color.WHITE, Assets.fontLarge);

        g.setColor(Color.BLACK);
        g.fillRect(32, 64, 448, 290);

//        g.drawImage(Assets.coins, 48, 84, 64, 64, null);
        FontLoader.drawString(g, "Explore and Loot", 120, 108, false, new Color(255, 198, 0), Assets.font);

        FontLoader.drawString(g, "Explore the vast land", 120, 138, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "and find the 10 hidden", 120, 158, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "treasure chests. Don't", 120, 178, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "forget the loot!", 120, 198, false, Color.WHITE, Assets.font);

//        g.drawImage(Assets.clock, 48, 204, 64, 64, null);
        FontLoader.drawString(g, "Don't Waste Time!", 120, 228, false, new Color(255, 198, 0), Assets.font);

        FontLoader.drawString(g, "Find all the chests in", 120, 258, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "the shortest amount of", 120, 278, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "time possible for a high", 120, 298, false, Color.WHITE, Assets.font);
        FontLoader.drawString(g, "rank!", 120, 318, false, Color.WHITE, Assets.font);

        g.setColor(Color.BLACK);
        g.fillRect(32, 384, 448, 160);

        FontLoader.drawString(g, "Begin Game", 100, 432, false, Color.GRAY, Assets.fontLarge);
        FontLoader.drawString(g, "Back to Menu", 100, 512, false, Color.GRAY, Assets.fontLarge);

        if(currentOption == 0) {
            FontLoader.drawString(g, ">", 60, 432, false, new Color(255, 198, 0), Assets.fontLarge);
            FontLoader.drawString(g, "Begin Game", 100, 432, false, new Color(255, 255, 255, alpha), Assets.fontLarge);
        } else if(currentOption == 1) {
            FontLoader.drawString(g, ">", 60, 512, false, new Color(255, 198, 0), Assets.fontLarge);
            FontLoader.drawString(g, "Back to Menu", 100, 512, false, new Color(255, 255, 255, alpha), Assets.fontLarge);
        }
    }
}
