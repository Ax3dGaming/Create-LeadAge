package com.axedgaming.leadage.common.capability;

public class SaturnismData implements ISaturnismData {
    private int value = 0;
    private int minuteTicks = 0;

    @Override
    public int get() {
        return value;
    }

    @Override
    public void set(int value) {
        this.value = Math.max(0, value);
    }

    @Override
    public void add(int amount) {
        set(this.value + amount);
    }

    @Override
    public int getMinuteTicks() {
        return minuteTicks;
    }

    @Override
    public void setMinuteTicks(int ticks) {
        this.minuteTicks = ticks;
    }
}