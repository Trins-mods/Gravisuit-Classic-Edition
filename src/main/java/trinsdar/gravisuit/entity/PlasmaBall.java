package trinsdar.gravisuit.entity;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.util.Registry;

public class PlasmaBall extends ThrowableProjectile {
    ItemRelocator.TeleportData data;
    ItemStack relocator;
    public PlasmaBall(Level level, ItemRelocator.TeleportData data, ItemStack relocator) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
        this.data = data;
        this.relocator = relocator;
    }

    public PlasmaBall(EntityType<PlasmaBall> plasmaBallEntityEntityType, Level level) {
        super(plasmaBallEntityEntityType, level);

    }

    public PlasmaBall(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity){
            teleportEntity(livingEntity, data.toTeleportTarget(), entity.getMotionDirection(), relocator);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir, ItemStack stack) {
        int weight = TeleportUtil.getWeightOfEntity(player, true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            if (ElectricItem.MANAGER.use(stack, (int)((double)weight * TeleportUtil.getDistanceCost(player.getLevel(), player.blockPosition(), server, pos) * 5.0), player)) {
                TeleportUtil.teleportEntity(player, server, pos, dir);
            }
        }
    }
}
