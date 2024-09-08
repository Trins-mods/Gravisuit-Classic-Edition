package trinsdar.gravisuit.util;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.block.BlockEntityPlasmaPortal;
import trinsdar.gravisuit.block.BlockPlasmaPortal;
import trinsdar.gravisuit.entity.PlasmaBall;
import trinsdar.gravisuit.items.ItemComponents;
import trinsdar.gravisuit.items.armor.ItemAdvancedElectricJetpack;
import trinsdar.gravisuit.items.armor.ItemAdvancedLappack;
import trinsdar.gravisuit.items.armor.ItemAdvancedNuclearJetpack;
import trinsdar.gravisuit.items.armor.ItemGravitationJetpack;
import trinsdar.gravisuit.items.armor.ItemNuclearGravitationJetpack;
import trinsdar.gravisuit.items.tools.ItemMagnet;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.items.tools.ItemToolVajra;
import trinsdar.gravisuit.items.tools.ItemVoider;

import java.util.Map;
public class Registry {
    public static final Map<ResourceLocation, Item> REGISTRY = new Object2ObjectArrayMap<>();
    public static final ItemAdvancedElectricJetpack ADVANCED_ELECTRIC_JETPACK = new ItemAdvancedElectricJetpack();
    public static final ItemAdvancedNuclearJetpack ADVANCED_NUCLEAR_JETPACK = new ItemAdvancedNuclearJetpack();
    public static final ElectricPackArmor ADVANCED_LAPPACK = new ItemAdvancedLappack("advanced_lappack", Rarity.UNCOMMON, () -> GravisuitConfig.ADVANCED_LAPPACK_STORAGE.get(), 2, () -> GravisuitConfig.ADVANCED_LAPPACK_TRANSFER.get());
    public static final ElectricPackArmor ULTIMATE_LAPPACK = new ItemAdvancedLappack("ultimate_lappack", Rarity.EPIC, () -> GravisuitConfig.ULTIMATE_LAPPACK_STORAGE.get(), 3, () -> GravisuitConfig.ULTIMATE_LAPPACK_TRANSFER.get());
    public static final ItemGravitationJetpack GRAVITATION_JETPACK = new ItemGravitationJetpack();
    public static final ItemNuclearGravitationJetpack NUCLEAR_GRAVITATION_JETPACK = new ItemNuclearGravitationJetpack();
    public static final ItemToolGravitool GRAVITOOL = new ItemToolGravitool();
    public static final ItemToolVajra VAJRA = new ItemToolVajra();
    public static final ItemRelocator RELOCATOR = new ItemRelocator();
    public static final ItemMagnet MAGNET = new ItemMagnet();
    public static final ItemVoider VOIDER = new ItemVoider();
    public static final ItemComponents SUPER_CONDUCTOR_COVER = new ItemComponents("super_conductor_cover");
    public static final ItemComponents SUPER_CONDUCTOR = new ItemComponents("super_conductor");
    public static final ItemComponents COOLING_CORE = new ItemComponents("cooling_core");
    public static final ItemComponents GRAVITATION_ENGINE = new ItemComponents("gravitation_engine");
    public static final ItemComponents MAGNETRON = new ItemComponents("magnetron");
    public static final ItemComponents VAJRA_CORE = new ItemComponents("vajra_core");
    public static final ItemComponents ENGINE_BOOST = new ItemComponents("engine_boost");

    public static final EntityType<PlasmaBall> PLASMA_BALL_ENTITY_TYPE = EntityType.Builder.<PlasmaBall>of(PlasmaBall::new, MobCategory.MISC).sized(1.0f, 1.0f).setShouldReceiveVelocityUpdates(true).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(PlasmaBall::new).build(GravisuitClassic.MODID + ":plasma_ball");

    public static final Block PLASMA_PORTAL = new BlockPlasmaPortal();

    public static final BlockEntityType<?> PLASMA_PORTAL_BLOCK_ENTITY = BlockEntityType.Builder.of(BlockEntityPlasmaPortal::new, PLASMA_PORTAL).build(null);
    public static void init(){
    }

    public static ItemStack findStack(Item filter, Player player){
        if (ModList.get().isLoaded("curios")){
            ItemStack curios = CuriosUtil.getCuriosItem(filter, player);
            if (!curios.isEmpty()){
                return curios;
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot.getItem() == filter){
                return slot;
            }
        }
        return ItemStack.EMPTY;
    }
}
