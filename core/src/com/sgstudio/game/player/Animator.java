package com.sgstudio.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sgstudio.game.MyGame;
import com.sgstudio.game.Tutorial;
import com.sgstudio.game.controller.KeyManager;
import com.sgstudio.main.*;

public class Animator {
	final int FRAME_COLS = 8; 
	final int FRAME_ROWS = 1;
	
    private Animation walkAnimation; 
    private Texture walkSheet; 
    private Texture stop;
    private TextureRegion[] walkFrames; 
    private SpriteBatch spriteBatch; 
    private TextureRegion currentFrame;
	 
	float stateTime;
	private MainHero mainHero;
	private boolean isLastLeft = false;
	
    public Animator(MainHero mainHero) {
		this.mainHero = mainHero;
		walkSheet = new Texture(Gdx.files.internal("Char_move.png"));
	        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, 
	        		walkSheet.getHeight()/FRAME_ROWS);
	        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames[index++] = tmp[i][j];
	            }
	        }
	        walkAnimation = new Animation(0.075f, walkFrames);
	        spriteBatch = MyGame.getBatch();
	        stateTime = 0f;
	        stop = new Texture("charstat.png");
	}
	
    public void render() {

    	float heroDX = mainHero.getHeroDX();
    	
		if (heroDX > 0.4f){
	        stateTime += Gdx.graphics.getDeltaTime();	        
	        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);	        
	        spriteBatch.draw(currentFrame, mainHero.getHeroX() - currentFrame.getRegionWidth()/2, mainHero.getHeroY() - currentFrame.getRegionHeight()/2,
	        		currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
	        isLastLeft = true;
		} else if( heroDX < -0.4f) {
		    stateTime += Gdx.graphics.getDeltaTime(); 
	        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
	        spriteBatch.draw(currentFrame, mainHero.getHeroX() + currentFrame.getRegionWidth()/2, mainHero.getHeroY() - currentFrame.getRegionHeight()/2,
	        		-currentFrame.getRegionWidth(), currentFrame.getRegionHeight()); 
	        isLastLeft = false;
		} else {
			if (isLastLeft ) {
				spriteBatch.draw(stop, mainHero.getHeroX() - stop.getWidth()/2, mainHero.getHeroY() - stop.getHeight()/2,
		        		stop.getWidth(), stop.getHeight());
	    	} else {
				spriteBatch.draw(stop, mainHero.getHeroX() + stop.getWidth()/2, mainHero.getHeroY() - stop.getHeight()/2,
					-stop.getWidth(), stop.getHeight());				
			}
		}
    }
}