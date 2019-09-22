package com.marshmallow.vo

import spock.lang.Specification

class DirectionTest extends Specification  {

    Direction n, s, e, w
    def setup(){
        n = Direction.NORTH
        s = Direction.SOUTH
        e = Direction.EAST
        w = Direction.WEST
    }

    def 'Test North Values' () {
        when:
        n
        then:
        n.getX() == 0
        n.getY() == 1
    }

    def 'Test South Values' () {
        when:
        s
        then:
        s.getX() == 0
        s.getY() == -1
    }

    def 'Test West Values' () {
        when:
        w
        then:
        w.getX() == -1
        w.getY() == 0
    }

    def 'Test East Values' () {
        when:
        e
        then:
        e.getX() == 1
        e.getY() == 0
    }
}
