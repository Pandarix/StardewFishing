package net.Pandarix.stardewfishing.common.item;

import net.Pandarix.stardewfishing.StardewFishing;
import net.Pandarix.stardewfishing.client.RodCastOverlay;
import net.Pandarix.stardewfishing.util.Config;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class StardewFishingRodItem extends FishingRodItem {
    public StardewFishingRodItem(Properties pProperties) {
        super(pProperties);
    }

    public static final int USE_DURATION = 120;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        //MODDED
        if (Config.FEATURES_ENABLED.get()) {
            StardewFishing.LOGGER.info("use");
            if (pLevel.isClientSide) {
                RodCastOverlay.enableOverlay();
            }
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }

        //VANILLA
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.fishing != null) {
            if (!pLevel.isClientSide) {
                int i = pPlayer.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(i, pPlayer, (p_41288_) -> {
                    p_41288_.broadcastBreakEvent(pHand);
                });
            }

            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                int k = EnchantmentHelper.getFishingSpeedBonus(itemstack);
                int j = EnchantmentHelper.getFishingLuckBonus(itemstack);
                pLevel.addFreshEntity(new FishingHook(pPlayer, pLevel, j, k));
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        StardewFishing.LOGGER.info("finished");
        this.stopUsing(pLivingEntity);
        return pStack;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        StardewFishing.LOGGER.info("cancelled");
        this.stopUsing(pLivingEntity);
    }

    private void stopUsing(LivingEntity pUser) {
        pUser.playSound(SoundEvents.FISHING_BOBBER_THROW, 1.0F, 1.0F);
        if (pUser.level.isClientSide) {
            RodCastOverlay.hideOverlay();
        }
        pUser.swing(pUser.getUsedItemHand());
        ((Player) pUser).getCooldowns().addCooldown(this, 100);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (count%4==0) {
            player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, (float) (Math.random()*0.25+0.75));
        }
        super.onUsingTick(stack, player, count);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

}
