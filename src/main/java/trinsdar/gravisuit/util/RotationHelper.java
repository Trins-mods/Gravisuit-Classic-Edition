package trinsdar.gravisuit.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class RotationHelper {
    /*
    * NOTE: This code was not my own; I got it from RotationUtil in immersive engineering as permitted by their license: https://github.com/BluSunrize/ImmersiveEngineering/blob/master/LICENSE
    * Proper credit goes to BluSunrize for the code.
    * */
    public static final List<RotationBlacklistEntry> blacklist = new ArrayList<>();

    static
    {
        blacklist.add((w, pos) -> {
            BlockState state = w.getBlockState(pos);
            return state.getBlock()!=Blocks.CHEST||state.get(ChestBlock.CHEST_TYPE)== ChestType.SINGLE;
        });
    }

    public static boolean rotateBlock(World world, BlockPos pos, boolean inverse)
    {
        return rotateBlock(world, pos, inverse?BlockRotation.COUNTERCLOCKWISE_90: BlockRotation.CLOCKWISE_90);
    }

    public static boolean rotateBlock(World world, BlockPos pos, BlockRotation rotation) {
        for(RotationBlacklistEntry e : blacklist)
            if(!e.blockRotation(world, pos))
                return false;

        BlockState state = world.getBlockState(pos);
        BlockState newState = state.rotate(world, pos, rotation);
        if(newState!=state)
        {
            world.setBlockState(pos, newState);
            for(Direction d : Direction.values())
            {
                final BlockPos otherPos = pos.offset(d);
                final BlockState otherState = world.getBlockState(otherPos);
                final BlockState nextState = newState.getStateForNeighborUpdate(d, otherState, world, pos, otherPos);
                if(nextState!=newState)
                {
                    if(!nextState.isAir())
                    {
                        world.setBlockState(pos, nextState);
                        newState = nextState;
                    }
                    else
                    {
                        world.setBlockState(pos, state);
                        return false;
                    }
                }
            }
            for(Direction d : Direction.values())
            {
                final BlockPos otherPos = pos.offset(d);
                final BlockState otherState = world.getBlockState(otherPos);
                final BlockState nextOther = otherState.getStateForNeighborUpdate(d.getOpposite(), newState, world, otherPos, pos);
                if(nextOther!=otherState)
                    world.setBlockState(otherPos, nextOther);
            }
            return true;
        }
        else
            return false;
    }

    @FunctionalInterface
    public interface RotationBlacklistEntry
    {
        boolean blockRotation(World w, BlockPos pos);
    }

}
