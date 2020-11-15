package com.grey20;

import raylib.*;

import static raylib.Raylib.*;

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
     * Null position.
     */
    public static final int POS_NULL = -100000;

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
     * Velocity.
     */
    public Vector2 Velocity;

    /**
     * Acceleration.
     */
    public Vector2 Acceleration;

    /**
     * If you can see the item or not.
     */
    public boolean Visible;

    /**
     * Current state of the item.
     */
    public State CurrentState;

    /**
     * Bin it is in.
     */
    public String Bin;

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

    /**
     * Do the falling state.
     */
    private void DoFallState() {

        //Get vars.
        //Velocity = new Vector2();

        //Check for grabbing.
        if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON)) {
            if (Main.UpdateBinIntersection(this)) {
                CurrentState = State.Grabbed;
            }
        }

        //Check for out of bounds.
        if (Position.getY() > Main.HEIGHT) {
            Bin = null;
            CurrentState = State.Destroy;
        }

    }

    /**
     * Do grabbing state.
     */
    private void DoGrabbingState() {
        Position = new Vector2(GetMouseX() - HitboxWidthHeight.getX() / 2, GetMouseY() - HitboxWidthHeight.getY() / 2);
        if (IsMouseButtonReleased(MOUSE_LEFT_BUTTON)) {
            if (Main.UpdateBinIntersection(this)) {
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
        Position.setX(POS_NULL);
        Position.setY(POS_NULL);
        Dead = true;
    }

}
