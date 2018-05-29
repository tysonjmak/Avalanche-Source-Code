package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.audio.AudioPlayer;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.FontLoader;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState {

    private String rank;
    private AudioPlayer collect;

    public GameOverState(Application app, GameStateManager gsm) {
        super(app, gsm);
        rank = "";

        collect = new AudioPlayer("/sound/collect.wav");
    }

    @Override
    public void update() {
        if(app.getKeys().keyPressed(KeyEvent.VK_ENTER)) {
            collect.play();
            gsm.setState(GameStateManager.MENU);
        }

        if(app.getTimeMinutes() <= 2)
            rank = "Speed Demon";
        if(app.getTimeMinutes() == 3)
            rank = "Proficient";
        if(app.getTimeMinutes() == 4)
            rank = "Beginner";
        if(app.getTimeMinutes() >= 5)
            rank = "Bumbling Idiot";
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(125, 112, 113));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());

        g.setColor(Color.BLACK);
        g.fillRect(32, 64, 448, 150);

        g.setColor(new Color(255, 198, 0));
        g.fillRect(48, 80, 416, 118);

        FontLoader.drawString(g, "You Win!", app.getWidth() / 2, 145, true, Color.BLACK, Assets.fontExtraLarge);
        FontLoader.drawString(g, "You Win!", app.getWidth() / 2, 140, true, Color.WHITE, Assets.fontExtraLarge);

        g.setColor(Color.BLACK);
        g.fillRect(32, 230, 448, 180);

//        g.drawImage(Assets.clock, 48, 256, 64, 64, null);
//        g.drawImage(Assets.rank, 48, 330, 64, 64, null);

        FontLoader.drawString(g, "Time:", 128, 268, false, new Color(255, 198, 0), Assets.font);

        if( app.getTimeSeconds() >= 10)
            FontLoader.drawString(g,  app.getTimeMinutes() + ":" +  app.getTimeSeconds(), 126, 298, false, Color.WHITE, Assets.fontLarge);
        else
            FontLoader.drawString(g,  app.getTimeMinutes() + ":0" +  app.getTimeSeconds(), 126, 298, false, Color.WHITE, Assets.fontLarge);

        FontLoader.drawString(g, "Rank:", 128, 348, false, new Color(255, 198, 0), Assets.font);
        FontLoader.drawString(g, rank, 126, 378, false, Color.WHITE, Assets.fontLarge);

        FontLoader.drawString(g, "Press ENTER to return to menu", app.getWidth() / 2, 512, true, Color.BLACK, Assets.font);
    }
}
