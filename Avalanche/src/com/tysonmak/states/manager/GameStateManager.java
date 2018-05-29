package com.tysonmak.states.manager;

import com.tysonmak.application.Application;
import com.tysonmak.states.*;

import java.awt.*;

public class GameStateManager {

    private Application app;

    private boolean paused;
    private PauseState pauseState;

    private GameState[] gameStates;
    private int currentState;
    private int previousState;

    public static final int NUMSTATES = 6;
    public static final int INTRO = 0;
    public static final int MENU = 1;
    public static final int PLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int HELP = 4;
    public static final int SPLASH = 5;

    public GameStateManager(Application app) {
        this.app = app;

        paused = false;
        pauseState = new PauseState(app, this);

        gameStates = new GameState[NUMSTATES];
        setState(SPLASH);
    }

    public void setState(int state) {
        previousState = currentState;
        unloadState(previousState);
        currentState = state;

        if(state == INTRO)
            gameStates[state] = new TutorialState(app, this);
        else if(state == MENU)
            gameStates[state] = new MenuState(app, this);
        else if(state == PLAY)
            gameStates[state] = new PlayState(app, this);
        else if(state == GAMEOVER)
            gameStates[state] = new GameOverState(app, this);
        else if(state == HELP)
            gameStates[state] = new HelpState(app, this);
        else if(state == SPLASH)
            gameStates[state] = new SplashState(app, this);
    }

    public void unloadState(int state) {
        gameStates[state] = null;
    }

    public void update() {
        if(paused)
            pauseState.update();
        else if(gameStates[currentState] != null)
            gameStates[currentState].update();
    }

    public void render(Graphics2D g) {
        if(paused)
            pauseState.render(g);
        else if(gameStates[currentState] != null)
            gameStates[currentState].render(g);
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public PauseState getPauseState() {
        return pauseState;
    }

    public void setPauseState(PauseState pauseState) {
        this.pauseState = pauseState;
    }

    public GameState[] getGameStates() {
        return gameStates;
    }

    public void setGameStates(GameState[] gameStates) {
        this.gameStates = gameStates;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public int getPreviousState() {
        return previousState;
    }

    public void setPreviousState(int previousState) {
        this.previousState = previousState;
    }
}
