package org.demo.L01;

public enum Condition {
    INSTANCE;

    boolean stop = false;

    boolean shouldStop() {
        return stop;
    }

    void stop() {
        stop = true;
    }


}
