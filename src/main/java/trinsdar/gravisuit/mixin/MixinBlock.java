package trinsdar.gravisuit.mixin;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.curioplugin.CurioModule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import trinsdar.gravisuit.util.Registry;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.Block.getDrops;
import static net.minecraft.world.level.block.Block.popResource;

@Debug(export = true)
@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)V", at = @At("HEAD"), cancellable = true)
    private static void injectDropResources(BlockState state, LootContext.Builder lootContextBuilder, CallbackInfo ci) {
        Entity entity = lootContextBuilder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player){
            ItemStack magnet = findStack(Registry.MAGNET, player);
            if (!magnet.isEmpty()){
                ServerLevel serverlevel = lootContextBuilder.getLevel();
                BlockPos blockpos = new BlockPos(lootContextBuilder.getParameter(LootContextParams.ORIGIN));

                state.getDrops(lootContextBuilder).forEach((itemStack) -> {
                    if (!ElectricItem.MANAGER.canUse(magnet, 10) || !player.addItem(itemStack)){
                        popResource(serverlevel, blockpos, itemStack);
                    } else {
                        ElectricItem.MANAGER.use(magnet, 10, player);
                    }
                });
                state.spawnAfterBreak(serverlevel, blockpos, ItemStack.EMPTY, true);
                ci.cancel();
            }
        }

    }

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void injectDropResources(BlockState state, Level level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && entity instanceof Player player) {
            ItemStack magnet = findStack(Registry.MAGNET, player);
            if (!magnet.isEmpty()){
                getDrops(state, serverLevel, pos, blockEntity, entity, stack).forEach((itemStack) -> {
                    if (!ElectricItem.MANAGER.canUse(magnet, 10) || !player.addItem(itemStack)){
                        popResource(serverLevel, pos, itemStack);
                    } else {
                        ElectricItem.MANAGER.use(magnet, 10, player);
                    }
                });
                state.spawnAfterBreak((ServerLevel)level, pos, stack, true);
                ci.cancel();
            }

        }

    }

    private static ItemStack findStack(Item filter, Player player){
        if (ModList.get().isLoaded("curios")){
            ItemStack curios = getCuriosItem(filter, player);
            if (!curios.isEmpty()){
                return curios;
            }
        }
        for (ItemStack slot : player.getInventory().items) {
            if (slot.getItem() == filter){
                return slot;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack getCuriosItem(Item filter, Player player){
        return CuriosApi.getCuriosHelper().findFirstCurio(player, filter).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }
}
