package com.marshmallow.service;

import com.marshmallow.exception.BadDirectionException;
import com.marshmallow.vo.Direction;
import com.marshmallow.vo.RequestVO;
import com.marshmallow.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.awt.Point;
import java.util.List;

import static com.marshmallow.vo.Direction.*;

/**
 * Service class which takes in a valid RequestVO and returns a ResponseVO.
 * Handles the macro logic of the application, including navigation and cleaning.
 * @author skircher
 */

@Component
public class CleanerService {

    public ResponseVO doService(RequestVO request) {
        ResponseVO response = new ResponseVO();
        // Work with points for easy access to x and y values
        Point cleanerPosition = request.getStartingPositionPoint();
        List<Point> patchList = request.getOilPatchList();
        // Split movements every character after handling whitespace
        String[] movements = request.getNavigationInstructions()
                                 .toUpperCase()
                                 .replaceAll("\\s+", "")
                                 .split("");

        for (String movement : movements) {
            moveCleaner(movement, cleanerPosition);
            // Check if we're in a valid location.
            if (isOutOfBounds(cleanerPosition, request.getAreaSizePoint())) {
                throw new BadDirectionException("The cleaner has moved out of bounds!");
            }

            // Null check with short circuit and since we consider no oil spills a valid use case
            if (patchList != null && patchList.contains(cleanerPosition)){
                response.incrementPatchesCleaned();
                // Take care to remove the patch from the list in case we re-visit this location.
                patchList.remove(cleanerPosition);
            }
        }
        // After all the movement, we settle here
        int[] finalPosition = {cleanerPosition.x, cleanerPosition.y};
        response.setFinalPosition(finalPosition);
        return response;
    }

    private void moveCleaner(String direction, Point current) {
        // Make use of the Enum to convert cardinal direction into an x,y movement
        Direction d;
        switch (direction) {
            case "N":
                d = NORTH;
                break;
            case "S":
                d = SOUTH;
                break;
            case "E":
                d = EAST;
                break;
            case "W":
                d = WEST;
                break;
            default :
                // another approach would be @Pattern validation in the VO, but for now handle the blanket case here
                throw new BadDirectionException("Please ensure navigationInstructions" +
                                                    " only contains valid cardinal directions!");
        }
        // We still need to pass two ints to translate the point.
        current.translate(d.getX(), d.getY());
    }

    // moving this below since it's slightly unwieldy
    private boolean isOutOfBounds(Point cleaner, Point areaSize) {
        return (cleaner.x > areaSize.x || cleaner.y > areaSize.y || cleaner.x < 0 || cleaner.y < 0);
    }
}
