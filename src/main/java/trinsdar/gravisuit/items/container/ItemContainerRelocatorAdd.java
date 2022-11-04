package trinsdar.gravisuit.items.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerRelocatorAdd extends ItemContainer<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/relocator_add.png");
    public ItemContainerRelocatorAdd(ItemInventoryRelocator inv, int id, InteractionHand hand, Player player, int windowId) {
        super(inv, player, id, windowId);
        addComponent(new GuiCompRelocatorAdd(hand, player));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setYSize(66);
    }
}
