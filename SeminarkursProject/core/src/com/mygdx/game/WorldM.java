package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Actor.Clutch;
import com.mygdx.game.Actor.Conveyor;
import com.mygdx.game.Actor.Direction;
import com.mygdx.game.Actor.DrawH;
import com.mygdx.game.Actor.ElectricActor;
import com.mygdx.game.Actor.ElectricOven;
import com.mygdx.game.Actor.FLayer;
import com.mygdx.game.Actor.Generator;
import com.mygdx.game.Actor.Miner;
import com.mygdx.game.Actor.Oven;
import com.mygdx.game.Actor.Powerline;
import com.mygdx.game.Actor.Resource;
import com.mygdx.game.Actor.SolarPanel;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Player.PlayerController;
import com.mygdx.game.Saving.ResourceManager;
import com.mygdx.game.Saving.SaveData;
import com.mygdx.game.Textures.TexturesClass;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.FMath;
import com.mygdx.game.Tools.IVector2;

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

	// saveKlasse
	private static SaveData saveData;
	// stores all Enemys
	private Array<Enemy> enemies = new Array<Enemy>();

	private SpriteBatch batch;
	private TexturesClass textureClass;
	private float dt;
	private int seed;

	@Override
	public void create ()
	{
		seed = new Random().nextInt(10000000);
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


		Conveyor f = new Conveyor(Direction.right, new ItemMaster(), new IVector2(1,3));
		tiles[1][1].setRes(0);
		Miner m = new Miner(new IVector2(1,1));
		addActor(m, new IVector2(1,1));

		addActor(f, new IVector2(1,3));

		//enemies.add(new Enemy());
		//enemies.get(0).findPath(new IVector2());
		saveData = new SaveData();
		invgui = new InventoryGUI(cam);
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
	// Chris
	static public void load() {
		for (int y = 0; y < tiles[0].length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				tiles[x][y].actor = null;
				tiles[x][y].deleteResource();
			}
		}
		saveData = (SaveData) ResourceManager.load("firstSave");
		playerController.setPosition(new Vector2(saveData.playerX, saveData.playerY));

        float [][]images = saveData.images;

        ItemId[][] actorId = saveData.actorId;
		Direction[][] actorDirection = saveData.actorDirection;
        ItemId[][] actorItemId = saveData.actorItemId;
        int[][] actorItemStackSize = saveData.actorItemStackSize;
        int[][] actorCapacity = saveData.actorCapacity;

		Resource resource[][] = saveData.resources;
		for (int y = 0; y < tiles[0].length; y++) {
			for (int x = 0; x < tiles.length; x++) {

				if(actorId[x][y] != null) {
                    IVector2 pos = new IVector2(x,y);
					switch(actorId[x][y]) {
						case MINER:
							Miner m = new Miner(pos);
                            if(actorItemId[x][y] == ItemId.COAL) m.setItem(ItemList.coal(actorItemStackSize[x][y]));
							addActor(m, new IVector2(x,y));
							break;
                        case CLUTCH:
                            Clutch c = new Clutch(pos, actorDirection[x][y]);
                            if(actorItemId[x][y] == ItemId.COAL) c.setItem(ItemList.coal(actorItemStackSize[x][y]), null);
							addActor(c, pos);
							break;
                        case CONVEYOR:
                            Conveyor con = new Conveyor(actorDirection[x][y], null, pos);
                            if(actorItemId[x][y] == ItemId.COAL) con.setItem(ItemList.coal(actorItemStackSize[x][y]), null);
							addActor(con, new IVector2(x,y));
							break;
						case OVEN:
						    Oven o = new Oven();
                            if(actorItemId[x][y] == ItemId.COAL) o.setItem(ItemList.coal(actorItemStackSize[x][y]), null);
							addActor(o, pos);
							break;
                        case ELECTRICOVEN:
                            ElectricActor ov = new ElectricOven();
                            ov.addCapacity(actorCapacity[x][y]);
							addActor(ov, pos);
                            break;
                        case POWERLINE:
                            Powerline p = new Powerline(pos, actorDirection[x][y]);
                            p.addCapacity(actorCapacity[x][y]);
                            addActor(p, pos);
                            break;
                        case GENERATOR:
                            Generator g = new Generator(pos);
                            g.addCapacity(actorCapacity[x][y]);
                            if(actorItemId[x][y] == ItemId.COAL) g.setItem(ItemList.coal(actorItemStackSize[x][y]), null);
							addActor(g, pos);
                            break;
                        case SOLARPANEL:
                            SolarPanel sp = new SolarPanel(pos);
                            sp.addCapacity(actorCapacity[x][y]);
							addActor(sp, pos);
                            break;
					}
				}
				if (resource[x][y] != null){
					tiles[x][y].setResource(resource[x][y]);
				}
				tiles[x][y].updateColl();

				tiles[x][y].image = images[x][y];
			}
		}
	}
    // Chris
	static public void save() {
		saveData.setPlayer(playerController);
		saveData.setTile(tiles);

		ResourceManager.save(saveData, "firstSave" );
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
		for(Enemy enemy: enemies)
		{
			enemy.move(dt, FMath.getTile(playerController.getPosition()));
		}
		for(int i=0; i < updateActors.size; i++)
		{
			updateActors.get(i).update(dt);
		}

		// camera updaten
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render
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
	public static Actor getActor (IVector2 pos){
		return tiles[pos.x][pos.y].actor;
	}

	static public boolean setItemActor (IVector2 pos, ItemMaster item){
		if(!validTile(pos) || tiles [pos.x][pos.y].item != null) return false;
		tiles [pos.x][pos.y].item = item;
		return true;
	}
	static public void resetItemActor(IVector2 pos){
		tiles[pos.x][pos.y].item = null;
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
