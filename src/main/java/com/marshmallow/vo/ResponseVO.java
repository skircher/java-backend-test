package com.marshmallow.vo;

import org.springframework.stereotype.Component;

/**
 * Simple response including where the boat winds up and a total number of oil patches cleaned along the journey.
 * @author skircher
 */

@Component
public class ResponseVO {
    private int[] finalPosition;
    private int oilPatchesCleaned;

    public int[] getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(int[] finalPosition) {
        this.finalPosition = finalPosition;
    }

    public int getOilPatchesCleaned() {
        return oilPatchesCleaned;
    }

    public void incrementPatchesCleaned(){
        oilPatchesCleaned++;
    }
}
