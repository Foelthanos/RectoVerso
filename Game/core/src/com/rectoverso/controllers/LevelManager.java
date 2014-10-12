package com.rectoverso.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.rectoverso.RVGame;
import com.rectoverso.model.Entity;
import com.rectoverso.model.Level;
import com.rectoverso.model.Level.LevelType;
import com.rectoverso.model.Map;
import com.rectoverso.model.Map.Side;
import com.rectoverso.model.Player;
import com.rectoverso.model.Tile;
import com.rectoverso.model.Tile.TileCollision;
import com.rectoverso.model.Tile.TileContent;
import com.rectoverso.utils.LevelException;


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
	public static Level loadLevel(FileHandle file){
		ArrayList<Map> level_maps = new ArrayList<Map>();
		Level level = null;
		XmlReader reader = new XmlReader();

		try {
			Element root = reader.parse(file);
			Array<Element> maps = root.getChildrenByName("map");

			LevelType level_type = null;

			if(root.getAttribute("type").equals("NORMAL"))
				level_type = LevelType.NORMAL;
			else if(root.getAttribute("type").equals("MOVIE"))
				level_type = LevelType.MOVIE;
			else if(root.getAttribute("type").equals("SECRET"))
				level_type = LevelType.SECRET;

			Player level_player = new Player(new Vector2(50, 0), new Vector2(0, 0));
			int level_number = Integer.parseInt(root.getAttribute("number"));
			String level_name = root.getAttribute("name");
			boolean level_locked = (root.getAttribute("locked") == "true")?true:false;
			int level_scaleX = Integer.parseInt(root.getAttribute("scaleX"));
			int level_scaleY = Integer.parseInt(root.getAttribute("scaleY"));
			String level_background = (root.getAttribute("background")=="")?null:root.getAttribute("background");

			for (Element map : maps)
			{
				Side map_side = (map.getAttribute("side").equals("RECTO"))?Side.RECTO:Side.VERSO;
				ArrayList<Tile> map_tiles = new ArrayList<Tile>();
				ArrayList<Entity> map_entities = new ArrayList<Entity>();
				TileContent tile_content;
				TileCollision tile_collision;
				Array<Element> tiles = map.getChildrenByName("tile");

				for(Element tile : tiles){
					String tmp = tile.getAttribute("contentType");
					if(tmp.equals("BASE_GRASSGROUND"))
						tile_content = TileContent.BASE_GRASSGROUND;
					else if(tmp.equals("BASE_TECHNOGROUND"))
						tile_content = TileContent.BASE_TECHNOGROUND;
					else 
						tile_content = TileContent.NOTHING; 

					tmp = tile.getAttribute("collisionType");
					if(tmp.equals("NO_COLLISION"))
						tile_collision = TileCollision.NO_COLLISION;
					else if(tmp.equals("COLLISION"))
						tile_collision = TileCollision.COLLISION;
					else 
						tile_collision = TileCollision.NO_COLLISION; 

					map_tiles.add(new Tile(new Vector2(Integer.parseInt(tile.getAttribute("posX")),Integer.parseInt(tile.getAttribute("posY")))
					, tile_content, tile_collision));
				}

				Array<Element> entities = map.getChildrenByName("entities");
				// A finir
				for(Element entity : entities){

				}

				level_maps.add(new Map(map_tiles, map_entities, map_side));
			}
			level = new Level(level_number, level_name, level_type, 
					level_locked, level_background, level_maps, level_scaleX, level_scaleY, level_player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}

	public static void saveLevel (/*Level level*/){
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

			//TO BE CONTINUED... On prendra garde � privil�gier les constantes et enums

			Gdx.app.log(RVGame.LOG, writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setItem (/*Item item , Map carte , int tileX , int tileY , boolean recto*/){
		//ajoute un item � l'emplacement indiqu�, si une autre s'y trouve d�ja, il sera remplac�
	}
	public void setTile (/*Tuile tile, Map carte , int tileX , int tileY , boolean recto*/){
		//ajoute une tuile � l'emplacement indiqu�, si une autre s'y trouve d�ja, il sera remplac�
	}
	public void setBackground (/*Background bg, Map carte */){
		//ajoute ou remplace le background
	}
	public void render(/*Map carte*/){

	}
}
