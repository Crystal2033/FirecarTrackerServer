package com.aqulasoft.fireman.tracker.models;

// first -- boolean -- flag for services to start loading data points in repo. True -- need to load in database.
// second -- data points

public class BoolPair<S> {

    private boolean first; //first member of pair
    private S second; //second member of pair

    public BoolPair(boolean first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public boolean getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
