package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;

import static com.simibubi.create.foundation.block.connected.AllCTTypes.*;


public class ModSpriteShifts {

    public static final CTSpriteShiftEntry LEAD_CASING = ct(OMNIDIRECTIONAL, "lead_casing");

    private static CTSpriteShiftEntry ct(AllCTTypes type, String name) {
        return CTSpriteShifter.getCT(type,
                LeadAge.asResource("block/" + name),
                LeadAge.asResource("block/" + name + "_connected"));
    }

    public static void register() { LeadAge.LOGGER.debug("Registering ModSpriteShifts!"); }
}
