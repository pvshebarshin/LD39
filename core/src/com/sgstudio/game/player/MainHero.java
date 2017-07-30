package com.sgstudio.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sgstudio.game.KeyManager;
import com.sgstudio.game.MyGame;
import com.sgstudio.game.train.Train;
import com.sgstudio.utils.Box2DHelper;

public class MainHero {
	private Texture img;
	private SpriteBatch batch;
	private Sprite sprite;

	private World world;
	private Body body;

	private int wood;
	private static int maxWood;
	private KeyManager keys;
	private Train train;

	public MainHero(SpriteBatch batch, World world, Train train) {
		this.train = train;
		this.batch = MyGame.getBatch();
		this.world = world;

		img = new Texture("atlas/test.png");
		sprite = new Sprite(img);

		maxWood = 10;
		wood = 10;

		createPhysics();

		keys = new KeyManager();
	}

	private void createPhysics() {
		body = Box2DHelper.makeBoxAroundSprite(world, sprite);
		Box2DHelper.setTransform(body, 590, 255, 0);
		body.setFixedRotation(true);
	}

	public enum State {
		NONE, WALKING, DEAD
	}

	public void render() {
		update();

		Vector2 pos = Box2DHelper.getPosition(body);
		batch.draw(sprite, pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
	}

	public void dispose() {
		img.dispose();
	}

	public void update() {
		if (keys.getPressedLeft()) {
			body.applyForceToCenter(-1.0f, 0, true);
		}
		if (keys.getPressedRight()) {
			body.applyForceToCenter(1.0f, 0, true);
		}
		if (keys.getPressedUp()) {
			body.applyForceToCenter(0, 2.0f, true);
		}
		if (keys.getPressedE()) {
			putWood();
		}
	}

	public void putWood() {
		train.updOvenWood(getWood());
		setWood(0);
	}

	public int getWood() {
		return wood;
	}

	public int getMaxWood() {
		return maxWood;
	}

	// Update Stats Methods
	public void updWood(int i) {
		if (wood + i <= 0) {
			wood = 0;
		} else if (wood + i > 0) {
			wood += i;
		} else if (wood + i > maxWood) {
			wood = maxWood;
		} else {
			wood += i;
		}
	}

	// Set methods
	public void setWood(int i) {
		if (i >= maxWood) {
			wood = maxWood;
		} else if (i <= 0) {
			wood = 0;
		} else
			wood += i;
	}

	public void setMaxWood(int i) {
		maxWood = i;
	}

	public Vector2 getPosition() {
		return Box2DHelper.getPosition(body);
	}
}