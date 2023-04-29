package trinsdar.gravisuit.block;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.util.Registry;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityPlasmaPortal extends BlockEntity {
    ItemRelocator.TeleportData otherEnd = null;

    boolean insidePortal = false;
    List<LivingEntity> entitesToTeleport = new ArrayList<>();

    int ticker = 0;
    public BlockEntityPlasmaPortal(BlockPos arg2, BlockState arg3) {
        super(Registry.PLASMA_PORTAL_BLOCK_ENTITY, arg2, arg3);
    }

    public void setOtherEnd(ItemRelocator.TeleportData otherEnd) {
        this.otherEnd = otherEnd;
    }

    public void setInsidePortal(boolean insidePortal) {
        this.insidePortal = insidePortal;
    }

    public void addEntityToTeleport(LivingEntity entity){
        entitesToTeleport.add(entity);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity){
        if (blockEntity instanceof BlockEntityPlasmaPortal portal) {
            portal.onTick(level, pos, state);
        }
    }

    public void onTick(Level level, BlockPos pos, BlockState state){
        ticker++;
        if (!this.entitesToTeleport.isEmpty() && otherEnd != null){
            entitesToTeleport.forEach(e -> {
                teleportEntity(e, otherEnd.toTeleportTarget(), e.getDirection());
            });
            entitesToTeleport.clear();
        }
        if (ticker >= 500){
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir) {
        int weight = TeleportUtil.getWeightOfEntity(player, true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            TeleportUtil.teleportEntity(player, server, pos, dir);
        }
    }
}
