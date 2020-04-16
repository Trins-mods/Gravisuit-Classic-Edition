package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ContainerItemComponent;
import ic2.core.inventory.gui.GuiIC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerRelocatorAdd extends ContainerItemComponent<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/relocator_add.png");
    public ItemContainerRelocatorAdd(IPortableInventory inv, int id, EnumHand hand, EntityPlayer player) {
        super(inv, id);
        addComponent(new GuiCompRelocatorAdd(hand, player));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiLoaded(GuiIC2 gui) {
        gui.setMaxGuiY(66);
        gui.disableName();
        gui.dissableInvName();
    }
}
