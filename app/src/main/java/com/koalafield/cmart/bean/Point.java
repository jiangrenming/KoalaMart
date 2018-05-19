package com.koalafield.cmart.bean;

/**
 * Created by jiangrenming on 2018/5/19.
 */

public class Point {

    public  int x;
    public  int y;
    public Point() {}
    public Point(int x, int y) {
        this.x = x; this.y = y;
    }
    public Point(Point src) {
        this.x = src.x;
        this.y = src.y;
    }
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
