package trinsdar.gravisuit.entity;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.PlayMessages;
import trinsdar.gravisuit.block.BlockEntityPlasmaPortal;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.util.Registry;

import java.util.UUID;

public class PlasmaBall extends ThrowableProjectile {
    ItemRelocator.TeleportData data;
    ItemStack relocator;
    String uuid = "";
    Player shooter;
    InteractionHand hand;

    public PlasmaBall(Level level, Player shooter, ItemRelocator.TeleportData data, InteractionHand hand) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
        this.data = data;
        this.relocator = shooter.getItemInHand(hand);
        this.shooter = shooter;
        this.hand = hand;
        double y = shooter.getY() + (double)shooter.getEyeHeight() - 0.1;
        double yaw = Math.toRadians(shooter.getYRot());
        double pitch = Math.toRadians(shooter.getXRot());
        uuid = shooter.getStringUUID();
        this.setPos(shooter.getX() - Math.cos(yaw) * 0.16, y, shooter.getZ() - Math.sin(yaw) * 0.16);
        this.setLaserHeading(-Math.sin(yaw) * Math.cos(pitch), -Math.sin(pitch), Math.cos(yaw) * Math.cos(pitch), 1.0);
    }

    public PlasmaBall(EntityType<PlasmaBall> plasmaBallEntityEntityType, Level level) {
        super(plasmaBallEntityEntityType, level);

    }

    public PlasmaBall(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    public void setLaserHeading(Vec3 motion, double speed) {
        this.setLaserHeading(motion.x(), motion.y(), motion.z(), speed);
    }

    public void setLaserHeading(double newMotionX, double newMotionY, double newMotionZ, double speed) {
        double newSpeed = 1.0 / Math.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ) * speed;
        this.setDeltaMovement(newMotionX * newSpeed, newMotionY * newSpeed, newMotionZ * newSpeed);
        Vec3 motion = this.getDeltaMovement();
        this.setYRot(this.yRotO = (float)Math.toDegrees(Math.atan2(motion.x(), motion.z())));
        this.setXRot(this.xRotO = (float)Math.toDegrees(Math.atan2(motion.y(), Math.sqrt(motion.x() * motion.x() + motion.z() * motion.z()))));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity && IC2.PLATFORM.isSimulating() && !relocator.isEmpty()){
            CompoundTag nbt = relocator.getOrCreateTag();
            if (nbt.getByte("mode") == 1){
                teleportEntity(livingEntity, data.toTeleportTarget(), entity.getMotionDirection(), relocator);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (IC2.PLATFORM.isSimulating() && !relocator.isEmpty() && ElectricItem.MANAGER.canUse(relocator, 10000000)){
            CompoundTag nbt = relocator.getOrCreateTag();
            if (nbt.getByte("mode") == 2){
                BlockPos pos = result.getBlockPos().relative(result.getDirection());
                this.getLevel().setBlock(pos, Registry.PLASMA_PORTAL.defaultBlockState(), 3);
                BlockEntity be = this.getLevel().getBlockEntity(pos);
                BlockPos teleportPos = BlockPos.of(data.getPos());
                ItemRelocator.TeleportData origin = new ItemRelocator.TeleportData(pos.asLong(), this.getLevel().dimension().location().toString(), "origin");
                if (be instanceof BlockEntityPlasmaPortal portal){
                    portal.setOtherEnd(data);
                }
                TeleporterTarget teleporterTarget = data.toTeleportTarget();
                ServerLevel teleportWorld = teleporterTarget.getWorld();
                teleportWorld.setBlock(teleportPos, Registry.PLASMA_PORTAL.defaultBlockState(), 3);
                BlockEntity teleportBE = teleportWorld.getBlockEntity(teleportPos);
                if (teleportBE instanceof BlockEntityPlasmaPortal portal){
                    portal.setOtherEnd(origin);
                }
                if (nbt.contains("lastPosition")){
                    CompoundTag tag = nbt.getCompound("lastPosition");
                    TeleporterTarget lastTeleporterTarget = ItemRelocator.TeleportData.fromNBT(tag, "lastPosition").toTeleportTarget();
                    ServerLevel lastLevel = lastTeleporterTarget.getWorld();
                    if (lastLevel.getBlockState(lastTeleporterTarget.getTargetPosition()).getBlock() == Registry.PLASMA_PORTAL){
                        lastLevel.setBlock(lastTeleporterTarget.getTargetPosition(), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
                nbt.put("lastPosition", origin.writeToNBT());
            }
        }
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

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("relocator", relocator.save(new CompoundTag()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        relocator = ItemStack.of(compound.getCompound("relocator"));
    }
}
