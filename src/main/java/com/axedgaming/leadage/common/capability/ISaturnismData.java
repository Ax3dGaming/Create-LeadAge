package com.axedgaming.leadage.common.capability;

public interface ISaturnismData {
    int get();
    void set(int value);
    void add(int amount);

    int getMinuteTicks();
    void setMinuteTicks(int ticks);
}