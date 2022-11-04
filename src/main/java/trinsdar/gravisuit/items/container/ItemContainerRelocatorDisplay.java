package trinsdar.gravisuit.items.container;

import ic2.core.IC2;
import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerRelocatorDisplay extends ItemContainer<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/relocator_display.png");
    public ItemContainerRelocatorDisplay(ItemInventoryRelocator inv, int id, InteractionHand hand, Player player, int windowID) {
        super(inv, player, id, windowID);
        if (IC2.PLATFORM.isRendering()) {
            addComponent(new GuiCompRelocatorDisplay(player, hand));
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setYSize(116);
        screen.setGuiName(Component.empty());
    }
}
