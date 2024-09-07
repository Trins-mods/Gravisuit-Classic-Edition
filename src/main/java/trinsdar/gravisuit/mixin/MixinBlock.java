package trinsdar.gravisuit.mixin;

import ic2.api.items.electric.ElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemMagnet;
import trinsdar.gravisuit.util.Registry;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Block.getDrops;
import static net.minecraft.world.level.block.Block.popResource;

@Debug(export = true)
@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow public abstract void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool);

    @Shadow
    public static void popResource(Level pLevel, BlockPos pPos, ItemStack pStack) {
    }

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)V", at = @At("HEAD"), cancellable = true)
    private static void injectDropResources(BlockState state, LootContext.Builder lootContextBuilder, CallbackInfo ci) {
        Entity entity = lootContextBuilder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player){
            ItemStack magnet = findStack(Registry.MAGNET, player);
            if (!magnet.isEmpty() && ItemMagnet.getMagnetMode(magnet) == ItemMagnet.MagnetMode.ADD_TO_INVENTORY){
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
            if (!magnet.isEmpty() && ItemMagnet.getMagnetMode(magnet) == ItemMagnet.MagnetMode.ADD_TO_INVENTORY){
                getDrops(state, serverLevel, pos, blockEntity, entity, stack).forEach((itemStack) -> {
                    if (!ElectricItem.MANAGER.canUse(magnet, 10)){
                        popResource(serverLevel, pos, itemStack);
                    } else {
                        if (gravisuit$popResource(serverLevel, pos, itemStack, player)) {
                            ElectricItem.MANAGER.use(magnet, 10, player);
                        }
                    }
                });
                state.spawnAfterBreak((ServerLevel)level, pos, stack, true);
                ci.cancel();
            }

        }

    }

    private static boolean gravisuit$popResource(Level pLevel, BlockPos pPos, ItemStack pStack, Player player) {
        float f = EntityType.ITEM.getHeight() / 2.0F;
        double d0 = (double)((float)pPos.getX() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25);
        double d1 = (double)((float)pPos.getY() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25) - (double)f;
        double d2 = (double)((float)pPos.getZ() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25);
        return gravisuit$popResource(pLevel, () -> {
            return new ItemEntity(pLevel, d0, d1, d2, pStack);
        }, pStack, player);
    }

    private static boolean gravisuit$popResource(Level pLevel, Supplier<ItemEntity> pItemEntitySupplier, ItemStack pStack, Player player) {
        if (!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots) {
            ItemEntity itementity = pItemEntitySupplier.get();
            GravisuitClassic.LOGGER.info(pStack.getCount());
            itementity.setNoPickUpDelay();
            itementity.playerTouch(player);
            ItemStack leftover = itementity.getItem();
            if (itementity.isRemoved()){
                pLevel.addFreshEntity(itementity);
            }
            return leftover.getCount() != pStack.getCount() || itementity.isRemoved();
        }
        return false;
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
