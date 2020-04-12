package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ContainerItemComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerRelocatorDisplay extends ContainerItemComponent<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/relocator_display.png");
    public ItemContainerRelocatorDisplay(IPortableInventory inv, int id, ItemStack item) {
        super(inv, id);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
