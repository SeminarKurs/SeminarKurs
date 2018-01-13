package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Actor.Conveyor;
import com.mygdx.game.Actor.DrawH;
import com.mygdx.game.Actor.FLayer;
import com.mygdx.game.Actor.Miner;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Enemy.PathFinding.PathFinding;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Player.PlayerController;
import com.mygdx.game.Textures.TexturesClass;
import com.mygdx.game.Types.Collision;
import com.mygdx.game.Types.IVector2;

import java.util.Random;

/**
 * Created by Tobias on round about 20.08.2017.
 */


public class WorldM extends ApplicationAdapter {

	Random rd = new Random();
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "title";
	public static final IVector2 worldSize = new IVector2(50, 50);

	// the tiles that represent the world
	private static Tile[][] tiles = new Tile[worldSize.x][worldSize.y];

	// all actors that need to be updated
	private static Array<Actor> updateActors = new Array<Actor>();
	// represents the player
	private static PlayerController playerController;
	// the cam (what the player sees)
	protected static OrthographicCamera cam;
	// UI for inventory
	private InventoryGUI invgui;

	// stores all Enemys
	private Array<Enemy> enemies = new Array<Enemy>();

	private SpriteBatch batch;
	// used for random things
	private Random rand = new Random();
	private TexturesClass textureClass;
	private float dt;
	private int seed;

	@Override
	public void create ()
	{
		seed = new Random().nextInt(10000000);
		seed = 121345;
		textureClass = new TexturesClass();		// make new tiles
		for(int x = 0; x < tiles.length ; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y] = new Tile();
			}
		//ganerate the world
		generate();

		//create the inventory
		Inventory.playerInventory = new Inventory();
		Inventory.playerInventory.addStarterItems();

		//Actor a = (Actor)new TestActor();
		// make a cam that isn't chrunched
		cam = new OrthographicCamera(3, 3.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();

		playerController = new PlayerController(cam);

		Gdx.input.setInputProcessor(playerController);

		Conveyor f = new Conveyor(4, new ItemMaster(), new IVector2(1,3));
		tiles[1][1].setRes(0);
		Miner m = new Miner(new IVector2(1,1));
		addActor(m, new IVector2(1,1));

		addActor(f, new IVector2(1,3));

		new PathFinding();
		enemies.add(new Enemy());
	}

