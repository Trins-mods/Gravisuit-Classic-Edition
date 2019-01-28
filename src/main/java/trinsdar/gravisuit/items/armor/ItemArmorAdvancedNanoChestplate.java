package trinsdar.gravisuit.items.armor;

import ic2.core.IC2;
import ic2.core.item.armor.electric.ItemArmorNanoSuit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedElectricJetpack;

public class ItemArmorAdvancedNanoChestplate extends ItemArmorNanoSuit {
    ItemArmorAdvancedElectricJetpack jetpack = new ItemArmorAdvancedElectricJetpack();

    public ItemArmorAdvancedNanoChestplate() {
        super(44, EntityEquipmentSlot.CHEST);
        this.setUnlocalizedName(GravisuitClassic.MODID + ".advancedNanoChestplate");
        this.setRegistryName("advanced_nano_chestplate");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public String getTexture() {
        return "ic2:textures/models/armor/nano";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        jetpack.onArmorTick(world, player,itemStack);
    }
}
