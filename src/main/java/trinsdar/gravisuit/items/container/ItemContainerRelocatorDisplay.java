package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ContainerItemComponent;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.util.misc.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerRelocatorDisplay extends ContainerItemComponent<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/relocator_display.png");
    public ItemContainerRelocatorDisplay(IPortableInventory inv, int id, ItemStack item, EntityPlayer player) {
        super(inv, id);
        NBTTagCompound nbt = StackUtil.getNbtData(item);
        if (nbt.hasKey("map")){
            NBTTagCompound map = nbt.getCompoundTag("map");
            if (map.getSize() > 0 && map.getSize() < 11){
                int i = 0;
                for (String name : map.getKeySet()){
                    addComponent(new GuiCompRelocatorDisplay(item, i, player, name));
                    i++;
                }
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiLoaded(GuiIC2 gui) {
        gui.setMaxGuiY(116);
        gui.disableName();
        gui.dissableInvName();
    }
}
