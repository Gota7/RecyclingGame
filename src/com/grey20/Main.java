package com.grey20;

import raylib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Fun value.
     */
    public static final float FUN_VALUE = 200;

    /**
     * Timer.
     */
    private static float Timer = 0;

    /**
     * Current items in the game.
     */
    public static ArrayList<Item> Items = new ArrayList<Item>();

    /**
     * Items that can be spawned.
     */
    public static final HashMap<String, Item.Material> AVAILABLE_ITEMS = new HashMap<String, Item.Material>() {
        {
            put("Paper", Item.Material.PaperCardboard);
        }
    };

    /**
     * Main.
     */
    public static void main(String[] args) {
        InitWindow(WIDTH, HEIGHT, "Recycling Game");
        SetTargetFPS(60);
        Texture2D bg = LoadTexture("BG.png");
        Resources.Textures.put("Bins", LoadTexture("Bins.png"));
        InitResources();
        SpawnItem();
        while (!WindowShouldClose()) {
            BeginDrawing();
                ClearBackground(Resources.White);
                DrawTexture(bg, 0, 0, Resources.White);
                Draw();
            EndDrawing();
            Update();
        }
        CloseWindow();
    }

    /**
     * Init resources.
     */
    public static void InitResources() {
        Resources.Textures.put("Paper", LoadTexture("Paper.png"));
    }

    /**
     * Spawn an item.
     */
    private static void SpawnItem() {

        //Spawn a random item.
        int ind = (int)(Math.random() * AVAILABLE_ITEMS.size());
        String key = (String)AVAILABLE_ITEMS.keySet().toArray()[ind];
        Items.add(new Item(key, AVAILABLE_ITEMS.get(key)));

    }

    /**
     * If two hitboxes collide.
     * @param pos1 Position 1.
     * @param hitbox1 Hitbox 1.
     * @param pos2 Position 2.
     * @param hitbox2 Hitbox 2.
     * @return If they collide.
     */
    public static boolean Collides(Vector2 pos1, Vector2 hitbox1, Vector2 pos2, Vector2 hitbox2) {
        if (pos1.getX() < pos2.getX() + hitbox2.getX() &&
                pos1.getX() + hitbox1.getX() > pos2.getX() &&
                pos1.getY() < pos2.getY() + hitbox2.getY() &&
                pos1.getY() + hitbox1.getY() > pos2.getY())
        {
            return true;
        }
        return false;
    }

    /**
     * Draw stuff.
     */
    public static void Draw() {

        //Money.
        DrawText("Money: $" + Resources.Money, 10, 10, 50, Resources.White);

        //Bins.
        DrawTexture(Resources.Textures.get("Bins"), 0, 0, Resources.White);

        //Draw items.
        for (int i = 0; i < Items.size(); i++) {
            Items.get(i).Draw();
        }

    }

    /**
     * Update stuff.
     */
    public static void Update() {

        //Spawn item if needed.
        Timer++;
        if (Timer > Math.max(FUN_VALUE - Resources.GetLevel() * 5, 10)) {
            SpawnItem();
            Timer = 0;
        }

        //Update items.
        for (int i = 0; i < Items.size(); i++) {
            Items.get(i).Update();
        }

        //Remove dead items.
        for (int i = Items.size() - 1; i >= 0; i--) {
            if (Items.get(i).Dead) {
                //TODO!!! KILL CODE!!!
                Items.remove(i);
            }
        }

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

        if (item.Bin.equals("Compost")) {
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
        } else {
            answer += groundPunishment;
            System.out.println("You've killed 5 fish.");
        }
        int[] retVal = { answer, killCount };
        return retVal;
    }
}