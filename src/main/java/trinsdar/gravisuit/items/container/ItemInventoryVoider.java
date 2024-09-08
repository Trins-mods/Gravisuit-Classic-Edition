package trinsdar.gravisuit.items.container;

import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemVoider;

public class ItemInventoryVoider extends PortableInventory {
    public ItemInventoryVoider(Player player, ItemVoider inv, ItemStack stack, Slot slot) {
        super(player, inv, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand interactionHand, Direction direction, int i) {
        return new ItemContainerVoider(this, this.getID(), player, i);
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return super.hasGui(player, hand, side);
    }

    @Override
    public int getSlotCount() {
        return 5;
    }
}
