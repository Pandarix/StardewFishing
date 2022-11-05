package net.Pandarix.stardewfishing.client.screen;

import net.Pandarix.stardewfishing.StardewFishing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RodReelingScreen extends Gui {

    private static final ResourceLocation ROD_REELING_GUI = new ResourceLocation(StardewFishing.MOD_ID, "textures/gui/reeling_gui.png");

    public RodReelingScreen(Minecraft pMinecraft, ItemRenderer pItemRenderer) {
        super(pMinecraft, pItemRenderer);
    }

}
