package com.tysonmak.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage texture;

    public SpriteSheet(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return texture.getSubimage(x, y, width, height);
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
