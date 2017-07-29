package com.sgstudio.game.village;

public class Village {
	private static long startTime;
	private static float time = 0;
	public void setStartTime(long startTime){ 
		Village.startTime = startTime; 
	}
	public void addHunger(int hunger){
		Village.hunger += hunger;
	}
	
	private float maxHP = 100000f;
	private static float maxthirst = 100f;
	private static float maxhunger = 100f;

	private static float thirst = maxthirst;
	private static float hunger = maxhunger;
	public float HP = maxHP;
	
	private final int DEATH = 0;
	
	public void update(){
		if(time!=(System.currentTimeMillis() - startTime) / 1000){
			time++;
			
			if (HP > maxHP){HP = maxHP;}
			if (thirst > maxthirst){thirst = maxthirst;}
			if (hunger > maxhunger){hunger = maxhunger;}
			
			hunger -= 0.333333333333333333333333333333f;
			thirst -= 0.416666666666666666666666666666f;
			
			
			if (hunger < 0){hunger = 0;}
			if (hunger == 0){
				HP -= 300;
			}
			
			
			if (thirst < 0){thirst = 0;}
			if (thirst == 0){
				HP -= 400;
			}
			
			if (HP < DEATH){HP = DEATH;}
			/*if (thirst > 0){
			System.out.println(hunger);
			System.out.println(thirst);
			} else {
				System.out.println(hunger);
				System.out.println(HP);
			}*/
		}
	}
	public float getHp() {return HP;}
	public float getThirst() {return thirst;}
	public float getHunger() {return hunger;}
	
	
	public void updHunger(float i) {
		if(hunger + i <= 0) {
			hunger = 0;
		} else if(hunger + i > 0) {
			hunger += i;
		} else if(hunger + i > maxhunger) { 
			hunger = maxhunger;
		} else {hunger += i;}
	}
	
	public void updThirst(float i) {
		if(thirst + i <= 0) {
			thirst = 0;
		} else if(thirst + i > 0) {
			thirst += i;
		} else if(thirst + i > maxthirst) { 
			thirst = maxthirst;
		} else {thirst += i;}
	}
	
	public void updHp(float i) {
		if(HP + i <= 0) {
			HP = 0;
		} else if(HP + i > 0) {
			HP += i;
		} else if(HP + i > maxHP) { 
			HP = maxHP;
		} else {HP += i;}
	}
	
	
	public void setHealth(float i) {HP = i;}
	public void setWater(float i) {thirst = i;}
	public void setHunger(float i) {hunger = i;}
	public void setMaxHealth(float i) {maxHP = i;}
}