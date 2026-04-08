package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.menu.RadioAnalyserMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, LeadAge.MOD_ID);

    public static final RegistryObject<MenuType<RadioAnalyserMenu>> RADIO_ANALYSER_MENU =
            MENUS.register("radio_analyser_menu",
                    () -> IForgeMenuType.create(RadioAnalyserMenu::new));

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}