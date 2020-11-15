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
    public static final float FUN_VALUE = 130;

    /**
     * Timer.
     */
    private static float Timer = 0;

    /**
     * If game over.
     */
    private static boolean IsGameOver;

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
            put("Cardboard", Item.Material.PaperCardboard);
            put("Food", Item.Material.Food);
            put("Glass", Item.Material.Glass);
            put("Brass", Item.Material.MetalOther);
            put("Plastic1", Item.Material.Plastic1);
            put("Plastic2", Item.Material.Plastic2);
            put("Plastic3", Item.Material.Plastic3);
            put("Plastic4", Item.Material.Plastic4);
            put("Plastic5", Item.Material.Plastic5);
            put("Plastic6", Item.Material.Plastic6);
            put("Plastic7", Item.Material.Plastic7);
            put("Plastic7PLA", Item.Material.Plastic7PLA);
        }
    };

    /**
     * Bin hitboxes.
     */
    public static final HashMap<String, Vector2[]> BIN_HITBOXES = new HashMap<>() {
        {
            put("Trash", new Vector2[] { new Vector2(132, 449), new Vector2(194, 264) });
            put("Recycling", new Vector2[] { new Vector2(358, 463), new Vector2(184, 235) });
            put("Compost", new Vector2[] { new Vector2(562, 503), new Vector2(206, 200) });
            put("Scrap", new Vector2[] { new Vector2(772, 471), new Vector2(221, 244) });
        }
    };

    /**
     * Main.
     */
    public static void main(String[] args) {
        InitWindow(WIDTH, HEIGHT, "Recycling Game");
        SetTargetFPS(60);
        Texture2D bg = LoadTexture("BG.png");
        InitGame();
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
     * Init game.
     */
    private static void InitGame() {
        Resources.NumSuccesses = 0;
        Resources.Money = 50;
        Resources.Kills = 0;
        IsGameOver = false;
    }

    /**
     * Init resources.
     */
    public static void InitResources() {
        Resources.Textures.put("Paper", LoadTexture("paper.png"));
        Resources.Textures.put("Cardboard", LoadTexture("cardboard.png"));
        Resources.Textures.put("Food", LoadTexture("food.png"));
        Resources.Textures.put("Glass", LoadTexture("glass.png"));
        Resources.Textures.put("Brass", LoadTexture("Brass.png"));
        Resources.Textures.put("Plastic1", LoadTexture("plastic1.png"));
        Resources.Textures.put("Plastic2", LoadTexture("plastic2.png"));
        Resources.Textures.put("Plastic3", LoadTexture("plastic3.png"));
        Resources.Textures.put("Plastic4", LoadTexture("plastic4.png"));
        Resources.Textures.put("Plastic5", LoadTexture("plastic5.png"));
        Resources.Textures.put("Plastic6", LoadTexture("plastic6.png"));
        Resources.Textures.put("Plastic7", LoadTexture("plastic7.png"));
        Resources.Textures.put("Plastic7PLA", LoadTexture("plastic7pla.png"));
        Resources.Textures.put("Bins", LoadTexture("Bins.png"));
    }

    /**
     * Spawn an item.
     */
    private static void SpawnItem() {

        // Spawn a random item.
        int ind = (int) (Math.random() * AVAILABLE_ITEMS.size());
        String key = (String) AVAILABLE_ITEMS.keySet().toArray()[ind];
        Items.add(new Item(key, AVAILABLE_ITEMS.get(key)));

    }

    /**
     * If two hitboxes collide.
     * 
     * @param pos1    Position 1.
     * @param hitbox1 Hitbox 1.
     * @param pos2    Position 2.
     * @param hitbox2 Hitbox 2.
     * @return If they collide.
     */
    public static boolean Collides(Vector2 pos1, Vector2 hitbox1, Vector2 pos2, Vector2 hitbox2) {
        if (pos1.getX() < pos2.getX() + hitbox2.getX() && pos1.getX() + hitbox1.getX() > pos2.getX()
                && pos1.getY() < pos2.getY() + hitbox2.getY() && pos1.getY() + hitbox1.getY() > pos2.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Draw stuff.
     */
    public static void Draw() {

        // Game over.
        if (IsGameOver) {
            DrawText("GAMEOVER", 210, 300, 100, Resources.Red);
            DrawText("Right click to play again!", 310, 400, 30, Resources.White);
            return;
        }

        // Money.
        DrawText("Money: $" + Resources.Money, 10, 10, 50, Resources.White);

        // Kills
        DrawText("Kills: " + Resources.Kills, 10, 60, 50, Resources.Red);

        // Bins.
        DrawTexture(Resources.Textures.get("Bins"), 0, 0, Resources.White);

        // Draw items.
        for (int i = 0; i < Items.size(); i++) {
            Items.get(i).Draw();
        }

    }

    /**
     * Update stuff.
     */
    public static void Update() {

        // Game over.
        if (Resources.Money < 0 || Resources.Kills > 50) {
            IsGameOver = true;
        }
        if (IsGameOver) {
            if (IsMouseButtonDown(MOUSE_RIGHT_BUTTON)) {
                InitGame();
            }
            return;
        }

        // Spawn item if needed.
        Timer++;
        if (Timer > Math.max(FUN_VALUE - Resources.GetLevel() * 5, 10)) {
            SpawnItem();
            Timer = 0;
        }

        // Update items.
        for (int i = 0; i < Items.size(); i++) {
            Items.get(i).Update();
        }

        // Remove dead items.
        for (int i = Items.size() - 1; i >= 0; i--) {
            if (Items.get(i).Dead) {
                float money = monetaryChangeKillCount(Items.get(i))[0];
                float kills = monetaryChangeKillCount(Items.get(i))[1];
                if (money > 0 && kills < 50) {
                    Resources.NumSuccesses++;
                }
                Resources.Money += money;
                Resources.Kills += kills;
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
        for (int i = 0; i < BIN_HITBOXES.size(); i++) {
            String key = (String) BIN_HITBOXES.keySet().toArray()[i];
            Vector2[] data = BIN_HITBOXES.get(key);
            if (Collides(item.Position, item.HitboxWidthHeight, data[0], data[1])) {
                item.Bin = key;
                return true;
            }
        }
        item.Bin = null;
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
            killCount += 5;
        } else if (item.Bin.equals("Compost")) {
            if (item.MaterialType == Material.Plastic7PLA) {
                answer += plasticRewards[2];
            } else if (item.MaterialType == Material.Food) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[2];
                System.out.println("You've killed a tree.");
                killCount++;
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Recycling")) {
            if (item.MaterialType == Material.Plastic1 || item.MaterialType == Material.Plastic2) {
                answer += plasticRewards[1];
            } else if (item.MaterialType == Material.Plastic4 || item.MaterialType == Material.Plastic5) {
                answer += plasticRewards[3];
                System.out.println("Although plastic 4 (LDPE) and plastic 5 (PPE) are technically recyclable, \n"
                        + "whether or not they can be recycled may depend on where you are located. \n"
                        + "Check with your local recycling collection services first.");
            } else if (item.MaterialType == Material.AluminumSteel || item.MaterialType == Material.PaperCardboard
                    || item.MaterialType == Material.Glass) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[3];
                System.out.println("You've killed a raccoon.");
                killCount++;
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Trash")) {
            if (item.MaterialType == Material.Plastic3) {
                answer += plasticRewards[0];
            } else if (item.MaterialType == Material.Plastic6) {
                answer += plasticRewards[3];
                System.out
                        .println("Although it is rare, it is sometimes actually possible to recycle plastic 6 (PS). \n"
                                + "Check with your local recycling collection services to make sure.");
            } else if (item.MaterialType == Material.Plastic7) {
                answer += plasticRewards[1];
            } else if (item.isPlastic()) {
                answer += plasticPunishments[0];
                System.out.println("You've killed a turtle.");
                killCount++;
            } else {
                answer += otherPunishments;
            }
        } else if (item.Bin.equals("Scrap")) {
            if (item.MaterialType == Material.MetalOther) {
                answer += otherRewards;
            } else if (item.isPlastic()) {
                answer += plasticPunishments[1];
                System.out.println("You've killed a bird.");
                killCount++;
            } else {
                answer += otherPunishments;
            }
        }
        int[] retVal = { answer, killCount };
        return retVal;
    }
}