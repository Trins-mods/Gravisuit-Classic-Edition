package trinsdar.gravisuit.items.tools;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.BasicElectricItem;
import ic2.core.item.base.ItemElectricTool;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;

import java.util.Arrays;
import java.util.List;

public class ItemRelocator extends BasicElectricItem {

    public ItemRelocator() {
        this.maxCharge = 20000000;
        this.transferLimit = 1000;
        this.tier = 6;
        this.setRegistryName("relocator");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".relocator");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public int getTextureEntry(int i) {
        return 0;
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    public boolean canTakeDamage(ItemStack stack, int damage) {
        return ElectricItem.manager.canUse(stack, (double)(damage * 1000000));
    }

    public void damageItem(ItemStack stack, int damage, EntityPlayer player) {
        ElectricItem.manager.use(stack, (double)(damage * 1000000), player);
    }
}
