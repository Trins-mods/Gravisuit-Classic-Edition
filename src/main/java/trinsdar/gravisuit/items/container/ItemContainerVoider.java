package trinsdar.gravisuit.items.container;

import ic2.core.IC2;
import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.item.inv.container.ScannerContainer;
import ic2.core.item.inv.inventory.ScannerInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemContainerVoider extends ItemContainer<ItemInventoryVoider> {
    public static ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/gui/voider.png");
    public ItemContainerVoider(ItemInventoryVoider inv, int id, Player player, int windowId) {
        super(inv, player, id, windowId);
        for(int i = 0; i < 5; ++i) {
            this.addSlot(new GhostSlot(inv, i, 44 + 18 * i, 24, SpecialFilters.ALWAYS_TRUE));
        }
        this.addPlayerInventoryWithOffset(player.getInventory(), 0, -17);
        this.addComponent(new FilterComponent(this.getPreviewOffset()));
    }

    @OnlyIn(Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 149);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0 && slotId < this.getInventorySize()) {
            ItemStack stack = this.getCarried();
            this.getHolder().setStackInSlot(slotId, stack.isEmpty() ? ItemStack.EMPTY : StackUtil.copyWithSize(stack, 1));
            return;
        }
        super.clicked(slotId, dragType, clickTypeIn, player);
    }
}
