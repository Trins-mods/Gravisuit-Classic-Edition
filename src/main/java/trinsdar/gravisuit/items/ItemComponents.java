package trinsdar.gravisuit.items;

import ic2.core.IC2;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;

import java.util.Arrays;
import java.util.List;

public class ItemComponents extends Item implements IStaticTexturedItem {
    public enum ItemComponentTypes {
        SUPER_CONDUCTOR_COVER(0),
        SUPER_CONDUCTOR(1),
        COOLING_CORE(2),
        GRAVITATION_ENGINE(3),
        MAGNETRON(4),
        VAJRA_CORE(5),
        ENGINE_BOOST(6);

        private int id;

        ItemComponentTypes(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    ItemComponentTypes variant;

    public ItemComponents(ItemComponentTypes variant) {
        this.variant = variant;
        setRegistryName(variant.toString().toLowerCase());
        setUnlocalizedName(GravisuitClassic.MODID + "." + variant.toString().toLowerCase());
        setCreativeTab(IC2.tabIC2);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[variant.getID()];
    }
}

