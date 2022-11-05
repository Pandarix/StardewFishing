package net.Pandarix.stardewfishing.item.init;

import net.Pandarix.stardewfishing.common.item.StardewFishingRodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VanillaItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> FISHING_ROD = ITEMS.register("fishing_rod",
            () -> new StardewFishingRodItem((new Item.Properties()).durability(64).tab(CreativeModeTab.TAB_TOOLS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}


