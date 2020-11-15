package com.grey20;

import raylib.*;

import static raylib.Color.*;
import static raylib.Raylib.*;
import static raylib.TextureFilterMode.*;

import com.grey20.Item.Material;

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
     * Update what bin the item intersects with, and returns if it intersects with a
     * bin.
     * 
     * @param item Item to update.
     * @return If there is an intersection.
     */
    public static boolean UpdateBinIntersection(Item item) {
        return false;
    }

    public static int[] monetaryChangeKillCount(Item item) {
        int answer = 0;
        int killCount = 0;

        final int[] plasticRewards = { 15, 17, 18, 20 };
        final int[] plasticPunishments = { -5, -7, -10, -15 };
        final int otherRewards = 5;
        final int otherPunishments = -4;
        final int groundPunishment = -20;

        if (item.Bin == null) {
            answer += groundPunishment;
            System.out.println("You've killed 5 fish.");
        } else if (item.Bin.equals("Compost")) {
            if (item.MaterialType == Material.Plastic7PLA) {
                answer += plasticRewards[3];
            } else if (item.MaterialType == Material.Food) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[3];
                System.out.println("You've killed a tree.");
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Recycling")) {
            if (item.MaterialType == Material.Plastic1 || item.MaterialType == Material.Plastic2) {
                answer += plasticRewards[1];
            } else if (item.MaterialType == Material.Plastic4 || item.MaterialType == Material.Plastic5) {
                answer += plasticRewards[4];
                System.out.println("Although plastic 4 (LDPE) and plastic 5 (PPE) are technically recyclable, \n"
                        + "whether or not they can be recycled where you are located may depend. \n"
                        + "Check witb your local collection services first before recycling.");
            } else if (item.MaterialType == Material.AluminumSteel || item.MaterialType == Material.PaperCardboard
                    || item.MaterialType == Material.Glass) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[4];
                System.out.println("You've killed a deer.");
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Trash")) {
            if (item.MaterialType == Material.Plastic3) {
                answer += plasticRewards[1];
            } else if (item.MaterialType == Material.Plastic6) {
                answer += plasticRewards[4];
            } else if (item.MaterialType == Material.Plastic7) {
                answer += plasticRewards[17];
            } else if (item.isPlastic()) {
                answer += plasticPunishments[1];
                System.out.println("You've killed a turtle.");
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Scrap")) {
            if (item.MaterialType == Material.MetalOther) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[2];
                System.out.println("You've killed a bird.");
            } else {
                answer += otherPunishments;
            }
        }
        int[] retVal = { answer, killCount };
        return retVal;
    }
}