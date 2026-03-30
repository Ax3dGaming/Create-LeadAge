package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import net.minecraft.server.level.ServerLevel;

import java.util.*;

public class RadioWorldRegistry {

    private static final Map<ServerLevel, Set<RadioBlockEntity>> RADIOS = new HashMap<>();
    private static final Map<ServerLevel, Set<RadioAnalyserBlockEntity>> ANALYSERS = new HashMap<>();

    public static void addRadio(ServerLevel level, RadioBlockEntity be) {
        RADIOS.computeIfAbsent(level, l -> new HashSet<>()).add(be);
    }

    public static void removeRadio(ServerLevel level, RadioBlockEntity be) {
        Set<RadioBlockEntity> set = RADIOS.get(level);
        if (set != null) set.remove(be);
    }

    public static Set<RadioBlockEntity> getRadios(ServerLevel level) {
        return RADIOS.getOrDefault(level, Collections.emptySet());
    }

    public static void addAnalyser(ServerLevel level, RadioAnalyserBlockEntity be) {
        ANALYSERS.computeIfAbsent(level, l -> new HashSet<>()).add(be);
    }

    public static void removeAnalyser(ServerLevel level, RadioAnalyserBlockEntity be) {
        Set<RadioAnalyserBlockEntity> set = ANALYSERS.get(level);
        if (set != null) set.remove(be);
    }

    public static Set<RadioAnalyserBlockEntity> getAnalysers(ServerLevel level) {
        return ANALYSERS.getOrDefault(level, Collections.emptySet());
    }
}