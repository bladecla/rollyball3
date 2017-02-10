package com.rolly.ball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.rolly.ball.RollyBall;

/**
 * Created by Bladecla on 2/10/2017.
 */
public class SplashScreen implements Screen {

    private RollyBall game;
    private Sound jingle;
    private Texture cheezyLogo;
    private SpriteBatch batch;
    private float timer;
    private boolean play = true;

    public SplashScreen(RollyBall game){
        this.game = game;
    }

    @Override
    public void show() {
    jingle = Gdx.audio.newSound(Gdx.files.internal("cheezy.wav"));
        cheezyLogo = new Texture(Gdx.files.internal("cheezylogo.png"));
    batch = new SpriteBatch();
        timer = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;
        if (timer > .5)
        {
            batch.begin();
            batch.draw(cheezyLogo, Gdx.graphics.getWidth()/2-(cheezyLogo.getWidth()/2), Gdx.graphics.getHeight()/2-(cheezyLogo.getHeight()/2));

            batch.end();
            if(play)
                jingle.play(.5f);
            play = false;

        }

        if (timer > 2)
        {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
batch.dispose();
        jingle.dispose();
        cheezyLogo.dispose();
    }
}
