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
    public static HashMap<String, Texture2D> Textures;

    /**
     * Money the player has.
     */
    public static long Money;

    /**
     * Draw a texture.
     * @param id ID.
     * @param pos Position.
     */
    public static void DrawTexture(String id, Vector2 pos) {
        DrawTextureV(Textures.get(id), pos, new Color(255, 255, 255));
    }

}
