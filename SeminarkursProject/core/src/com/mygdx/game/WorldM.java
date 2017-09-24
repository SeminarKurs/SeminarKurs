package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.GUI.GUI;
import com.mygdx.game.Types.IVector2;

public class WorldM extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "title";
	public static final IVector2 worldSize = new IVector2(10, 10);

	public static WorldM worldM;

	private static Tile[][] tiles = new Tile[worldSize.x][worldSize.y];

	private GUI gui;
	private PlayerController pController;

	SpriteBatch batch;



	Texture tex;

	private float rotationSpeed = 0.5f;

	protected OrthographicCamera cam;


	@Override
	public void create () {

		for(int x = 0; x < tiles.length ; x++)
			for (int y = 0; y < tiles[0].length; y++)
				tiles[x][y] = new Tile();

		worldM = this;
		rotationSpeed = 0.5f;
		gui = new GUI();

		tex = new Texture("badlogic.jpg");

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		// make a cam that isn't chrunched
		cam = new OrthographicCamera(3, 3 * (h / w));

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();

		pController = new PlayerController(cam);
		pController.UpdPosition();
		Gdx.input.setInputProcessor(pController);

		tiles[1][0].type = 1;
		tiles[1][0].collision = 2;

	}

	public boolean playerCollides(Vector2 move)
	{
		//move.x
		return true;
	}



	@Override
	public void render () {

		//update
		pController.tick(Gdx.graphics.getDeltaTime());

		cam.update();

		// render
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for(int x = 0; x < tiles.length ; x++)
			for(int y = 0; y < tiles[0].length; y++)
			{
				if(tiles[x][y].type == 0)
				{
					//System.out.println(x +" "+y);
					batch.draw(tex, x-0.5f, y-0.5f, 1f, 1f);
				}
			}
		batch.draw(pController.getPlayerTex(), pController.getPosition().x -PlayerController.PlSIZEHX, pController.getPosition().y-PlayerController.PlSIZEHY, PlayerController.PlSIZEHX*2,PlayerController.PlSIZEHY*2);
		batch.end();
	}

	static public void tileClicked(IVector2 postion)
	{
		System.out.println(postion.x +" "+ postion.y);
		if(postion.x < tiles.length && postion.y < tiles[0].length && postion.x >= 0 && postion.y >= 0)
		{
			tiles[postion.x][postion.y].type = 1;
			tiles[postion.x][postion.y].collision = 2;
		}
	}

	static public Vector3 getCamPosition(){return worldM.cam.position;}
	static public float getViewportWidth(){ return worldM.cam.viewportWidth* worldM.cam.zoom;}
	static public float getViewportHeight(){return worldM.cam.viewportHeight* worldM.cam.zoom;}
	static public int getTileCollision(int x, int y){
		if(x < 0 || x > worldM.tiles.length || y < 0 ||y > worldM.tiles[0].length )return 0;return worldM.tiles[x][y].collision;
		}

	@Override
	public void dispose () {
		batch.dispose();
		tex.dispose();
	}
}
