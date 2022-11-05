package net.Pandarix.stardewfishing.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Pandarix.stardewfishing.StardewFishing;
import net.Pandarix.stardewfishing.common.item.StardewFishingRodItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class RodCastOverlay {

    private static final ResourceLocation NO = new ResourceLocation(StardewFishing.MOD_ID, "textures/no.png");
    private static final ResourceLocation YES = new ResourceLocation(StardewFishing.MOD_ID, "textures/yes.png");
    private static boolean showOverlay = false;
    public static final IGuiOverlay HUD_ROD_CAST = ((gui, poseStack, partialTick, width, height) -> {
        if (showOverlay) {
            Minecraft minecraft = gui.getMinecraft(); //Minecraft Client instance

            minecraft.getProfiler().push("castBar"); //divider name
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION); //Texture set
            RenderSystem.setShaderColor(1.0F, 1.5F, 1.0F, 1.0F); //Shade lightly green

            //Display variables
            int posX = width / 2 - 91;
            int posY = (int) (height - 32 * 1.5 + 3);
            int maxWidth = 182;

            //Displays gray-ish background
            gui.blit(poseStack, posX, posY, 0, 84, maxWidth, 5);

            assert minecraft.player != null;
            double k =(StardewFishingRodItem.getFishingCastStrength(minecraft.player)) * maxWidth;

            //Display blue bar for use progress
            if (k >= 1) {
                gui.blit(poseStack, posX, posY, 0, 89, (int) Math.round(k), 5);
            }
        }
    });

    public static final void enableOverlay() {
        showOverlay = true;
    }

    public static final void hideOverlay() {
        showOverlay = false;
    }

}
