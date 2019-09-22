package com.marshmallow.service

import com.marshmallow.exception.BadDirectionException
import com.marshmallow.vo.RequestVO
import com.marshmallow.vo.ResponseVO
import spock.lang.Specification

import java.awt.Point

class CleanerServiceTest extends Specification {
    CleanerService cleanerService = new CleanerService()

    def 'Move Cleaner 1' () {
        setup:
        Point position = new Point(0,0)
        when:
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("S", position)
        cleanerService.moveCleaner("E", position)
        cleanerService.moveCleaner("E", position)
        then:
        position == new Point(2,3)
    }

    def 'Move Cleaner 2' () {
        setup:
        Point position = new Point(0,0)
        when:
        cleanerService.moveCleaner("S", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("N", position)
        cleanerService.moveCleaner("S", position)
        cleanerService.moveCleaner("W", position)
        cleanerService.moveCleaner("E", position)
        then:
        position == new Point(0,1)
    }


    def 'Is Out of Bounds Conditionals' () {
        when:
        // x too big
        Point p1 = new Point(4,0)
        // y too big
        Point p2 = new Point(0,4)
        // x is negative
        Point p3 = new Point(-1,0)
        // y is negative
        Point p4 = new Point(0,-1)
        // valid
        Point p5 = new Point(1,1)
        // also valid
        Point p6 = new Point(0,0)
        Point area = new Point(3,3)

        then:
        // OOB
        cleanerService.isOutOfBounds(p1, area)
        cleanerService.isOutOfBounds(p2, area)
        cleanerService.isOutOfBounds(p3, area)
        cleanerService.isOutOfBounds(p4, area)
        // Not OOB
        !cleanerService.isOutOfBounds(p5, area)
        !cleanerService.isOutOfBounds(p6, area)
    }

    // Most of doService is covered by parent and child tests, we'll test the happy path and reach the exceptions
    def 'Test doService Happy Path' () {
        setup:
        RequestVO r = new RequestVO()
        int[] area = [5,5]
        int[] start = [0, 2]
        int[][] patches =  [[1, 0], [2, 2], [2, 3]]
        String nav = "NNESEESWNWW"
        r.setAreaSize(area)
        r.setStartingPosition(start)
        r.setOilPatches(patches)
        r.setNavigationInstructions(nav)
        when:
        ResponseVO response = cleanerService.doService(r)
        then:
        response.finalPosition == [0,3]
        response.oilPatchesCleaned == 2
    }

    def 'Test doService Bad Navigation' () {
        setup:
        RequestVO r = new RequestVO()
        int[] area = [5,5]
        int[] start = [0, 2]
        int[][] patches =  [[1, 0], [2, 2], [2, 3]]
        String nav = "nnnnnnnnnnnnnn"
        r.setAreaSize(area)
        r.setStartingPosition(start)
        r.setOilPatches(patches)
        r.setNavigationInstructions(nav)
        when:
        ResponseVO response = cleanerService.doService(r)
        then:
        thrown(BadDirectionException)
    }

    def 'Test doService Bad Navigation Direction' () {
        setup:
        RequestVO r = new RequestVO()
        int[] area = [5,5]
        int[] start = [0, 2]
        int[][] patches =  [[1, 0], [2, 2], [2, 3]]
        String nav = "George"
        r.setAreaSize(area)
        r.setStartingPosition(start)
        r.setOilPatches(patches)
        r.setNavigationInstructions(nav)
        when:
        ResponseVO response = cleanerService.doService(r)
        then:
        thrown(BadDirectionException)
    }
}
