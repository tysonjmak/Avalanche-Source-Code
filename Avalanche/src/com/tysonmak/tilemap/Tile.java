package com.tysonmak.tilemap;

import com.tysonmak.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static Tile[] tiles = new Tile[256];

    public static Tile snow1 = new Tile(Assets.snow1, 0, false);
    public static Tile snow2 = new Tile(Assets.snow2, 1, false);
    public static Tile snow3 = new Tile(Assets.snow3, 4, false);
    public static Tile snow4 = new Tile(Assets.snow4, 5, false);

    public static Tile trunk1 = new Tile(Assets.trunk1, 6, true);
    public static Tile trunk2 = new Tile(Assets.trunk2, 7, true);

    public static Tile leaves1 = new Tile(Assets.leaves1, 2, false);
    public static Tile leaves2 = new Tile(Assets.leaves2, 3, false);
    public static Tile finish = new Tile(Assets.finish, 17, true);

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private BufferedImage texture;
    private final int id;
    private boolean solid;

    public Tile(BufferedImage texture, int id, boolean solid) {
        this.texture = texture;
        this.id = id;
        this.solid = solid;

        tiles[id] = this;
    }

    public void render(Graphics2D g, int x, int y) {
        g.drawImage(texture, x, y, WIDTH, HEIGHT, null);
    }

    public boolean isSolid() {
        return solid;
    }

    public int getId() {
        return id;
    }
}
