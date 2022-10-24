package trinsdar.gravisuit.util;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import trinsdar.gravisuit.items.ItemComponents;
import trinsdar.gravisuit.items.armor.ItemAdvancedElectricJetpack;
import trinsdar.gravisuit.items.armor.ItemAdvancedLappack;
import trinsdar.gravisuit.items.armor.ItemAdvancedNuclearJetpack;
import trinsdar.gravisuit.items.armor.ItemGravitationJetpack;
import trinsdar.gravisuit.items.armor.ItemNuclearGravitationJetpack;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.items.tools.ItemToolVajra;

import java.util.Map;
public class Registry {
    public static final Map<ResourceLocation, Item> REGISTRY = new Object2ObjectArrayMap<>();
    public static final ItemAdvancedElectricJetpack ADVANCED_ELECTRIC_JETPACK = new ItemAdvancedElectricJetpack();
    public static final ItemAdvancedNuclearJetpack ADVANCED_NUCLEAR_JETPACK = new ItemAdvancedNuclearJetpack();
    public static final ElectricPackArmor ADVANCED_LAPPACK = new ItemAdvancedLappack("advanced_lappack", Rarity.UNCOMMON, 600000, 2, 500);
    public static final ElectricPackArmor ULTIMATE_LAPPACK = new ItemAdvancedLappack("ultimate_lappack", Rarity.EPIC, 10000000, 3, 4000);
    public static final ItemGravitationJetpack GRAVITATION_JETPACK = new ItemGravitationJetpack();
    public static final ItemNuclearGravitationJetpack NUCLEAR_GRAVITATION_JETPACK = new ItemNuclearGravitationJetpack();
    public static final ItemToolGravitool GRAVITOOL = new ItemToolGravitool();
    public static final ItemToolVajra VAJRA = new ItemToolVajra();
    public static final ItemRelocator RELOCATOR = new ItemRelocator();
    public static final ItemComponents SUPER_CONDUCTOR_COVER = new ItemComponents("super_conductor_cover");
    public static final ItemComponents SUPER_CONDUCTOR = new ItemComponents("super_conductor");
    public static final ItemComponents COOLING_CORE = new ItemComponents("cooling_core");
    public static final ItemComponents GRAVITATION_ENGINE = new ItemComponents("gravitation_engine");
    public static final ItemComponents MAGNETRON = new ItemComponents("magnetron");
    public static final ItemComponents VAJRA_CORE = new ItemComponents("vajra_core");
    public static final ItemComponents ENGINE_BOOST = new ItemComponents("engine_boost");

    public static void init(){
    }
}
