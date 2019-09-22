package com.marshmallow.vo;

import com.marshmallow.exception.MalformedRequestException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The RequestVO places a number of data constraints on the JSON request so we can safely work with data
 * We also use this class to safely cast int[] from the inputs to Points for ease of use in the service
 * @author skircher
 */

@Component
public class RequestVO {

    // Validate that areaSize exists, and that it matches an int array with exactly 2 elements
    @NotEmpty(message = "Please provide an area size in the following format [x,y]")
    @Size(min = 2, max = 2, message = "Please provide a valid Cartesian coordinate!")
    private int[] areaSize;

    // Do the same for the starting position
    @NotEmpty(message = "Please provide an initial position in the following format [x,y]")
    @Size(min = 2, max = 2, message = "Please provide a valid Cartesian coordinate!")
    private int[] startingPosition;

    // Built in validations are tricky for 2D arrays, we'll handle some scenarios throughout
    // Assume that there might be an area with no oil
    private int[][] oilPatches;

    // Assume that though there might not be oil, there must be a journey for the cleaner
    @NotEmpty(message = "You must provide navigationInstructions, or else the cleaner won't know where to go!")
    private String navigationInstructions;

    // We take in request params as ints for validation, but actually want to cast to / work with Points
    private Point areaSizePoint;
    private Point startingPositionPoint;
    private List<Point> oilPatchList;

    public int[] getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(int[] areaSize) {
        this.areaSize = areaSize;
        // all these are vanilla getters / setters + call the corresponding point setting method.
        setAreaSizePoint(areaSize);
    }

    public int[] getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(int[] startingPosition) {
        this.startingPosition = startingPosition;
        setStartingPositionPoint(startingPosition);
    }

    public int[][] getOilPatches() {
        return oilPatches;
    }

    public void setOilPatches(int[][] oilPatches) {
        this.oilPatches = oilPatches;
        setOilPatchList(oilPatches);
    }

    public Point getAreaSizePoint() {
        return areaSizePoint;
    }

    public void setAreaSizePoint(int[] areaSize) {
        this.areaSizePoint = new Point();
        // This is safe since we validated areaSize.length == 2
        this.areaSizePoint.x = areaSize[0];
        this.areaSizePoint.y = areaSize[1];
    }

    public Point getStartingPositionPoint() {
        return startingPositionPoint;
    }

    public void setStartingPositionPoint(int[] startingPosition) {
        this.startingPositionPoint = new Point();
        this.startingPositionPoint.x = startingPosition[0];
        this.startingPositionPoint.y = startingPosition[1];
    }

    public List<Point> getOilPatchList() {
        return oilPatchList;
    }

    public void setOilPatchList(int[][] oilPatches) {
        this.oilPatchList = new ArrayList<>();
        for (int[] patch : oilPatches) {
            if (patch.length == 2) {
                Point p = new Point();
                p.x = patch[0];
                p.y = patch[1];
                this.oilPatchList.add(p);
            }
            // This could be made more elegant, but for now, perform the same length == 2check for each array in the
            // 2D oil patch array, and throw an exception if we're working with invalid coordinates.
            else throw new MalformedRequestException("One or more of the Oil Path Coordinates Provided " +
                                                         "is not a valid Cartesian coordinate!");
        }
    }

    public String getNavigationInstructions() {
        return navigationInstructions;
    }

    public void setNavigationInstructions(String navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
