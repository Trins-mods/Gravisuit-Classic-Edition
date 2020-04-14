
package trinsdar.gravisuit.network;

import ic2.core.util.misc.StackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemRelocator.TeleportData;
import trinsdar.gravisuit.util.Registry;

public class PacketRelocator implements IMessage {
    public static final int ADDDESTINATION = 0;
    public static final int REMOVEDESTINATION = 1;
    public static final int ADDDEFAULT= 2;
    public static final int TELEPORT = 3;

    private boolean dataB;
    private byte function = -1;
    private TeleportData location;
    private ItemStack relocator;

    public PacketRelocator() {
    }

    public PacketRelocator(TeleportData location, int function, ItemStack relocator) {
        this.location = location;
        this.function = (byte) function;
        this.relocator = relocator;
    }

    public ItemStack getRelocator() {
        return relocator;
    }

    @Override
    public void toBytes(ByteBuf bytes) {
        bytes.writeByte(function);
        if (function == ADDDESTINATION) {
            bytes.writeInt(location.getX());
            bytes.writeInt(location.getY());
            bytes.writeInt(location.getZ());
            bytes.writeInt(location.getDimId());
            ByteBufUtils.writeUTF8String(bytes, location.getName());
        }

        if (function == REMOVEDESTINATION || function == TELEPORT || function == ADDDEFAULT) {
            ByteBufUtils.writeUTF8String(bytes, location.getName());
        }
        ByteBufUtils.writeItemStack(bytes, relocator);
    }

    @Override
    public void fromBytes(ByteBuf bytes) {
        function = bytes.readByte();
        if (function == ADDDESTINATION) {
            location = new TeleportData(bytes.readInt(), bytes.readInt(), bytes.readInt(), bytes.readInt(), ByteBufUtils.readUTF8String(bytes));
        }

        if (function == REMOVEDESTINATION || function == TELEPORT || function == ADDDEFAULT) {
            location = new TeleportData(ByteBufUtils.readUTF8String(bytes));
        }
        relocator = ByteBufUtils.readItemStack(bytes);
    }

    public static class Handler extends MessageHandlerWrapper<PacketRelocator, IMessage> {

        @Override
        public IMessage handleMessage(PacketRelocator message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            ItemStack teleporter = getItem(serverPlayer, Registry.relocator);
            if (teleporter.isEmpty()) {
                return null;
            }


            serverPlayer.getServerWorld().addScheduledTask(() -> {
                NBTTagCompound nbt = StackUtil.getNbtData(teleporter);
                NBTTagCompound map = nbt.getCompoundTag("Locations");

                if (message.function == ADDDESTINATION) {
                    NBTTagCompound tag = new NBTTagCompound();
                    message.location.writeToNBT(tag);
                    map.setTag(message.location.getName(), tag);
                    nbt.setTag("Locations", map);
                    teleporter.setTagCompound(nbt);
                }

                if (message.function == REMOVEDESTINATION) {
                    map.removeTag(message.location.getName());
                    nbt.setTag("Locations", map);
                    teleporter.setTagCompound(nbt);
                }

                if (message.function == TELEPORT) {
                    NBTTagCompound teleportData;
                    if (map.hasKey(message.location.getName())){
                        teleportData = map.getCompoundTag(message.location.getName());
                        ItemRelocator.teleportEntity(ctx.getServerHandler().player, (int)teleportData.getDouble("X"), (int)teleportData.getDouble("Y"), (int)teleportData.getDouble("Z"), teleportData.getInteger("Dimension"), teleporter);
                    }
                }

                if (message.function == ADDDEFAULT){
                    nbt.setString("DefaultLocation", message.location.getName());
                }
            });


            return null;
        }

        public static ItemStack getItem(EntityPlayer player, Item item) {
            if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == item) {
                return player.getHeldItemMainhand();
            }
            else if (!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() == item) {
                return player.getHeldItemOffhand();
            }
            return ItemStack.EMPTY;
        }
    }
}

