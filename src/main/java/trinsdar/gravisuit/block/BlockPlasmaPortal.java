package trinsdar.gravisuit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.util.Registry;

public class BlockPlasmaPortal extends Block implements EntityBlock {
    public BlockPlasmaPortal() {
        super(Properties.of(Material.PORTAL).instabreak().noCollission().noOcclusion().noLootTable());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return Registry.PLASMA_PORTAL_BLOCK_ENTITY.create(pos, state);
    }
}
