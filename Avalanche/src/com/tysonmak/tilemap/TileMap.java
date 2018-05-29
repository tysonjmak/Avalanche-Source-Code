package com.tysonmak.tilemap;

import com.tysonmak.application.Application;
import com.tysonmak.entity.Player;
import jdk.internal.util.xml.impl.Input;

import java.awt.*;
import java.io.*;

public class TileMap {

    private Application app;

    private int width;
    private int height;
    private int[][] tiles;

    public TileMap(Application app, String path) {
        this.app = app;
        loadWorld(path);
    }

    public void update() {

    }

    public void render(Graphics2D g) {
        int xStart = (int) Math.max(0, app.getCam().getX() / Tile.WIDTH);
        int xEnd = (int) Math.min(width, (app.getCam().getX() + app.getWidth()) / Tile.WIDTH + 1);
        int yStart = (int) Math.max(0, app.getCam().getY() / Tile.HEIGHT);
        int yEnd = (int) Math.min(height, (app.getCam().getY() + app.getHeight()) / Tile.HEIGHT + 1);

        for(int y = yStart;y < yEnd;y++) {
            for(int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (int) (x * Tile.WIDTH - app.getCam().getX()),
                        (int) (y * Tile.HEIGHT - app.getCam().getY()));
            }
        }
    }

    public void setTile(int x, int y, int id) {
        tiles[x][y] = id;
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height)
            return Tile.snow1;

        Tile tile = Tile.tiles[tiles[x][y]];
        if(tile == null)
            return Tile.snow1;
        return tile;
    }

    public void loadWorld(String path) {
        String file = loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = parseInt(tokens[0]);
        height = parseInt(tokens[1]);

        tiles = new int[width][height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                tiles[x][y] = parseInt(tokens[(x + y * width) + 2]);
            }
        }
    }

    public String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        InputStream file = this.getClass().getResourceAsStream(path);
        InputStreamReader reader = new InputStreamReader(file);

        try {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine()) != null)
                builder.append(line + "\n");

            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
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

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }
}
