package com.marshmallow.vo

import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import java.awt.Point

class RequestVOTest extends Specification {
    RequestVO r
    private Validator validator

    def setup() {
        r = new RequestVO()
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def 'RequestVO Getters and Setters'() {
        setup:
        int[] start = [1, 1]
        int[] area = [5, 5]
        String nav = "NNNSSSWWEWS"
        int[][] patches = [[1, 2], [3, 3], [2, 2]]

        when:
        r.setAreaSize(area)
        r.setNavigationInstructions(nav)
        r.setOilPatches(patches)
        r.setStartingPosition(start)
        then:
        r.getAreaSize() == [5, 5]
        r.getNavigationInstructions() == "NNNSSSWWEWS"
        r.getOilPatches() == [[1, 2], [3, 3], [2, 2]]
        r.getStartingPosition() == [1, 1]
    }

    def 'RequestVO Point Getters and Setters'() {
        setup:
        int[] start = [1, 1]
        int[] area = [5, 5]
        int[][] patches = [[1, 2], [3, 3], [2, 2]]
        List<Point> expectedPatches = new ArrayList<>();
        expectedPatches.add(new Point(1,2))
        expectedPatches.add(new Point(3,3))
        expectedPatches.add(new Point(2,2))

        when:
        r.setAreaSize(area)
        r.setOilPatches(patches)
        r.setStartingPosition(start)

        then:
        r.getAreaSizePoint() == new Point(5, 5)
        r.getStartingPositionPoint() == new Point(1,1)
        r.getOilPatchList() == expectedPatches
    }

    def 'Invalid Area Structure'() {

        when:
        int[] badArea = [5,5,5]
        r.setAreaSize(badArea)
        Set<ConstraintViolation<RequestVO>> violations = validator.validate(r)

        then:
        !violations.isEmpty()
    }

    def 'Invalid Area Type'() {

        when:
        int[] badArea = ["A", 5]
        r.setAreaSize(badArea)
        Set<ConstraintViolation<RequestVO>> violations = validator.validate(r)

        then:
        !violations.isEmpty()
    }


    def 'Invalid Starting Position Structure'() {

        when:
        int[] badPosition = [5,5,5]
        r.setStartingPosition(badPosition)
        Set<ConstraintViolation<RequestVO>> violations = validator.validate(r)

        then:
        !violations.isEmpty()
    }

    def 'Invalid Starting Position Type'() {

        when:
        int[] badPosition = [5,5]
        r.setStartingPosition(badPosition)
        Set<ConstraintViolation<RequestVO>> violations = validator.validate(r)

        then:
        !violations.isEmpty()
    }

    def 'Bad Navigation Instructions'() {
        when:
        r.setNavigationInstructions("")
        Set<ConstraintViolation<RequestVO>> violations = validator.validate(r)

        then:
        !violations.isEmpty()
    }
}