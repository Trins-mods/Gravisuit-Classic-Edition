package trinsdar.gravisuit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import trinsdar.gravisuit.util.Registry;

public class BlockEntityPlasmaPortal extends BlockEntity {
    public BlockEntityPlasmaPortal(BlockPos arg2, BlockState arg3) {
        super(Registry.PLASMA_PORTAL_BLOCK_ENTITY, arg2, arg3);
    }


}
