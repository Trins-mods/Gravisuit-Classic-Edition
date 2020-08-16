
package trinsdar.gravisuit.network;

import ic2.core.util.misc.StackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemRelocator.TeleportData;

public class PacketRelocator implements IMessage {
    public static final int ADDDESTINATION = 0;
    public static final int REMOVEDESTINATION = 1;
    public static final int ADDDEFAULT= 2;
    public static final int TELEPORT = 3;

    private boolean dataB;
    private byte function = -1;
    private TeleportData location;
    private boolean hand;

    public PacketRelocator() {
    }

    public PacketRelocator(TeleportData location, int function, boolean hand) {
        this.location = location;
        this.function = (byte) function;
        this.hand = hand;
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
        bytes.writeBoolean(hand);
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
        hand = bytes.readBoolean();
    }

    public static EnumHand boolToHand(boolean hand){
        return hand ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }

    public static boolean handToBool(EnumHand hand){
        return hand == EnumHand.MAIN_HAND;
    }

    public static class Handler extends MessageHandlerWrapper<PacketRelocator, IMessage> {

        @Override
        public IMessage handleMessage(PacketRelocator message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            ItemStack teleporter = serverPlayer.getHeldItem(boolToHand(message.hand));
            if (teleporter.isEmpty()) {
                return null;
            }

            serverPlayer.getServerWorld().addScheduledTask(() -> {
                ItemStack teleport = serverPlayer.getHeldItem(boolToHand(message.hand));
                NBTTagCompound nbt = StackUtil.getOrCreateNbtData(teleport);
                NBTTagCompound map = nbt.getCompoundTag("Locations");

                if (message.function == ADDDESTINATION) {
                    NBTTagCompound tag = new NBTTagCompound();
                    message.location.writeToNBT(tag);
                    map.setTag(message.location.getName(), tag);
                    nbt.setTag("Locations", map);
                    teleport.setTagCompound(nbt);
                    serverPlayer.openContainer.detectAndSendChanges();
                }

                if (message.function == REMOVEDESTINATION) {
                    map.removeTag(message.location.getName());
                    nbt.setTag("Locations", map);
                    teleport.setTagCompound(nbt);
                    serverPlayer.openContainer.detectAndSendChanges();
                }

                if (message.function == TELEPORT) {
                    NBTTagCompound teleportData;
                    if (map.hasKey(message.location.getName())){
                        teleportData = map.getCompoundTag(message.location.getName());
                        ItemRelocator.teleportEntity(ctx.getServerHandler().player, (int)teleportData.getDouble("X"), (int)teleportData.getDouble("Y"), (int)teleportData.getDouble("Z"), teleportData.getInteger("Dimension"), teleport);
                    }
                }

                if (message.function == ADDDEFAULT){
                    nbt.setString("DefaultLocation", message.location.getName());
                    serverPlayer.openContainer.detectAndSendChanges();
                }

            });


            return null;
        }
    }
}

