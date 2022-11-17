package net.Pandarix.stardewfishing.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Pandarix.stardewfishing.StardewFishing;
import net.Pandarix.stardewfishing.common.item.StardewFishingRodItem;
import net.Pandarix.stardewfishing.item.init.VanillaItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RodCastOverlay {

    private static final ResourceLocation NO = new ResourceLocation(StardewFishing.MOD_ID, "textures/no.png");
    private static final ResourceLocation YES = new ResourceLocation(StardewFishing.MOD_ID, "textures/yes.png");

    private static boolean showOverlay = false;
    private static int overlayShade = 0; //if -1 -> red, 0 -> normal, 1 -> yellow

    private static double lastCastValue = 0;
    private static final int MAX_WIDTH = 182;

    public static final IGuiOverlay HUD_ROD_CAST = ((gui, poseStack, partialTick, width, height) -> {
        if (showOverlay && Objects.requireNonNull(gui.getMinecraft().player).getItemInHand(gui.getMinecraft().player.getUsedItemHand()).is(VanillaItemInit.FISHING_ROD.get())) {
            Minecraft minecraft = gui.getMinecraft(); //Minecraft Client instance

            minecraft.getProfiler().push("castBar"); //divider name
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION); //Texture set

            if (overlayShade == 0) {
                RenderSystem.setShaderColor(1.0F, 1.5F, 1.0F, 1.0F); //Shade light green
            } else if (overlayShade == 1) {
                RenderSystem.setShaderColor(2.0F, 2.0F, 0.0F, 1.0F); //Shade light yellow
            } else {
                RenderSystem.setShaderColor(1.0F, 0.25F, 0.25F, 1.0F); //Shade dark red
            }

            //Display variables
            int posX = width / 2 - 91;
            int posY = (int) (height - 32 * 1.5 + 3);

            //Displays gray-ish background
            gui.blit(poseStack, posX, posY, 0, 84, MAX_WIDTH, 5);

            assert minecraft.player != null;

            double k = 0;
            if (lastCastValue != 0) {
                k = lastCastValue;
            } else {
                k = (StardewFishingRodItem.getFishingCastStrength(minecraft.player)) * MAX_WIDTH;
            }

            //Display blue bar for use progress
            if (k >= 1) {
                gui.blit(poseStack, posX, posY, 0, 89, (int) Math.round(k), 5);
            }
        } else {
            hideOverlay();
            resetCastValue();
            resetCastShade();
        }
    });

    public static void enableOverlay() {
        showOverlay = true;
    }

    public static void hideOverlay() {
        showOverlay = false;
    }

    public static void badCastShade() {
        overlayShade = -1;
    }

    public static void goodCastShade() {
        overlayShade = 1;
    }

    public static void resetCastShade() {
        overlayShade = 0;
    }

    public static void resetCastValue() {
        lastCastValue = 0;
    }
    public static void storeLastCastValue(Player player) {
        lastCastValue = (StardewFishingRodItem.getFishingCastStrength(player)) * MAX_WIDTH;
    }

}
