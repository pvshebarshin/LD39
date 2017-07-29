package com.sgstudio.game.village;import com.badlogic.gdx.graphics.Texture;import com.badlogic.gdx.graphics.g2d.SpriteBatch;public class Village {	private static long startTime;	private static float time = 0;	private Texture good;	private Texture bad;	private SpriteBatch batch;	public Village(SpriteBatch batch) {		this.batch = batch;		good = new Texture("pashasimages/good.gif");		bad = new Texture("pashasimages/bad.png");				Village.startTime = System.currentTimeMillis();	}	public void addHunger(int hunger) {		Village.hunger += hunger;	}	private float maxHP = 100000f;	private static float maxThirst = 100f;	private static float maxHunger = 100f;		private static float thirst = maxThirst;	private static float hunger = maxHunger;	public float HP = maxHP;	private final int DEATH = 0;	public void update() {		if (time != (System.currentTimeMillis() - startTime) / 1000) {			time++;						if (HP > maxHP) {HP = maxHP;}			if (thirst > maxThirst) {thirst = maxThirst;}			if (hunger > maxHunger) {hunger = maxHunger;}			hunger -= 0.333333333333333333333333333333f;			thirst -= 0.416666666666666666666666666666f;			if (hunger < 0) {hunger = 0;}			if (hunger == 0) {				HP -= 300;			}			if (thirst < 0) {thirst = 0;}			if (thirst == 0) {				HP -= 400;			}			if (HP < DEATH) {HP = DEATH;}			/*			 * if (thirst > 0){			 * 			 * System.out.println(hunger);			 * 			 * System.out.println(thirst);			 * 			 * } else {			 * 			 * System.out.println(hunger);			 * 			 * System.out.println(HP);			 * 			 * }			 */		}	}	public float getHealth() {return HP;}	public float getWater() {return thirst;}	public float getHunger() {return hunger;}	public void updHunger(float i) {		if (hunger + i <= 0) {			hunger = 0;		} else if (hunger + i > 0) {			hunger += i;		} else if (hunger + i > maxHunger) {			hunger = maxHunger;		} else {			hunger += i;		}	}	public void updWater(float i) {		if (thirst + i <= 0) {			thirst = 0;		} else if (thirst + i > 0) {			thirst += i;		} else if (thirst + i > maxThirst) {			thirst = maxThirst;		} else {			thirst += i;		}	}	public void updHealth(float i) {		if (HP + i <= 0) {			HP = 0;		} else if (HP + i > 0) {			HP += i;		} else if (HP + i > maxHP) {			HP = maxHP;		} else {			HP += i;		}	}	public void setHealth(float i) {		if (i < maxHP) {			HP = maxHP;		}		else if (i < 0) {			HP = 0;		}	}	public void setWater(float i) {		if (i < maxThirst) {			thirst = maxThirst;		} else if (i < 0) {			thirst = 0;		}	}	public void setHunger(float i) {		if (i < maxHunger) {			hunger = maxHunger;		}		else if (i < 0) {			hunger = 0;		}	}	public void render() {		if (HP > 50000) {			batch.draw(good, 50, 100, 50, 50);		} else {			batch.draw(bad, 50, 100, 64, 64);		}	}	public void setMaxHealth(float i) {maxHP = i;}	public void setMaxWater(float i) {maxThirst = i;}	public void setMaxHunger(float i) {maxHunger = i;}}