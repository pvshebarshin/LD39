package com.sgstudio.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.sgstudio.game.models.Chair;
import com.sgstudio.game.models.Table;
import com.sgstudio.game.models.Wardrobe;
import com.sgstudio.game.models.Wood;
import com.sgstudio.game.train.Coach;
import com.sgstudio.game.train.Locomotive;

public class MyContactListener implements ContactListener {

	private boolean contact = false;
	private int view = 0;
	private String contactF = "";

	private long startTime;
	private float time = 0;
	private int i = 0;

	private Body bodyToDestroy;
	private World world;
	
	private Object object;

	public MyContactListener(World world) {
		startTime = System.currentTimeMillis();
		this.world = world;
	}

	@Override
	public void beginContact(Contact contact) {

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		WorldManifold manifold = contact.getWorldManifold();

		for (int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
			if (contact.getFixtureB().getUserData() == null || contact.getFixtureA().getUserData() == null) {
				continue;
			}
			
			if (contact.getFixtureB().getUserData().equals("Player") && contact.getFixtureA().getUserData() instanceof Chair) {
				object = contact.getFixtureA().getUserData();
				this.contact = true;
				this.contactF = "Press 'F' to break chair";
				i = 0;
				view = 1;
				contact.setEnabled(false);
				bodyToDestroy = contact.getFixtureA().getBody();
			} else if (contact.getFixtureB().getUserData().equals("Player") && contact.getFixtureA().getUserData() instanceof Table) {
				object = contact.getFixtureA().getUserData();
				this.contact = true;
				this.contactF = "Press 'F' to break table";
				i = 0;
				view = 2;
				contact.setEnabled(false);
				bodyToDestroy = contact.getFixtureA().getBody();
			} else if (contact.getFixtureB().getUserData().equals("Player") && contact.getFixtureA().getUserData() instanceof Wood) {
				object = contact.getFixtureA().getUserData();
				this.contact = true;
				this.contactF = "Press 'F' to raise the wood";
				i = 0;
				view = 2;
				contact.setEnabled(false);
				bodyToDestroy = contact.getFixtureA().getBody();
			} else  if (contact.getFixtureB().getUserData().equals("Monstr") && contact.getFixtureA().getUserData() instanceof Coach) {
				Coach coach = (Coach) contact.getFixtureA().getUserData();
				coach.destroy();
			} else  if (contact.getFixtureB().getUserData().equals("Monstr") && contact.getFixtureA().getUserData() instanceof Locomotive) {
				Locomotive coach = (Locomotive) contact.getFixtureA().getUserData();
				coach.destroy();
			} else  if (contact.getFixtureB().getUserData().equals("Monstr") && contact.getFixtureA().getUserData() instanceof Chair) {
				Chair coach = (Chair) contact.getFixtureA().getUserData();
				coach.destroy();
			} else  if (contact.getFixtureB().getUserData().equals("Monstr") && contact.getFixtureA().getUserData() instanceof Table) {
				Table coach = (Table) contact.getFixtureA().getUserData();
				coach.destroy();
			} else  if (contact.getFixtureB().getUserData().equals("Rails") && contact.getFixtureA().getUserData() instanceof Chair) {
				Chair coach = (Chair) contact.getFixtureA().getUserData();
				coach.onRails();
			} else  if (contact.getFixtureB().getUserData().equals("Rails") && contact.getFixtureA().getUserData() instanceof Table) {
				Table coach = (Table) contact.getFixtureA().getUserData();
				coach.onRails();
			} else if (contact.getFixtureB().getUserData().equals("Player") && contact.getFixtureA().getUserData() instanceof Wardrobe) {
				object = contact.getFixtureA().getUserData();
				this.contact = true;
				this.contactF = "Press 'F' to break wardrobe";
				i = 0;
				view = 4;
				contact.setEnabled(false);
				bodyToDestroy = contact.getFixtureA().getBody();
			} else if (contact.getFixtureA().getUserData().equals("Player") && contact.getFixtureB().getUserData().equals("Firebox")) {
				contact.setEnabled(false);
			} else if (contact.getFixtureA().getUserData().equals("Locomotive") && contact.getFixtureB().getUserData().equals("Player")) {
				this.contact = true;
				this.contactF = "Press 'E' to put the wood";
				i = 0;
			} else if (!contact.getFixtureB().getUserData().equals("Player") && !(contact.getFixtureA().getUserData() instanceof Chair)) {
				if (time != (System.currentTimeMillis() - startTime) / 250) {
					time++;
					i++;
				}
			}

			if (i == 1) {
				this.contact = false;
				this.contactF = "";
				view = 0;
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	public boolean getContact() {
		return contact;
	}

	public String getContactF() {
		return contactF;
	}

	public int getView() {
		return view;
	}

	public int getFuel() {
		if (object == null) {
			return 0;
		}
		
		if (object instanceof Chair) { 
			return ((Chair)object).getFuel();
		}
		if (object instanceof Table) { 
			return ((Table)object).getFuel();
		}
		if (object instanceof Wardrobe) { 
			return ((Wardrobe)object).getFuel();
		}
		
		return 0;
	}
}
