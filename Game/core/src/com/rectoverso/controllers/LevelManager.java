package com.rectoverso.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.rectoverso.RVGame;


/**
 * Manages the levels.
 */
public class LevelManager
{
    //private final Level levels;

    /**
     * Creates the level manager.
     */
	private static final String BALISE_SIDE = "side";
	private static final String BALISE_TILE = "tile";
	
    public LevelManager()
    {
        // create the level 

        // register the levels
        
    }
    public void loadLevel(FileHandle file){
    	XmlReader xml = new XmlReader();
    	XmlReader.Element xml_element;
		try {
			xml_element = xml.parse(file);
	    	Iterator iterator_level = xml_element.getChildrenByName(BALISE_SIDE).iterator();
	    	while(iterator_level.hasNext()){
	    	     XmlReader.Element level_element = (XmlReader.Element)iterator_level.next();
	    	     /*String level_number = level_element.getAttribute("number");
	    	     String level_status = level_element.getAttribute("status");*/
	    	     
	    	     //parser les éléments et construire la carte
	    	     
	    	 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void saveLevel (/*Map recto , Map verso*/){
    	StringWriter writer = new StringWriter();
    	 XmlWriter xml = new XmlWriter(writer);
    	 try {
			xml.element("level")
			 	.attribute("name", "")
			 	.attribute("background", "")
				.element("map")
				        .attribute("side", "recto")
				        .element("tile")
				                .attribute("x", ""/*recto.tile(0,0).x*/)
				                .attribute("y", ""/*recto.tile(0,0).y*/)
				        .pop()
				.pop()
				.element("map")
						.attribute("side", "verso")
						.element("tile")
				                .attribute("x", ""/*verso.tile(0,0).x*/)
				                .attribute("y", ""/*verso.tile(0,0).y*/)
				        .pop()
				.pop()
			.pop();
			
			//TO BE CONTINUED... On prendra garde à privilégier les constantes et enums
			 
			Gdx.app.log(RVGame.LOG, writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void setItem (/*Item item , Map carte , int tileX , int tileY , boolean recto*/){
    	//ajoute un item à l'emplacement indiqué, si une autre s'y trouve déja, il sera remplacé
    }
    public void setTile (/*Tuile tile, Map carte , int tileX , int tileY , boolean recto*/){
    	//ajoute une tuile à l'emplacement indiqué, si une autre s'y trouve déja, il sera remplacé
    }
    public void setBackground (/*Background bg, Map carte */){
    	//ajoute ou remplace le background
    }
    public void render(/*Map carte*/){
    	
    }
}
