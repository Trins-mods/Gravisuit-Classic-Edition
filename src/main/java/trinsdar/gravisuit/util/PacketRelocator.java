/*
package trinsdar.gravisuit.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import reborncore.common.util.ItemNBTHelper;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemRelocator.TeleportData;

public class PacketRelocator implements IMessage {
    public static final int ADDDESTINATION = 0;
    public static final int REMOVEDESTINATION = 1;
    public static final int ADDDEFAULT= 2;
    public static final int TELEPORT = 3;

    private int data = 0;
    private boolean dataB;
    private byte function = -1;
    private TeleportData location;

    public PacketRelocator() {
    }

    public PacketRelocator(int function, int data, boolean b) {
        this.data = data;
        this.function = (byte) function;
        this.dataB = b;
    }

    public PacketRelocator(TeleportData location, int function) {
        this.location = location;
        this.function = (byte) function;
    }

    public PacketRelocator(TeleportData location, int function, int data) {
        this.data = data;
        this.location = location;
        this.function = (byte) function;
    }

    @Override
    public void toBytes(ByteBuf bytes) {
        bytes.writeByte(function);
        if (function == ADDDESTINATION) {
            bytes.writeDouble(location.getX());
            bytes.writeDouble(location.getY());
            bytes.writeDouble(location.getZ());
            bytes.writeInt(location.getDimId());
            ByteBufUtils.writeUTF8String(bytes, location.getName());
            //ByteBufUtils.writeUTF8String(bytes, location.getDimensionName());
        }

        if (function == REMOVEDESTINATION) {
            bytes.writeInt(data);
        }

        if (function == TELEPORT) {
            bytes.writeInt(data);
        }
    }

    @Override
    public void fromBytes(ByteBuf bytes) {
        function = bytes.readByte();
        if (function == ADDDESTINATION) {
            location = new TeleportData();
            location.setX(bytes.readInt());
            location.setY(bytes.readInt());
            location.setZ(bytes.readInt());
            location.setDimId(bytes.readInt());
            location.setName(ByteBufUtils.readUTF8String(bytes));
        }

        if (function == REMOVEDESTINATION) {
            data = bytes.readInt();
        }

        if (function == TELEPORT) {
            data = bytes.readInt();
        }
    }

    public static class Handler extends MessageHandlerWrapper<PacketRelocator, IMessage> {

        @Override
        public IMessage handleMessage(PacketRelocator message, MessageContext ctx) {
            ItemStack teleporter = HandHelper.getItem(ctx.getServerHandler().player, DEFeatures.dislocatorAdvanced);
            if (teleporter.isEmpty()) {
                return null;
            }

            NBTTagCompound compound = ItemNBTHelper.getCompound(teleporter);
            NBTTagList list = compound.getTagList("Locations", 10);

            if (message.function == ADDDESTINATION) {
                NBTTagCompound tag = new NBTTagCompound();
                message.location.setDimensionName(BrandonsCore.proxy.getMCServer().getWorld(message.location.getDimension()).provider.getDimensionType().getName());

                message.location.setXCoord(ctx.getServerHandler().player.posX);
                message.location.setYCoord(ctx.getServerHandler().player.posY);
                message.location.setZCoord(ctx.getServerHandler().player.posZ);

                message.location.writeToNBT(tag);
                list.appendTag(tag);
                compound.setTag("Locations", list);
                teleporter.setTagCompound(compound);
            }

            if (message.function == SCROLL) {
                int selected = ItemNBTHelper.getShort(teleporter, "Selection", (short) 0);
                int selectionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
                int maxSelect = Math.min(list.tagCount() - 1, 11);
                int maxOffset = Math.max(list.tagCount() - 12, 0);

                if (message.data > 0 && selected < maxSelect) {
                    ItemNBTHelper.setShort(teleporter, "Selection", (short) (selected + 1));
                    return null;
                }
                if (message.data > 0 && selectionOffset < maxOffset) {
                    ItemNBTHelper.setInteger(teleporter, "SelectionOffset", selectionOffset + 1);
                    return null;
                }
                if (message.data < 0 && selected > 0) {
                    ItemNBTHelper.setShort(teleporter, "Selection", (short) (selected - 1));
                    return null;
                }
                if (message.data < 0 && selectionOffset > 0) {
                    ItemNBTHelper.setInteger(teleporter, "SelectionOffset", selectionOffset - 1);
                    return null;
                }

            }

            if (message.function == UPDATEDESTINATION) {
                NBTTagCompound tag = list.getCompoundTagAt(message.data);
                message.location.setDimensionName(BrandonsCore.proxy.getMCServer().getWorld(message.location.getDimension()).provider.getDimensionType().getName());

                message.location.setXCoord(ctx.getServerHandler().player.posX);
                message.location.setYCoord(ctx.getServerHandler().player.posY);
                message.location.setZCoord(ctx.getServerHandler().player.posZ);

                message.location.writeToNBT(tag);
                list.set(message.data, tag);
                compound.setTag("Locations", list);
                teleporter.setTagCompound(compound);
            }

            if (message.function == UPDATELOCK) {
                list.getCompoundTagAt(message.data).setBoolean("WP", message.dataB);
                compound.setTag("Locations", list);
                teleporter.setTagCompound(compound);
            }

            if (message.function == REMOVEDESTINATION) {
                list.removeTag(message.data);
                compound.setTag("Locations", list);
                teleporter.setTagCompound(compound);
            }

            if (message.function == UPDATENAME) {
                list.getCompoundTagAt(message.data).setString("Name", message.location.getName());
                compound.setTag("Locations", list);
                teleporter.setTagCompound(compound);
            }

            if (message.function == TELEPORT) {
                int fuel = ItemNBTHelper.getInteger(teleporter, "Fuel", 0);
                if (!ctx.getServerHandler().player.capabilities.isCreativeMode) ItemNBTHelper.setInteger(teleporter, "Fuel", fuel - 1);
                TeleportLocation destination = new TeleportLocation();
                destination.readFromNBT(list.getCompoundTagAt(message.data));

                if (!ctx.getServerHandler().player.world.isRemote) {
                    DESoundHandler.playSoundFromServer(ctx.getServerHandler().player.world, ctx.getServerHandler().player.posX, ctx.getServerHandler().player.posY, ctx.getServerHandler().player.posZ, DESoundHandler.portal, SoundCategory.PLAYERS, 0.1F, ctx.getServerHandler().player.world.rand.nextFloat() * 0.1F + 0.9F, false, 32);
                }
                destination.teleport(ctx.getServerHandler().player);
                if (!ctx.getServerHandler().player.world.isRemote) {
                    DESoundHandler.playSoundFromServer(ctx.getServerHandler().player.world, ctx.getServerHandler().player.posX, ctx.getServerHandler().player.posY, ctx.getServerHandler().player.posZ, DESoundHandler.portal, SoundCategory.PLAYERS, 0.1F, ctx.getServerHandler().player.world.rand.nextFloat() * 0.1F + 0.9F, false, 32);
                }
            }

            if (message.function == MOVELOCATION) {
                int selected = ItemNBTHelper.getShort(teleporter, "Selection", (short) 0);
                int selectionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
                int maxSelect = Math.min(list.tagCount() - 1, 11);
                int maxOffset = Math.max(list.tagCount() - 12, 0);

                if (message.dataB) //up
                {
                    if (selected > 0) {
                        NBTTagCompound temp = list.getCompoundTagAt(selected + selectionOffset);
                        list.set(selected + selectionOffset, list.getCompoundTagAt(selected + selectionOffset - 1));
                        list.set(selected + selectionOffset - 1, temp);
                        compound.setTag("Locations", list);
                        teleporter.setTagCompound(compound);
                        ItemNBTHelper.setShort(teleporter, "Selection", (short) (ItemNBTHelper.getShort(teleporter, "Selection", (short) 0) - 1));
                    }
                }
                else //down
                {
                    if (selected < maxSelect) {
                        NBTTagCompound temp = list.getCompoundTagAt(selected + selectionOffset);
                        list.set(selected + selectionOffset, list.getCompoundTagAt(selected + selectionOffset + 1));
                        list.set(selected + selectionOffset + 1, temp);
                        compound.setTag("Locations", list);
                        teleporter.setTagCompound(compound);
                        ItemNBTHelper.setShort(teleporter, "Selection", (short) (ItemNBTHelper.getShort(teleporter, "Selection", (short) 0) + 1));
                    }
                }
            }

            if (message.function == ADDFUEL) {
                int fuel = ItemNBTHelper.getInteger(teleporter, "Fuel", 0);
                int count = 0;
                for (int i = 0; i < message.data; i++) {
                    if (ctx.getServerHandler().player.inventory.hasItemStack(new ItemStack(Items.ENDER_PEARL))) {
                        ctx.getServerHandler().player.inventory.clearMatchingItems(Items.ENDER_PEARL, 0, 1, null);
                        count++;
                    }
                    else break;
                }
                ItemNBTHelper.setInteger(teleporter, "Fuel", fuel + (DEConfig.dislocatorUsesPerPearl * count));
            }

            if (message.function == CHANGESELECTION) {
                ItemNBTHelper.setShort(teleporter, "Selection", (short) message.data);
            }

            if (message.function == UPDATEOFFSET) {
                ItemNBTHelper.setInteger(teleporter, "SelectionOffset", message.data);
            }
            return null;
        }
    }
}
*/
