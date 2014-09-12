package com.rectoverso.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.rectoverso.RVGame;
import com.rectoverso.model.Level;
import com.rectoverso.model.Level.LevelType;
import com.rectoverso.model.World;

public class LevelSelectManager {

	private World worldSelected ;
	private Level levelSelected ;
	private ArrayList<World>worlds = new ArrayList<World>();
	
	public void parseWorlds(){
		
		FileHandle file = Gdx.files.internal( "levels/worlds.xml" );
		
		int worldCount = 0;
		int levelCount = 0;
		int secretCount = 0;
		
		XmlReader xml = new XmlReader();
    	XmlReader.Element xml_element;
		try {
			xml_element = xml.parse(file);
	    	Iterator iterator_world = xml_element.getChildrenByName("world").iterator();
	    	Gdx.app.log(RVGame.LOG, "Select Level : init parse of worlds");
	    	
	    	//Iteration des mondes
	    	while(iterator_world.hasNext()){
	    	     XmlReader.Element world_element = (XmlReader.Element)iterator_world.next();
	    	     Gdx.app.log(RVGame.LOG, "World :"+ worldCount +" - " + world_element.getAttribute("name"));
	    	     
	    	     World world = new World(worldCount, world_element.getAttribute("name"));
	    	     
	    	     //Iteration des niveaux
	    	     levelCount = 1;
	    	     secretCount = 1;
	    	     Iterator iterator_level = world_element.getChildrenByName("level").iterator();
	    	     while(iterator_level.hasNext()){
	    	    	 XmlReader.Element level_element = (XmlReader.Element)iterator_level.next();
	    	    	 Gdx.app.log(RVGame.LOG, "Level :"+ levelCount +" - " + level_element.getAttribute("name"));
	    	    	 
	    	    	 Level level = new Level(
	    	    			 levelCount, 
	    	    			 level_element.getAttribute("name"), 
	    	    			 LevelType.valueOf( level_element.getAttribute("type"))
	    	    			 );
	    	    	 world.addLevel(level);
	    	    	 
	    	    	 //Iteration des niveaux secrets
	    	    	 Iterator iterator_secret = level_element.getChildrenByName("secret").iterator();
	    	    	 while(iterator_secret.hasNext()){
		    	    	 XmlReader.Element secret_element = (XmlReader.Element)iterator_secret.next();
		    	    	 Gdx.app.log(RVGame.LOG, "Secret :"+ secretCount +" - " + secret_element.getAttribute("name"));
		    	    	 
		    	    	 Level secret = new Level(
		    	    			 levelCount, 
		    	    			 level_element.getAttribute("name"), 
		    	    			 LevelType.secret
		    	    			 );
		    	    	 world.addLevel(secret);
		    	    	 secretCount ++;
	    	    	 }
	    	    	 
	    	    	 
	    	    	 levelCount ++;
	    	     }
	    	     
	    	     
	    	     if (worldCount != 0){
	    	    	 world.setLocked(world_element.getBooleanAttribute("locked"));
	    	     }
	    	     else{
	    	    	 world.setLocked(false);
	    	     }
	    	     worlds.add(world);
	    	     worldCount ++;
	    	     /*String level_number = level_element.getAttribute("number");
	    	     String level_status = level_element.getAttribute("status");*/
	    	     
	    	     //parser les éléments et construire la carte
	    	     
	    	 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Level getLevelSelected(){
		return levelSelected;
	}
	
	public void setSelectedWorld(int number){
		for(World w : worlds){
			if (w.getNumber() == number){
				this.worldSelected = w;
				return;
			}
		}
	}

	public World getWorldSelected() {
		// TODO Auto-generated method stub
		return worldSelected;
	}
}
