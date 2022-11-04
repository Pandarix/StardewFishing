package net.Pandarix.stardewfishing.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static final String CATEGORY_GENERAL_SETTINGS = "general settings";
    public static ForgeConfigSpec.BooleanValue FEATURES_ENABLED;

    public static void register() {
        registerServerConfigs();
    }

    private static void registerServerConfigs() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General Mod-Settings for StardewFishing").push("CATEGORY_GENERAL_SETTINGS");

        FEATURES_ENABLED = SERVER_BUILDER.comment(
                "Enable or disable the mods changes to the fishing mechanics [true/false]").define("enableFeatures", true);

        SERVER_BUILDER.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
