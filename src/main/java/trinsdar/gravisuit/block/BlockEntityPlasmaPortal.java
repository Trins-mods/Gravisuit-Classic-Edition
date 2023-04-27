package trinsdar.gravisuit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import trinsdar.gravisuit.util.Registry;

public class BlockEntityPlasmaPortal extends BlockEntity {
    int ticker = 0;
    public BlockEntityPlasmaPortal(BlockPos arg2, BlockState arg3) {
        super(Registry.PLASMA_PORTAL_BLOCK_ENTITY, arg2, arg3);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity){
        if (blockEntity instanceof BlockEntityPlasmaPortal portal) {
            portal.onTick(level, pos, state);
        }
    }

    public void onTick(Level level, BlockPos pos, BlockState state){
        ticker++;
        if (ticker >= 40){
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
