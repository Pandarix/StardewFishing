package net.Pandarix.stardewfishing;

import com.mojang.logging.LogUtils;
import net.Pandarix.stardewfishing.item.init.VanillaItemInit;
import net.Pandarix.stardewfishing.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(StardewFishing.MOD_ID)
public class StardewFishing
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "stardewfishing";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public StardewFishing()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        VanillaItemInit.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //  Creates Config files
        Config.register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM STARDEW FSIHING! [Common]");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO FROM STARDEW FISHING! [Server]");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM STARDEW FISHING [CLIENT],");
            LOGGER.info("MINECRAFT NAME >> {}!", Minecraft.getInstance().getUser().getName());
        }
    }
}
