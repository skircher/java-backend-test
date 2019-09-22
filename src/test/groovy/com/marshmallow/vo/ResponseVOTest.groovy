package com.marshmallow.vo

import spock.lang.Specification

class ResponseVOTest extends Specification {
    def 'Test Getters and Setters'() {
        setup:
        ResponseVO response = new ResponseVO()
        int[] position = [1,2]
        when:
        response.setFinalPosition(position)
        response.incrementPatchesCleaned()
        response.incrementPatchesCleaned()
        response.incrementPatchesCleaned()

        then:
        response.getFinalPosition() == [1,2]
        response.getOilPatchesCleaned() == 3
    }
}
