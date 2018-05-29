package com.tysonmak.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    private static FontLoader loader;

    private static final int width = 16;
    private static final int height = 16;

    private static final int widthC = 16;
    private static final int heightC = 32;

    public static Font font, font2, fontLarge, fontExtraLarge;

    public static BufferedImage snow1, snow2, snow3, snow4, trunk1, trunk2, leaves1, leaves2, ice, finish;
    public static BufferedImage playerDown, playerLeft, playerRight, avalanche;

    public static void create() {
        loader = new FontLoader();

        SpriteSheet tileSet_old = new SpriteSheet(ImageLoader.load("/tileset_old.png"));
        SpriteSheet tileSet = new SpriteSheet(ImageLoader.load("/tileset.png"));
        SpriteSheet character = new SpriteSheet(ImageLoader.load("/character.png"));

        snow1 = tileSet.crop(0, 0, width, height);
        snow2 = tileSet.crop(width, 0, width, height);
        snow3 = tileSet.crop(0, height, width, height);
        snow4 = tileSet.crop(width, height, width, height);

        trunk1 = tileSet.crop(width * 2, height, width, height);
        trunk2 = tileSet.crop(width * 3, height, width, height);

        leaves1 = tileSet.crop(width * 2, 0, width, height);
        leaves2 = tileSet.crop(width * 3, 0, width, height);

        playerDown = character.crop(0, 0, widthC, heightC);
        playerRight = character.crop(widthC, 0, widthC, heightC);
        playerLeft = character.crop(widthC * 2, 0, widthC, heightC);

        avalanche = tileSet.crop(0, 32, 64, 32);
        ice = tileSet.crop(0, height * 4, width, height);

        finish = tileSet.crop(width, height * 4, width, height);

        font = loader.load("font.ttf", 24);
        font2 = loader.load("font.ttf", 30);
        fontLarge = loader.load("font.ttf", 36);
        fontExtraLarge = loader.load("font.ttf", 70);
    }

}