	private void generate() {

		Random rd = new Random();

		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				//rd.setSeed(x * 123450 + y *23292367 + seed);
				float alpha = getSmoothNum(x,y); //rd.nextFloat();

				tiles[x][y].image = alpha;

				rd.setSeed(x * 908234 + y *234578 + seed);
				int res = rd.nextInt(TexturesClass.getTexturesResourceLenght() * 10);
				if(res < TexturesClass.getTexturesResourceLenght())
				{
					tiles[x][y].setRes(res);
				}
			}
	}
	private float getSmoothNum(int x, int y)
	{
		return ((getNum(x+1,y+1) + getNum(x+1,y-1) + getNum(x-1,y+1) + getNum(x-1,y-1))/32f + (getNum(x+1,y) + getNum(x,y+1) + getNum(x-1,y) + getNum(x,y-1))/8f + getNum(x,y)/2f);
	}

	private float getNum(int x, int y)// get the random float to the position
	{
		rd.setSeed(x * 123450 + y *23292367 + seed);
		return rd.nextFloat();
	}

	@Override
	public void render () {
		//update
		dt = Gdx.graphics.getDeltaTime();
		playerController.update(dt);
		for(int i=0; i < updateActors.size; i++)
		{
			updateActors.get(i).update(dt);
		}

		cam.update();

		// render
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		Array<FLayer> fLayers = new Array<FLayer>();
		for(int x = 0; x < tiles.length ; x++)
			for(int y = 0; y < tiles[0].length; y++)
			{
				// draw ground
				Color color = batch.getColor();

				color.a = 1;
				batch.setColor(color);
				batch.draw(TexturesClass.getTextureGround(0), x - 0.5f, y - 0.5f, 1f, 1f);
				color.a = tiles[x][y].image;

				batch.setColor(color);
				batch.draw(TexturesClass.getTextureGround(1), x - 0.5f, y - 0.5f, 1f, 1f);


				// reset alpha

				color.a = 1;
				batch.setColor(color);
				//draw Resources
				if(tiles[x][y].hasRes())
				{
					tiles[x][y].resDraw(batch, x,y);
				}
				// draw Actor
				if(tiles[x][y].actor != null)
				{
					tiles[x][y].actor.draw(batch, x,y, fLayers);
				}
				// draw Item
				if(tiles[x][y].item != null)
				{
					DrawH.drawItemActor(batch, x, y, tiles[x][y].item.getImage());
				}
			}

		for(int i = 0; i < fLayers.size; i++)
		{
			DrawH.drawItemActor(batch,fLayers.get(i).x, fLayers.get(i).y, fLayers.get(i).image);
		}
		for(int i = 0; i < enemies.size; i++)
			batch.draw(TexturesClass.getTextureEnemy(enemies.get(i).getImage()), enemies.get(i).getPosition().x - Enemy.ENEMYSIZEHX ,enemies.get(i).getPosition().y - Enemy.ENEMYSIZEHY, Enemy.ENEMYSIZEHX*2, Enemy.ENEMYSIZEHY*2);
		batch.draw(playerController.getPlayerTex(), playerController.getPosition().x - com.mygdx.game.Player.PlayerController.PlSIZEHX, playerController.getPosition().y- com.mygdx.game.Player.PlayerController.PlSIZEHY, com.mygdx.game.Player.PlayerController.PlSIZEHX*2, com.mygdx.game.Player.PlayerController.PlSIZEHY*2);
		batch.end();

		invgui = new InventoryGUI(cam);
		invgui.render();
	}
	static public Tile getResource(IVector2 posi)
	{
		return tiles[posi.x][posi.y];
	}

	public static Actor addActor(Actor actor, IVector2 pos)
	{
		// valid actor?
		if(actor != null) {

			if (addTileActor(actor, pos)) {
				if (actor.needUpdate()) {
					updateActors.add(actor);
				}
				if(actor.coll() == Collision.collides)// if the actor wouldn't collied then we don't need to change anything
				{
					tiles[pos.x][pos.y].collision = actor.coll();
				}
				return actor;
			}
		}
		return null;
	}

	static public boolean setItemActor (IVector2 pos, ItemMaster item){
		if(!validTile(pos) || tiles [pos.x][pos.y].item != null) return false;
		tiles [pos.x][pos.y].item = item;
		return true;
	}

	static public void setCollision(IVector2 pos)
	{
		tiles[pos.x][pos.y].collision = Collision.collides;
		tiles[pos.x][pos.y].image = 10000;
	}

	// add a actor to tile
	static private boolean addTileActor(Actor actor, IVector2 pos)
	{
		if(validTile(pos))
		{
			if(tiles[pos.x][pos.y].actor == null)
			{
				tiles[pos.x][pos.y].actor = actor;
				return true;
			}
		}
		return false;
	}
	@Override
	public void resize(int width, int height) {

		cam.setToOrtho(false, 3, 3.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		playerController.updCamPos();
		super.resize(width, height);

	}

	static public Vector3 getCamPosition(){return cam.position;}
	static public float getViewportWidth(){ return cam.viewportWidth* cam.zoom;}
	static public float getViewportHeight(){return cam.viewportHeight* cam.zoom;}
	static public Collision getTileCollision(int x, int y){ if(x < 0 || x >= tiles.length || y < 0 ||y >= tiles[0].length )return Collision.none;return tiles[x][y].collision; }
	static public Collision getTileCollision(IVector2 pos){ if(pos.x < 0 || pos.x >= tiles.length || pos.y < 0 || pos.y >= tiles[0].length )return Collision.none;return tiles[pos.x][pos.y].collision; }
	// see if a tile is valid
	static public boolean validTile(int x, int y){if(x < 0 || x >= tiles.length || y < 0 ||y >= tiles[0].length ) return false;return true;}
	static public boolean validTile(IVector2 pos){if(pos.x < 0 || pos.x >= tiles.length || pos.y < 0 ||pos.y >= tiles[0].length ) return false; return true;}
	static public void updateResource(IVector2 pos){tiles[pos.x][pos.y].refresh();}
	static public boolean hasResource (IVector2 pos){ return (validTile(pos) && tiles[pos.x][pos.y].hasRes()); }


	@Override
	public void dispose () {
		batch.dispose();
		textureClass.dispose();
		//invgui.dispose();
	}
}
