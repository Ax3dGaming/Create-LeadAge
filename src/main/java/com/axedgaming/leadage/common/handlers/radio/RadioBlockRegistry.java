package com.axedgaming.leadage.common.handlers.radio;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class RadioBlockRegistry {
    private static final Set<RadioBlockEntity> RADIOS = ConcurrentHashMap.newKeySet();
    private static final Set<RadioAnalyserBlockEntity> ANALYSERS = ConcurrentHashMap.newKeySet();

    private RadioBlockRegistry() {}

    public static void registerRadio(RadioBlockEntity be) {
        RADIOS.add(be);
    }

    public static void unregisterRadio(RadioBlockEntity be) {
        RADIOS.remove(be);
    }

    public static void registerAnalyser(RadioAnalyserBlockEntity be) {
        ANALYSERS.add(be);
    }

    public static void unregisterAnalyser(RadioAnalyserBlockEntity be) {
        ANALYSERS.remove(be);
    }

    public static Set<RadioBlockEntity> getRadios() {
        return RADIOS;
    }

    public static Set<RadioAnalyserBlockEntity> getAnalysers() {
        return ANALYSERS;
    }
}

