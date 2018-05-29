package com.tysonmak.states.manager;

import com.tysonmak.application.Application;

import java.awt.*;

public abstract class GameState {

    protected Application app;
    protected GameStateManager gsm;

    public GameState(Application app, GameStateManager gsm) {
        this.app = app;
        this.gsm = gsm;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);
}
