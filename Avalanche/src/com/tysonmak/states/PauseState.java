package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;

import java.awt.*;

public class PauseState extends GameState {

    public PauseState(Application app, GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {

    }
}
