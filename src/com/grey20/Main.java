package com.grey20;

import raylib.*;

import static raylib.Color.*;
import static raylib.Raylib.*;
import static raylib.TextureFilterMode.*;

/**
 * Main class.
 */
public class Main {

    /**
     * Screen width.
     */
    public static final int WIDTH = 1000;

    /**
     * Screen height.
     */
    public static final int HEIGHT = 800;

    /**
     * Main.
     */
    public static void main(String[] args) {
        InitWindow(WIDTH, HEIGHT, "Recycling Game");
        SetTargetFPS(60);
        Texture2D bg = LoadTexture("BG.png");
        while (!WindowShouldClose()) {
            BeginDrawing();
                ClearBackground(new Color(255, 255, 255, 255));
                DrawTexture(bg, 0, 0, new Color(255, 255, 255));
                Draw();
            EndDrawing();
            Update();
        }
        CloseWindow();
    }

    /**
     * Draw stuff.
     */
    public static void Draw() {
        DrawText("Money: $" + Resources.Money, 10, 10, 50, new Color(255, 255, 255));
    }

    /**
     * Update stuff.
     */
    public static void Update() {

    }

    /**
     * Update what bin the item intersects with, and returns if it intersects with a bin.
     * @param item Item to update.
     * @return If there is an intersection.
     */
    public static boolean UpdateBinIntersection(Item item) {
        return false;
    }


    public static double monetaryChange (Item item)
    {
        final int [] plasticPunishments = {-10,-15,-5,-7};
        final int [] platicRewards = {20,
        final int otherPunishments =-1;
        final int groundPunishment = -20;

        boolean isPlastic = item.isPlastic();
        int answer = 0;

        if(item.Bin.equals("Compost"))
        {
            if(item.MaterialType==item.Material.Food||item.MaterialType==item.Material.Plastic7PLA)
            {
                int answer = ;
            }
            else
            {
            }
        }
        else if(item.Bin.equals("Recycling"))
        {

        }
        else if(item.Bin.equals("Trash"))
        {

        }
        else if (item.Bin.equals("Scrap"))
        {

        }
        else
        {
            answer = groundPunishment;
        }
    }
}