package com.grey20;

import raylib.Color;

import raylib.*;
import static raylib.Color.*;
import static raylib.Raylib.*;
import static raylib.TextureFilterMode.*;
import java.util.HashMap;

/**
 * Resources.
 */
public class Resources {

    /**
     * Textures.
     */
    public static HashMap<String, Texture2D> Textures = new HashMap<>();

    /**
     * White color.
     */
    public static Color White = new Color(255, 255, 255);
    public static Color Red = new Color(255, 0, 0);

    /**
     * Money and kills the player has.
     */
    public static long Money = 50;
    public static long Kills = 0;

    // /**
    // * Number of times the player has entered something successfully.
    // */
    public static long NumSuccesses = 0;

    /**
     * Get the current level the player is on.
     * 
     * @return The current level.
     */
    public static long GetLevel() {
        return NumSuccesses / 5;
    }

    /**
     * Draw a texture.
     * 
     * @param id  ID.
     * @param pos Position.
     */
    public static void DrawTexture(String id, Vector2 pos) {
        DrawTextureV(Textures.get(id), pos, new Color(255, 255, 255));
    }

}
