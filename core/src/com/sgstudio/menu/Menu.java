package com.sgstudio.menu;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.sgstudio.game.music.MusicGame;
import com.sgstudio.main.Main;
import com.sgstudio.utils.Tiles;

public class Menu implements Screen {
	private Map<String, TextureRegion> atlasMenu;
	private static Map<String, TextureRegion> atlasSound;
	private boolean Moved[] = {false, false, false, false, false};
	private boolean Pressed[] = {false, false, false, false, false};
	private boolean Play = true;
	
	private final Main main;
	private Tiles tiles;
	
	private SpriteBatch batch;
	
	BitmapFont miniFont;
	
	private static Music sound;
	private static MusicGame music;
	
	private OrthographicCamera camera;

	public Menu(final Main main) {
		this.main = main;
	}
	
	private float width, height;
	
	@Override
	public void show() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		tiles = new Tiles();
		music = new MusicGame();
		music.stop();
		music.setMuted(true);
		tiles.createAtlas("atlas/menu.png", 3, 4);
		atlasMenu = tiles.getTextureRegion();
		
		tiles.createAtlas("atlas/sound.png", 2, 1);
		atlasSound = tiles.getTextureRegion();
		
		batch = main.getBatch();
		width = atlasMenu.get("tiles0_0").getRegionWidth();
		height = atlasMenu.get("tiles0_0").getRegionHeight();
		
		sound = Gdx.audio.newMusic(Gdx.files.internal("audio/music/MainTheme.ogg"));
		sound.setLooping(true);
		sound.setVolume(0.5f);
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/pixel.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.borderColor = Color.BLACK;
		param.borderWidth = 1;
		param.size = 15;
		miniFont = gen.generateFont(param);
		
