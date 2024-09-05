package trinsdar.gravisuit.items.tools;

import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemMagnet extends IC2ElectricItem implements ISimpleItemModel {
    public ItemMagnet() {
        super("magnet");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "magnet"), this);
    }

    @Override
    protected int getEnergyCost(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.MAGNET_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.MAGNET_TRANSFER.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("magnet");
    }
}
