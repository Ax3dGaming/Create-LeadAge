package com.axedgaming.leadage.common.capability;

public class MaskingData implements IMaskingData {
    private boolean masked = false;

    @Override
    public boolean isMasked() {
        return masked;
    }

    @Override
    public void setMasked(boolean value) {
        this.masked = value;
    }
}
