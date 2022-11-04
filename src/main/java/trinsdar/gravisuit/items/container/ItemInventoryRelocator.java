package trinsdar.gravisuit.items.container;

import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.items.tools.ItemRelocator;

public class ItemInventoryRelocator extends PortableInventory {
    ItemStack relocator;
    InteractionHand hand;
    public ItemInventoryRelocator(Player player, ItemRelocator inv, ItemStack relocator, InteractionHand hand) {
        super(player, inv, relocator, null);
        this.relocator = relocator;
        this.hand = hand;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand interactionHand, Direction direction, int i) {
        CompoundTag nbt = StackUtil.getNbtData(relocator);
        if (player.isCrouching() && nbt.getByte("mode") == 0){
            return new ItemContainerRelocatorAdd(this, this.getID(), hand, player, i);
        }
        return new ItemContainerRelocatorDisplay(this, this.getID(), hand, player, i);
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return super.hasGui(player, hand, side);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }
}
