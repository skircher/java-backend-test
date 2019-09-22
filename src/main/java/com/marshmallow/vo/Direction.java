package com.marshmallow.vo;

/**
 * This enum helps keep the service clean and readable.
 * Each cardinal direction corresponds with a change in Cartesian coordinate
 * @author skircher
 */

public enum Direction {
    NORTH(0,1),
    SOUTH(0, -1),
    EAST(1, 0),
    WEST(-1, 0);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
}
