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
        Food, PaperCardboard, AluminumSteel, MetalOther, Glass, Plastic1, Plastic2, Plastic3, Plastic4, Plastic5,
        Plastic6, Plastic7, Plastic7PLA
    }

    /**
     * State of the item.
     */
    public enum State {
        Falling, Grabbed, Destroy
    }

    /**
     * Null position.
     */
    public static final int POS_NULL = -100000;

    /**
     * Base acceleration of items.
     */
    public static final float BASE_ACCELERATION = 0.05f;

    /**
     * Acceleration increase per level.
     */
    public static final float LEVEL_INCREMENT_ACCLERATION = 0.001f;

    /**
     * Initial speed of the item.
     */
    public static final float INITIAL_SPEED = 1/3f;

    /**
     * Speed increment for level.
     */
    public static final float LEVEL_INCREMENT_SPEED = .1f;

    /**
     * It's fun!
     */
    public static final float FUN_VALUE = 150;

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
     * Frame timer.
     */
    private float Timer = 0;

    /**
     * If an item is grabbed.
     */
    public static boolean ItemGrabbed = false;

    /**
     * Spawn an item.
     * @param textureName Texture name.
     * @param materialType Material type.
     */
    public Item(String textureName, Material materialType) {

        //Init stuff.
        Visible = true;
        TextureName = textureName;
        MaterialType = materialType;
        CurrentState = State.Falling;

        //General setup maths.
        HitboxWidthHeight = new Vector2(Resources.Textures.get(textureName).getWidth(), Resources.Textures.get(textureName).getHeight());
        Position = new Vector2((float)Math.random() * (Main.WIDTH - HitboxWidthHeight.getX()) * 3, 10 - HitboxWidthHeight.getY());
        InitVelocity();
        Acceleration = new Vector2(0, BASE_ACCELERATION + Resources.GetLevel() * LEVEL_INCREMENT_ACCLERATION);

    }

    /**
     * Initialize velocity.
     */
    private void InitVelocity() {
        Velocity = new Vector2((INITIAL_SPEED + Resources.GetLevel() * LEVEL_INCREMENT_SPEED) * (Math.random() >= .5f ? -1 : 1), INITIAL_SPEED + Resources.GetLevel() * LEVEL_INCREMENT_SPEED);
    }

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
        Timer++;
    }

    /**
     * Do the falling state.
     */
    private void DoFallState() {

        //Get vars.
        Velocity = new Vector2(Acceleration.getX() + Velocity.getX(), Acceleration.getY() + Velocity.getY());
        Position = new Vector2(Position.getX() + Velocity.getX(), Position.getY() + Velocity.getY());
        if (Position.getX() < 0) {
            Velocity.setX(Math.abs(Velocity.getX()));
        } else if (Position.getX() >= Main.WIDTH - HitboxWidthHeight.getX()) {
            Velocity.setX(-Math.abs(Velocity.getX()));
        } else if (Timer > Math.max(FUN_VALUE - Resources.GetLevel() * 10, 120)) {
            if (Math.random() >= .5f) Velocity.setX(-Velocity.getX());
            Timer = 0;
        }

        //Check for grabbing.
        if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON) && !ItemGrabbed) {
            if (Main.Collides(Position, HitboxWidthHeight, GetMousePosition(), new Vector2(1, 1))) {
                CurrentState = State.Grabbed;
                ItemGrabbed = true;
                InitVelocity();
            }
        }

        // Check for out of bounds.
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
            ItemGrabbed = false;
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

    public boolean isPlastic() {
        Material mat = MaterialType;
        if (mat == Material.Plastic1 || mat == Material.Plastic2 || mat == Material.Plastic3 || mat == Material.Plastic4
                || mat == Material.Plastic5 || mat == Material.Plastic6 || mat == Material.Plastic7
                || mat == Material.Plastic7PLA) {
            return true;
        } else {
            return false;
        }
    }

}