		Gdx.input.setInputProcessor(new InputProcessor(){
			@Override
			public boolean keyDown(int keycode) { return false; }

			@Override
			public boolean keyUp(int keycode) { return false; }

			@Override
			public boolean keyTyped(char character) { return false; }

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(screenX>=Gdx.graphics.getWidth()/2-width/2 && screenX<=Gdx.graphics.getWidth()/2+width/2){
					
					//Button - Play
					if(screenY<=Gdx.graphics.getHeight()/2-height-height/2 && screenY>=Gdx.graphics.getHeight()/2-height*2-height/2)
						Pressed[0] = true;
					else Pressed[0] = false;
					//Button - About SumGamStudio
					if(screenY<=Gdx.graphics.getHeight()/2-height/2 && screenY>=Gdx.graphics.getHeight()/2-height/2-height)
						Pressed[2] = true;
					else Pressed[2] = false;
					//Button - Exit
					if(screenY<=Gdx.graphics.getHeight()/2+height/2 && screenY>=Gdx.graphics.getHeight()/2+height/2-height)
						Pressed[3] = true;
					else Pressed[3] = false;
				} else {
					for(int i=0;i<3;i++) Pressed[i]=false;
				}
				//Button - Sound
				if(screenX>=15 && screenX<=15+atlasSound.get("tiles0_0").getRegionWidth()){
					if(screenY<=Gdx.graphics.getHeight()-15 && screenY>=Gdx.graphics.getHeight()-15-atlasSound.get("tiles0_0").getRegionHeight())
						Pressed[4]=true;
					else Pressed[4]=false;
				} else Pressed[4]=false;
				
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if(Moved[0] && Pressed[0]) main.setScreen(main.tutorial);
				if(Moved[2] && Pressed[2]) main.setScreen(main.aboutsgstudio);
				if(Moved[3] && Pressed[3]) Gdx.app.exit();
				if(Moved[4] && Pressed[4]){
					if(Play) {
						sound.pause();
						music.musicStade();
					}
					else sound.play();
					Play=!Play;
					
				}
				for(int i=0;i<3;i++) Pressed[i]=false;
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				if(screenX>=Gdx.graphics.getWidth()/2-width/2 && screenX<=Gdx.graphics.getWidth()/2+width/2){
					//Button - Play
					if(screenY<=Gdx.graphics.getHeight()/2-height-height/2 && screenY>=Gdx.graphics.getHeight()/2-height*2-height/2)
						Moved[0] = true;
					else Moved[0] = false;
					//Button - About SumGamStudio
					if(screenY<=Gdx.graphics.getHeight()/2-height/2 && screenY>=Gdx.graphics.getHeight()/2-height/2-height)
						Moved[2] = true;
					else Moved[2] = false;
					//Button - Exit
					if(screenY<=Gdx.graphics.getHeight()/2+height/2 && screenY>=Gdx.graphics.getHeight()/2+height/2-height)
						Moved[3] = true;
					else Moved[3] = false;
				} else {
					for(int i=0;i<3;i++) Moved[i]=false;
				}
				//Button - Sound
				if(screenX>=15 && screenX<=15+atlasSound.get("tiles0_0").getRegionWidth()){
					if(screenY<=Gdx.graphics.getHeight()-15 && screenY>=Gdx.graphics.getHeight()-15-atlasSound.get("tiles0_0").getRegionHeight())
						Moved[4]=true;
					else Moved[4]=false;
				} else Moved[4]=false;
				
				return false;
			}

			@Override
			public boolean scrolled(int amount) { return false; }
		});
		
		if((Play) && (!music.isMuted())) sound.play();
	}
	
	public static Map<String, TextureRegion> getAtlasSound(){ return atlasSound; }
	
	
	private void renderMenu(){
		
		if(!Moved[0]) batch.draw(atlasMenu.get("tiles0_0"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height+height/2);
		else if(Pressed[0] && Moved[0]) batch.draw(atlasMenu.get("tiles0_2"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height+height/2);
		else batch.draw(atlasMenu.get("tiles0_1"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height+height/2);
		
		if(!Moved[2]) batch.draw(atlasMenu.get("tiles2_0"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height/2);
		else if(Pressed[2] && Moved[2]) batch.draw(atlasMenu.get("tiles2_2"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height/2);
		else batch.draw(atlasMenu.get("tiles2_1"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2+height/2);
		
		if(!Moved[3]) batch.draw(atlasMenu.get("tiles3_0"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2-height/2);
		else if(Pressed[3] && Moved[3]) batch.draw(atlasMenu.get("tiles3_2"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2-height/2);
		else batch.draw(atlasMenu.get("tiles3_1"),Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()/2-height/2);
			
		if(Play) batch.draw(atlasSound.get("tiles0_0"), 15, 15);
		else batch.draw(atlasSound.get("tiles0_1"), 15, 15);
	}
	
	static float r=0.57f;
	static float g=0.26f;
	static float b=0.41f;
	static boolean upR=true;
	static boolean upG=true;
	static boolean upB=true;
	
	public static float getR() {return r;}
	public static float getG() {return g;}
	public static float getB() {return b;}
	public static boolean getupR() {return upR;}
	public static boolean getupG() {return upG;}
	public static boolean getupB() {return upB;}
	
	public void setR(float r) {Menu.r = r;}
	public void setG(float g) {Menu.g = g;}
	public void setB(float b) {Menu.b = b;}
	public void setupR(boolean upR) {Menu.upR = upR;}
	public void setupG(boolean upG) {Menu.upG = upG;}
	public void setupB(boolean upB) {Menu.upB = upB;}
	@Override
	public void render(float delta) {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		renderMenu();
		
		miniFont.draw(batch, "The game for LudumDare39. Theme: Running out of Power.", 1, 12);
		
		batch.end();
		switchColor();
	}
	private void switchColor(){
		if(upR){
			r+=0.0025;
			if(r>=1) upR=!upR;
		}else{
			r-=0.0005;
			if(r<=0) upR=!upR;
		}
		
		if(upG){
			g+=0.0015;
			if(g>=1) upG=!upG;
		}else{
			g-=0.0015;
			if(g<=0) upG=!upG;
		}
		
		if(upB){
			b+=0.0005;
			if(b>=1) upB=!upB;
		}else{
			b-=0.0025;
			if(b<=0) upB=!upB;
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
		this.dispose();
	}

	@Override
	public void dispose() {
		sound.dispose();
	}
	
}
