package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Actor.Resource;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Player.PlayerController;
import com.mygdx.game.Textures.TexturesClass;
import com.mygdx.game.Types.IVector2;

import java.util.Random;

/**
 * Created by Tobias on round about 20.08.2017.
 */


public class WorldM extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "title";
	public static final IVector2 worldSize = new IVector2(10, 10);

	// the tiles that represent the world
	private static Tile[][] tiles = new Tile[worldSize.x][worldSize.y];

	// all actors that need to be updated
	private static Array<Actor> updateActors = new Array<Actor>();
	// represents the player
	private static PlayerController pController;
	// the cam (what the player sees)
	protected static OrthographicCamera cam;

	SpriteBatch batch;
	// used for random things
	Random rand = new Random();
	TexturesClass textureClass;

	@Override
	public void create ()
	{
		textureClass = new TexturesClass();		// make new tiles
		for(int x = 0; x < tiles.length ; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y] = new Tile();
				if(rand.nextFloat() <= 0.1f)
				{

				}
			}

		tiles[1][1].resource = new Resource(0);
		addActor(new Actor(), new IVector2(1,2));

		// make a cam that isn't chrunched
		cam = new OrthographicCamera(3, 3.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();

		pController = new PlayerController(cam);
		pController.UpdPosition();
		Gdx.input.setInputProcessor(pController);

		tiles[1][0].image = 1;
		tiles[1][0].collision = 2;
	}

	@Override
	public void render () {
		//update
		float dt = Gdx.graphics.getDeltaTime();
		pController.tick(dt);
		for(int i=0; i < updateActors.size; i++)
		{
			updateActors.get(i).update(dt);
		}

		cam.update();

		// render
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for(int x = 0; x < tiles.length ; x++)
			for(int y = 0; y < tiles[0].length; y++)
			{
				if(TexturesClass.getTextureGround(tiles[x][y].image) != null)
					batch.draw(TexturesClass.getTextureGround (tiles[x][y].image), x-0.5f, y-0.5f, 1f, 1f);
				if(tiles[x][y].resource != null)
				{
					tiles[x][y].resource.draw(batch, x,y);
				}
				if(tiles[x][y].actor != null)
				{
					tiles[x][y].actor.draw(batch, x,y);
				}
			}


		batch.draw(pController.getPlayerTex(), pController.getPosition().x - com.mygdx.game.Player.PlayerController.PlSIZEHX, pController.getPosition().y- com.mygdx.game.Player.PlayerController.PlSIZEHY, com.mygdx.game.Player.PlayerController.PlSIZEHX*2, com.mygdx.game.Player.PlayerController.PlSIZEHY*2);
		batch.end();
	}

	static public void tileClicked(IVector2 postion)
	{
		if(postion.x < tiles.length && postion.y < tiles[0].length && postion.x >= 0 && postion.y >= 0)
		{
			if(tiles[postion.x][postion.y].resource != null)
			{
				if(tiles[postion.x][postion.y].resource.amount - pController.getMineSpeed() <= 0)
				{
					tiles[postion.x][postion.y].resource = null;
				}else {
					tiles[postion.x][postion.y].resource.amount -= pController.getMineSpeed();
					System.out.println("Resourcetype: " + tiles[postion.x][postion.y].resource.getType() + " Amount:"+ (tiles[postion.x][postion.y].resource.amount - pController.getMineSpeed()));
				}
			}
		}
	}



	public static Actor addActor(Actor actor, IVector2 pos)
	{
		if(actor != null) {
			if (addTileActor(actor, pos)) {
				if (actor.GetNeedUpdate()) {
					updateActors.add(actor);
				}
				if(actor.coll() != 0)// if the actor wouldn't collied then we don't need to change anything
				{
					if(getTileCollision(pos) != 2)// if the actor is 2 we don't need to change anything
					{
						tiles[pos.x][pos.y].collision = actor.coll();
					}
				}
				return actor;
			}
		}
		return null;
	}

	// add a actor to tile
	static private boolean addTileActor(Actor actor, IVector2 pos)
	{
		if(validTile(pos))
		{
			System.out.println("Cos: " + actor.coll());
			if(tiles[pos.x][pos.y].actor == null)
				tiles[pos.x][pos.y].actor = actor;

			return true;
		}
		return false;
	}

	static public Vector3 getCamPosition(){return cam.position;}
	static public float getViewportWidth(){ return cam.viewportWidth* cam.zoom;}
	static public float getViewportHeight(){return cam.viewportHeight* cam.zoom;}
	static public int getTileCollision(int x, int y){ if(x < 0 || x >= tiles.length || y < 0 ||y >= tiles[0].length )return 0;return tiles[x][y].collision; }
	static public int getTileCollision(IVector2 pos){ if(pos.x < 0 || pos.x >= tiles.length || pos.y < 0 || pos.y >= tiles[0].length )return 0;return tiles[pos.x][pos.y].collision; }
	// see if a tile is valid
	static public boolean validTile(int x, int y){if(x < 0 || x >= tiles.length || y < 0 ||y >= tiles[0].length ) return false;return true;}
	static public boolean validTile(IVector2 pos){if(pos.x < 0 || pos.x >= tiles.length || pos.y < 0 ||pos.y >= tiles[0].length ) return false;return true;}
	// gets the texture by num


	@Override
	public void dispose () {
		batch.dispose();
		textureClass.dispose();
	}
}
