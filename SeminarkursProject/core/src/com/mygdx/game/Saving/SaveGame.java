package com.mygdx.game.Saving;

import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Christopher Schleppe on 20.12.2017.
 */

public class SaveGame {

    private Vector2 playerPos;

    private int[][] TileID;

    public void setPlayerPos (Vector2 pos){
        playerPos = pos;

    }
    public void setTileID (int x, int y, int id){
        TileID[x][y]  = id;
    }

    public void saveFile (String filepath){
        File outputFile;
        BufferedWriter outputWriter;

        try{
            outputFile = new File(filepath);
            outputWriter = new BufferedWriter(new FileWriter(outputFile));


            outputWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readFile (String filePath){
        File inputFile;
        BufferedReader inputReader;

        try{
            inputFile = new File (filePath);
            inputReader = new BufferedReader(new FileReader(inputFile));


            inputReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }









    /*public static void save (Serializable data, String filename)throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
            oos.writeObject(data);
        }
    }
    public static Object load (String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))){
            return ois.readObject();
        }
    }*/
}
