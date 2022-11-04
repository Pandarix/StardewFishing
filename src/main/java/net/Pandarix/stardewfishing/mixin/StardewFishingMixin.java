package net.Pandarix.stardewfishing.mixin;

import net.Pandarix.stardewfishing.StardewFishing;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class StardewFishingMixin {

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        StardewFishing.LOGGER.info("Initializing " + StardewFishing.MOD_ID + " Mixins");
    }

}
