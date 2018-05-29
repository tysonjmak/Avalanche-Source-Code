package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.FontLoader;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SplashState extends GameState {

    private final int fadeIn = 120, length = 180, fadeOut = 120;

    private int count;
    private int alpha;

    public SplashState(Application app, GameStateManager gsm) {
        super(app, gsm);

        count = 0;
    }

    @Override
    public void update() {
        count++;

        if(app.getKeys().keyJustPressed(KeyEvent.VK_ENTER))
            gsm.setState(GameStateManager.MENU);

        if(count < fadeIn) {
            alpha = (int) (255 - 255 * (1.0f * count / fadeIn));
            if(alpha < 0)
                alpha = 0;
        }

        if(count > fadeIn + length) {
            alpha = (int) (255 * (1.0f * count - fadeIn - length) / fadeOut);
            if(alpha > 255)
                alpha = 255;
        }

        if(count > fadeIn + length + fadeOut)
            gsm.setState(GameStateManager.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, app.getWidth(), app.getHeight());

        FontLoader.drawString(g, "This game was programmed", app.getWidth() / 2, app.getHeight() / 2 - 15, true, Color.WHITE, Assets.font2);
        FontLoader.drawString(g, "in java by Tyson Mak", app.getWidth() / 2, app.getHeight() / 2 + 15, true, Color.WHITE, Assets.font2);

        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());
    }

}
