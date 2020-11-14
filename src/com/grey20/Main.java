package com.grey20;

import raylib.*;

import static raylib.Color.*;
import static raylib.Raylib.*;
import static raylib.TextureFilterMode.*;

public class Main {
    /*** runMain
     * Open and close a window just to be sure that our library is sane.
     */
    public static void main(String[] args) {
        InitWindow(1024, 576, "Sample Window");
        SetTargetFPS(60);
        Vector2 pos = new Vector2(540, 200);
        Texture2D tex = LoadTexture("pig.png");
        while (!WindowShouldClose()) {
            BeginDrawing();
                ClearBackground(new Color(255, 255, 255, 255));
                DrawText("Hello World!", 10, 10, 20, new Color(0, 0, 0, 255));
                DrawCircleV(pos, 15.0f, new Color(200, 0, 200, 255));
                DrawTexture(tex, 300, 40, new Color(255, 255, 255));
            EndDrawing();
            pos.setX(pos.getX() + (float)Math.random() * 10f - 3);
            pos.setY(pos.getY() + (float)Math.random() * 10f - 3);
        }
        CloseWindow();
    }
}