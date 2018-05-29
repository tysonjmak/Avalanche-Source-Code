package com.tysonmak.states;

import com.tysonmak.application.Application;
import com.tysonmak.entity.Player;
import com.tysonmak.graphics.Assets;
import com.tysonmak.states.manager.GameState;
import com.tysonmak.states.manager.GameStateManager;
import com.tysonmak.tilemap.TileMap;

import java.awt.*;

public class PlayState extends GameState {

    private TileMap map;
    private Player player;

    public PlayState(Application app, GameStateManager gsm) {
        super(app, gsm);

        map = new TileMap(app, "/map_final.txt");
        player = new Player(app, gsm, map);
    }

    @Override
    public void update() {
        app.getCam().checkBlankSpace();
        map.update();
        player.update();
    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);
        g.drawImage(Assets.ice, 0, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64, 0, 64, 64, null);
        g.drawImage(Assets.ice, 128, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64 * 3, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64 * 4, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64 * 5, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64 * 6, 0, 64, 64, null);
        g.drawImage(Assets.ice, 64 * 7, 0, 64, 64, null);

        g.drawImage(Assets.avalanche, 0, 64, 256, 128, null);
        g.drawImage(Assets.avalanche, 256, 64, 256, 128, null);
        player.render(g);
    }
}
