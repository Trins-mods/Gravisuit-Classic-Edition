package trinsdar.gravisuit.util;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.function.Predicate;

public class RotationHelper {
    /*
    * NOTE: This code was not my own; I got it from RotationUtil in immersive engineering as permitted by their license: https://github.com/BluSunrize/ImmersiveEngineering/blob/master/LICENSE
    * Proper credit goes to BluSunrize for the code.
    * */
    public static HashSet<Predicate<IBlockState>> permittedRotation = new HashSet<>();
    public static HashSet<Predicate<TileEntity>> permittedTileRotation = new HashSet<>();

    static
    {
        permittedRotation.add(state -> {
            //preventing extended pistons from rotating
            return !((state.getBlock()== Blocks.PISTON||state.getBlock()==Blocks.STICKY_PISTON)&&state.getValue(BlockPistonBase.EXTENDED));
        });
        permittedRotation.add(state -> {
            //beds don't like being rotated piecewise
            return state.getBlock()!=Blocks.BED;
        });
		/*permittedRotation.add(state -> {
			//A lot of the RS stuff breaks when rotated
			Block b = state.getBlock();
			return b!=Blocks.TRIPWIRE_HOOK&&b!=Blocks.STONE_BUTTON&&b!=Blocks.WOODEN_BUTTON&&b!=Blocks.LEVER&&b!=Blocks.REDSTONE_TORCH;
		});
		permittedRotation.add(state -> {
			//misc things don't like floating in the air...
			Block b = state.getBlock();
			return b!=Blocks.TORCH&&b!=Blocks.LADDER&&b!=Blocks.WALL_SIGN&&b!=Blocks.WALL_BANNER;
		});*/
        permittedRotation.add(state -> {
            //preventing endportals, skulls from rotating
            return !(state.getBlock()==Blocks.END_PORTAL_FRAME||state.getBlock()==Blocks.SKULL);
        });
        permittedTileRotation.add(tile -> {
            //preventing double chests from rotating
            if(tile instanceof TileEntityChest)
            {
                TileEntityChest chest = (TileEntityChest)tile;
                return chest.adjacentChestXNeg!=null||chest.adjacentChestXPos!=null||chest.adjacentChestZNeg!=null||chest.adjacentChestZPos!=null;
            }
            return true;
        });
    }

    public static boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        for(Predicate<IBlockState> pred : permittedRotation)
            if(!pred.test(state))
                return false;
        if(state.getBlock().hasTileEntity(state))
        {
            TileEntity tile = world.getTileEntity(pos);
            if(tile!=null)
                for(Predicate<TileEntity> pred : permittedTileRotation)
                    if(!pred.test(tile))
                        return false;
        }
        return state.getBlock().rotateBlock(world, pos, axis);
    }

}
