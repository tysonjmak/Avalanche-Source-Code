package com.tysonmak.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update() {
        for(int i = 0; i < keys.length; i++) {
            if(cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if(justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }

            if(!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }
    }

    public boolean keyPressed(int key) {
        return keys[key];
    }

    public boolean keyReleased(int key) {
        return !keys[key];
    }

    public boolean keyJustPressed(int key) {
        return justPressed[key];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
