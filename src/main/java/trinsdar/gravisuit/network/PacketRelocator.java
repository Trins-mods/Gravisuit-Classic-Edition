
package trinsdar.gravisuit.network;

import ic2.core.utils.helpers.StackUtil;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemRelocator.TeleportData;

import java.util.function.Supplier;

public class PacketRelocator  {

    private boolean dataB;
    private TeleportFunction function;
    private TeleportData location;
    private InteractionHand hand;

    public PacketRelocator(TeleportData location, TeleportFunction function, InteractionHand hand) {
        this.location = location;
        this.function = function;
        this.hand = hand;
    }

    public static void encode(PacketRelocator relocator, FriendlyByteBuf bytes) {
        bytes.writeEnum(relocator.function);
        if (relocator.function == TeleportFunction.ADDDESTINATION) {
            bytes.writeInt(relocator.location.getX());
            bytes.writeInt(relocator.location.getY());
            bytes.writeInt(relocator.location.getZ());
            ByteBufUtil.writeUtf8(bytes, relocator.location.getDimId());
            ByteBufUtil.writeUtf8(bytes, relocator.location.getName());
        }else {
            ByteBufUtil.writeUtf8(bytes, relocator.location.getName());
        }
        bytes.writeEnum(relocator.hand);
    }


    public static PacketRelocator decode(FriendlyByteBuf bytes) {
        TeleportFunction function = bytes.readEnum(TeleportFunction.class);
        TeleportData location = null;
        if (function == TeleportFunction.ADDDESTINATION) {
            //location = new TeleportData(bytes.readInt(), bytes.readInt(), bytes.readInt(), ByteBufUtils.readUTF8String(bytes), ByteBufUtils.readUTF8String(bytes));
        } else {
            //location = new TeleportData(ByteBufUtils.readUTF8String(bytes));
        }
        InteractionHand hand = bytes.readEnum(InteractionHand.class);
        return new PacketRelocator(location, function, hand);
    }

    public static InteractionHand boolToHand(boolean hand){
        return hand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static boolean handToBool(InteractionHand hand){
        return hand == InteractionHand.MAIN_HAND;
    }

    public static void handle(PacketRelocator msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            NetworkEvent.Context context = ctx.get();
            ServerPlayer sender = context.getSender();
            if (sender != null) {
                ItemStack teleporter = sender.getItemInHand(msg.hand);
                if (teleporter.isEmpty()) return;
                CompoundTag nbt = StackUtil.getNbtData(teleporter);
                if (nbt.contains("Locations")){
                    CompoundTag map = nbt.getCompound("Locations");
                    if (msg.function == TeleportFunction.REMOVEDESTINATION){
                        if (map.contains(msg.location.getName())){
                            map.remove(msg.location.getName());
                        }
                    }

                    if (msg.function == TeleportFunction.ADDDEFAULT && map.contains(msg.location.getName())){
                        nbt.putString("DefaultLocation", msg.location.getName());
                    }
                    if (msg.function == TeleportFunction.ADDDESTINATION){
                        CompoundTag entry = new CompoundTag();
                        entry.putInt("X", msg.location.getX());
                        entry.putInt("Y", msg.location.getY());
                        entry.putInt("Z", msg.location.getZ());
                        entry.putString("Dimension", msg.location.getDimId());
                        map.put(msg.location.getName(), entry);
                    }
                    if (msg.function == TeleportFunction.TELEPORT){
                        if (map.contains(msg.location.getName())){
                            CompoundTag entry = map.getCompound(msg.location.getName());
                            //ItemRelocator.teleportEntity(sender, entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"), entry.getString("Dimension"), teleporter);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum TeleportFunction {
        ADDDESTINATION,
        REMOVEDESTINATION,
        ADDDEFAULT,
        TELEPORT;
    }
}

