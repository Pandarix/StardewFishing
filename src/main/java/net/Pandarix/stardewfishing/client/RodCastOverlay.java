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
            RenderSystem.setShaderColor(0.0F, 0.5F, 1.0F, 1.0F); //Shade Blue

            //Display variables
            int posX = width / 2 - 91;
            int posY = height - 32 * 2 + 3;
            int maxWidth = 182;

            //Displays gray-ish background
            gui.blit(poseStack, posX, posY, 0, 64, maxWidth, 5);

            /*
            //Value of use progress
            double k = Math.sin( //Generate a value between -1 & 1
                    ((minecraft.player.getUseItemRemainingTicks() * Math.PI) / StardewFishingRodItem.USE_DURATION)) //Normalize to a range of 0 & 1
                    * maxWidth; //Apply to width of bar
            */

            assert minecraft.player != null;
            double k =
            (
                Math.abs(
                    Math.sin(
                            (minecraft.player.getUseItemRemainingTicks() / (float) StardewFishingRodItem.USE_DURATION) * Math.PI + (Math.PI / 2)
                    )
                ) * (-1) + 1
            ) * maxWidth;

            //Display blue bar for use progress
            if (k >= 1) {
                gui.blit(poseStack, posX, posY, 0, 69, (int) Math.round(k), 5);
            }

            /*
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, NO);

            for (int i = 0; i < maxCount; i++) {
                GuiComponent.blit(poseStack, x - 94 + (i * 9), y - 54, 0, 0, 9, 9, 9, 9);
            }

            RenderSystem.setShaderTexture(0, YES);

            for (int i = 0; i < gui.getMinecraft().player.getUseItemRemainingTicks(); i++) {
                GuiComponent.blit(poseStack, x - 94 + (i * 9), y - 54, 0, 0, 9, 9, 9, 9);
            }
             */
        }
    });

    public static final void enableOverlay() {
        showOverlay = true;
    }

    public static final void hideOverlay() {
        showOverlay = false;
    }

}
