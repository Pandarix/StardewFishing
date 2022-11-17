package net.Pandarix.stardewfishing.mixin;

import net.Pandarix.stardewfishing.StardewFishing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Inject(method = "FishingHook", at = @At(value = ""))
    private void injectMethod(Player pPlayer, Level pLevel, int pLuck, int pLureSpeed, CallbackInfo info) {
        StardewFishing.LOGGER.info("works!");
    }
}
