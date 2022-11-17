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
    private static final int USE_DURATION = 120;

    public StardewFishingRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        //MODDED
        if (Config.FEATURES_ENABLED.get()) {

            //retrieve-----------------------------------------------------------------------------------------------------------------------------------
            if (pPlayer.fishing != null) {
                int i = pPlayer.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(i, pPlayer, (p_41288_) -> {
                    p_41288_.broadcastBreakEvent(pHand);
                });

                if (pLevel.isClientSide) {
                    RodCastOverlay.hideOverlay();
                    RodCastOverlay.resetCastShade();
                    RodCastOverlay.resetCastValue();
                }

                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            }

            //cast-----------------------------------------------------------------------------------------------------------------------------------
            if (pLevel.isClientSide) {
                RodCastOverlay.enableOverlay();
                RodCastOverlay.resetCastShade();
            }

            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        }

        //VANILLA
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
     * Calculated the Luck and Speed bonus of the fishing rod cast using the enchantment values and the castStrength
     *
     * @param enchantmentBonus Enchantment level of Lure or Luck of the Sea
     * @param player Player using the Fishing Rod
     * @return int calculated value of enchantment and castStrength
     */
    private int calculateBonus(int enchantmentBonus, Player player){
        StardewFishing.LOGGER.info("Enchantment-Bonus would be: " + enchantmentBonus);
        StardewFishing.LOGGER.info("Fishingcast Bonus is: " + stingyRound(getFishingCastStrength(player)*2));
        StardewFishing.LOGGER.info("Calculated Bonus is : " + ((int) ((enchantmentBonus*2/3)) + stingyRound(getFishingCastStrength(player)*2)));

        return ((int) ((enchantmentBonus*2/3)) + stingyRound(getFishingCastStrength(player)*2));
    }

    /**
     *  Rounds input double up if d > x.85.
     *  Else it rounds down.
     *
     * @param d input double to be rounded
     * @return int - rounded value
     */
    private int stingyRound(double d){
        return d % 1 > 0.8 ? Math.round((float) d) : (int) d;
    }

    /**
     * Uses an inverted and normalized sine function to represent the cast strength.
     *
     * @param player Player using the Fishing Rod
     * @return double - value between 0 and 1
     */
    public static double getFishingCastStrength(Player player){
        return (Math.abs(Math.sin((player.getUseItemRemainingTicks() / (float) (StardewFishingRodItem.USE_DURATION / Config.SPEED_MULTIPLIER.get())) * Math.PI + (Math.PI / 2))) * (-1) + 1);
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
        Player player = (Player) pUser;
        Level level = pUser.level;

        if(player.level.isClientSide){
            if(stingyRound(getFishingCastStrength(player)*2) == 2){
                player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.5F, (float) Math.random()+0.5F);
                RodCastOverlay.goodCastShade();
            } else if (getFishingCastStrength(player)*2 < 1) {
                player.playSound(SoundEvents.MANGROVE_ROOTS_PLACE, 0.75F, (float) Math.random()*0.5F);
                RodCastOverlay.badCastShade();
            }
            RodCastOverlay.storeLastCastValue(player);
        }

        pUser.playSound(SoundEvents.FISHING_BOBBER_THROW, 1.0F, 1.0F);
        if (!level.isClientSide) {
            int j = EnchantmentHelper.getFishingLuckBonus(pUser.getUseItem());
            int k = EnchantmentHelper.getFishingSpeedBonus(pUser.getUseItem());
            level.addFreshEntity(new FishingHook(player, level, calculateBonus(j, player), calculateBonus(k, player)));
        }
        pUser.swing(pUser.getUsedItemHand());
        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (count % 4 == 0) {
            double k = getFishingCastStrength((Player) player) * 0.5 + 0.5;
            player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, (float) k);
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
