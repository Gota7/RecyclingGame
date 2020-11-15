package com.grey20;

import raylib.Color;

import raylib.*;
import static raylib.Color.*;
import static raylib.Raylib.*;
import static raylib.TextureFilterMode.*;

/**
 * Item.
 */
public class Item {

    /**
     * Material type.
     */
    public enum Material {
        Food,
        PaperCardboard,
        AluminumSteel,
        MetalOther,
        Glass,
        Plastic1,
        Plastic2,
        Plastic3,
        Plastic4,
        Plastic5,
        Plastic6,
        Plastic7,
        Plastic7PLA
    }

    /**
     * State of the item.
     */
    public enum State {
        Falling,
        Grabbed,
        Destroy
    }

    /**
     * Name of the texture to display.
     */
    public String TextureName;

    /**
     * Type of material.
     */
    public Material MaterialType;

    /**
     * Position of the item on screen.
     */
    public Vector2 Position;

    /**
     * Represents the width and height of the hitbox.
     */
    public Vector2 HitboxWidthHeight;

    /**
     * If you can see the item or not.
     */
    public boolean Visible;

    /**
     * Current state of the item.
     */
    public State CurrentState;

    /**
     * If object is dead.
     */
    public boolean Dead;

    /**
     * Draw the item.
     */
    public void Draw() {
        if (Visible) {
            Resources.DrawTexture(TextureName, Position);
        }
    }

    /**
     * Update the item.
     */
    public void Update() {
        switch (CurrentState) {
            case Falling:
                DoFallState();
                break;
            case Grabbed:
                DoGrabbingState();
                break;
            case Destroy:
                DoDestroyState();
                break;
        }
    }

    private void DoFallState() {

    }

    /**
     * Do grabbing state.
     */
    private void DoGrabbingState() {
        if (IsMouseButtonReleased(MOUSE_LEFT_BUTTON)) {
            if (Main.DoesItemIntersectWithBin(this)) {
                CurrentState = State.Destroy;
            } else {
                CurrentState = State.Falling;
            }
        }
    }

    /**
     * Destroy the item.
     */
    private void DoDestroyState() {
        Visible = false;
        Position.setX(-10000);
        Position.setY(-10000);
        Dead = true;
    }

}
