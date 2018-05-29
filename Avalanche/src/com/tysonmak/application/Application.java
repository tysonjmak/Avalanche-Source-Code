package com.tysonmak.application;

import com.tysonmak.display.Display;
import com.tysonmak.graphics.Assets;
import com.tysonmak.graphics.Camera;
import com.tysonmak.input.KeyManager;
import com.tysonmak.states.manager.GameStateManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Application implements Runnable {

    private Display display;
    private String title;
    private int width;
    private int height;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics2D g;

    private KeyManager keys;
    private GameStateManager gsm;
    private Camera cam;

    private int timeMinutes;
    private int timeSeconds;

    public Application(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        keys = new KeyManager();
        cam = new Camera(this, 0, 0);
    }

    private void create() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.d3d", "True");

        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keys);

        Assets.create();

        gsm = new GameStateManager(this);

        timeMinutes = 0;
        timeSeconds = 0;
    }

    private void update() {
        keys.update();
        gsm.update();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        gsm.render(g);

        bs.show();
        g.dispose();
    }

    public void run() {
        create();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if(delta >= 1) {
                update();
                render();
                delta--;
            }
        }

        stop();
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    public Graphics2D getG() {
        return g;
    }

    public void setG(Graphics2D g) {
        this.g = g;
    }

    public KeyManager getKeys() {
        return keys;
    }

    public void setKeys(KeyManager keys) {
        this.keys = keys;
    }

    public GameStateManager getGsm() {
         return gsm;
    }

    public void setGsm(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }
}
