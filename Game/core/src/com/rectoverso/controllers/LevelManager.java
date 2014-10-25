package com.rectoverso.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
	public static Level loadLevel(FileHandle file , Level level){
		ArrayList<Map> level_maps = new ArrayList<Map>();
		XmlReader reader = new XmlReader();

		try {
			Element root = reader.parse(file);
			Array<Element> maps = root.getChildrenByName("map");

			/*LevelType level_type = null;

			if(root.getAttribute("type").equals("NORMAL"))
				level_type = LevelType.NORMAL;
			else if(root.getAttribute("type").equals("MOVIE"))
				level_type = LevelType.MOVIE;
			else if(root.getAttribute("type").equals("SECRET"))
				level_type = LevelType.SECRET;*/

			Player level_player = new Player(new Vector2(0, 0), new Vector2(0, 0),Side.RECTO);
			
			int level_sizeRow= Integer.parseInt(root.getAttribute("sizeRow"));
			int level_sizeCol= Integer.parseInt(root.getAttribute("sizeCol"));
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

					map_tiles.add(new Tile(
							Integer.parseInt(tile.getAttribute("col")),
							Integer.parseInt(tile.getAttribute("row")),
							tile_content,
							tile_collision));
				}

				Array<Element> entities = map.getChildrenByName("entities");
				// A finir
				for(Element entity : entities){

				}

				level_maps.add(new Map(map_tiles, map_entities, map_side));
			}
			level.loadLevel(level_background, level_maps, level_sizeRow,level_sizeCol, level_player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}

	public static void saveLevel (Level level, String fileName){
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		
		Map recto = level.getMap(Side.RECTO);
		Map verso = level.getMap(Side.VERSO);
		
		try {
			//<level type="NORMAL" name="Des pierres oubliées" locked="false" size="2" background="" number="1">
			xml = xml.element("level")
			.attribute("type", level.getType())
			.attribute("name", level.getName())
			.attribute("sizeRow", level.getSizeRow())
			.attribute("sizeCol", level.getSizeCol())
			.attribute("background", level.getBackground())
			.element("map")
				.attribute("side", "RECTO");
				
				for(int i = 0 ; i< level.getSizeRow() ; i++){
					for(int j = 0 ; j< level.getSizeCol() ; j++){
						//<tile contentType="BASE_GRASSGROUND" collisionType="COLLISION" row="0" col="0">
						Tile tile = recto.getTile(i,j , level);
						xml = 
								xml.element("tile")
									.attribute("contentType", tile.getTileContent())
									.attribute("collisionType", tile.getTileCollision())
									.attribute("row", i)
									.attribute("col", j)
								.pop();
					}
				}
			xml = xml.pop()
			.element("map")
				.attribute("side", "VERSO");
			
				for(int i = 0 ; i< level.getSizeRow() ; i++){
					for(int j = 0 ; j< level.getSizeCol() ; j++){
						//<tile contentType="BASE_GRASSGROUND" collisionType="COLLISION" row="0" col="0">
						Tile tile = verso.getTile(i,j , level);
						xml = 
								xml.element("tile")
									.attribute("contentType", tile.getTileContent())
									.attribute("collisionType", tile.getTileCollision())
									.attribute("row", i)
									.attribute("col", j)
								.pop();
					}
				}
				
				xml = xml.pop()
			.pop();

			//TO BE CONTINUED... On prendra garde ï¿½ privilï¿½gier les constantes et enums

			xml.close();
			Gdx.app.log(RVGame.LOG, writer.toString());
			
			//ecrire le fichier
			FileHandle file = Gdx.files.local( "bin/levels/"+fileName +".xml");
			Gdx.app.log(RVGame.LOG,Gdx.files.isLocalStorageAvailable() + Gdx.files.getLocalStoragePath() +" " + file.path());
			file.writeString(writer.toString(), false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
