package net.Pandarix.stardewfishing.mixin;


import net.Pandarix.stardewfishing.StardewFishing;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public abstract class FishingRodMixin {

    //injecting this method into the Bonemeal Blockinteraction
    @Inject(method = "use", at = @At("HEAD"))
    protected void injectWriteMethod(Level level, Player player, InteractionHand interactionHand,CallbackInfoReturnable info) {
        StardewFishing.LOGGER.info("Mixin successfully used");
    }

}
