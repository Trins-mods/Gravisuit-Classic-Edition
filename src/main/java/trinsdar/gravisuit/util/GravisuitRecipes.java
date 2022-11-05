package trinsdar.gravisuit.util;


import com.google.gson.JsonObject;
import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import trinsdar.gravisuit.GravisuitClassic;

public class GravisuitRecipes {

    public static void loadRecipes(IAdvancedCraftingManager registry){
        //Components
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "super_conductor_cover"), new ItemStack(Registry.SUPER_CONDUCTOR_COVER, 3), "AIA", "CCC", "AIA", 'A', IC2Items.PLATE_ADVANCED_ALLOY, 'I', IC2Items.PLATE_IRIDIUM, 'C', IC2Items.CARBON_PLATE);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "super_conductor"), new ItemStack(Registry.SUPER_CONDUCTOR, 3), "SSS", "GUG", "SSS", 'S', Registry.SUPER_CONDUCTOR_COVER, 'G', IC2Items.GLASSFIBER_CABLE, 'U', IC2Items.UUMATTER);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "cooling_core"), new ItemStack(Registry.COOLING_CORE), "CAC", "HIH", "CAC", 'C', IC2Items.COOLANT_CELL_60K, 'A', IC2Items.HEAT_EXCHANGER_ADVANCED, 'H', IC2Items.PLATING_HEAT_CAPACITY, 'I', IC2Items.PLATE_IRIDIUM);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "gravitation_engine"), new ItemStack(Registry.GRAVITATION_ENGINE), "TST", "CIC", "TST", 'T', IC2Blocks.TESLA_COIL, 'S', Registry.SUPER_CONDUCTOR, 'C', Registry.COOLING_CORE, 'I', IC2Blocks.TRANSFORMER_IV);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "magnetron"), new ItemStack(Registry.MAGNETRON), "ICI", "CSC", "ICI", 'I', IC2Tags.INGOT_REFINED_IRON, 'C', IC2Tags.INGOT_COPPER, 'S', Registry.SUPER_CONDUCTOR);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "vajra_core"), new ItemStack(Registry.VAJRA_CORE), " M ", "ITI", "StS", 'M', Registry.MAGNETRON, 'I', IC2Items.PLATE_IRIDIUM, 'T', IC2Blocks.TESLA_COIL, 'S', Registry.SUPER_CONDUCTOR, 't', IC2Blocks.TRANSFORMER_IV);
        //TODO circuit tags
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "engine_boost"), new ItemStack(Registry.ENGINE_BOOST), "GAG", "COC", "AHA", 'G', Tags.Items.DUSTS_GLOWSTONE, 'A', IC2Items.PLATE_ADVANCED_ALLOY, 'C', IC2Items.ADVANCED_CIRCUIT, 'O', IC2Items.OVERCLOCKER_UPGRADE, 'H', IC2Items.VENT_HEAT_ADVANCED);

        //Jetpacks
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "advanced_electric_jetpack"), new ItemStack(Registry.ADVANCED_ELECTRIC_JETPACK), "CEC", "BLB", "GAG", 'C', IC2Items.CARBON_PLATE, 'E', IC2Items.JETPACK_ELECTRIC, 'B', Registry.ENGINE_BOOST, 'L', Registry.ADVANCED_LAPPACK, 'G', IC2Items.GLASSFIBER_CABLE, 'A', IC2Items.ADVANCED_CIRCUIT);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "advanced_nuclear_jetpack"), new ItemStack(Registry.ADVANCED_NUCLEAR_JETPACK), "CEC", "BLB", "GAG", 'C', IC2Items.CARBON_PLATE, 'E', IC2Items.JETPACK_NUCLEAR, 'B', Registry.ENGINE_BOOST, 'L', Registry.ADVANCED_LAPPACK, 'G', IC2Items.GLASSFIBER_CABLE, 'A', IC2Items.ADVANCED_CIRCUIT);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "advanced_nuclear_jetpack_upgrade"), new ItemStack(Registry.ADVANCED_NUCLEAR_JETPACK), "CTC", "RNR", "CAC", 'C', IC2Items.ADVANCED_CIRCUIT, 'T', IC2Blocks.TRANSFORMER_EV, 'R', IC2Blocks.REACTOR_CHAMBER, 'N', IC2Blocks.NUCLEAR_REACTOR, 'A', Registry.ADVANCED_ELECTRIC_JETPACK);
        registry.addShapedIC2Recipe("compacted_nuclear_jetpack_1", new ItemStack(IC2Items.JETPACK_NUCLEAR_COMPACT), " B ", "XYX", "CVC", 'Y', Registry.ADVANCED_NUCLEAR_JETPACK, 'C', IC2Items.BAT_PACK, 'X', new ItemStack(IC2Items.ADVANCED_CIRCUIT, 4), 'V', Registry.ADVANCED_ELECTRIC_JETPACK, 'B', IC2Items.LAP_PACK);
        registry.addShapedIC2Recipe("compacted_electric_jetpack", new ItemStack(IC2Items.JETPACK_ELECTRIC_COMPACT), " C ", "XYX", "VBV", 'C', IC2Items.TRANSFORMER_UPGRADE, 'Y', IC2Items.BAT_PACK, 'X', new ItemStack(IC2Items.ADVANCED_CIRCUIT, 4), 'V', Registry.ADVANCED_ELECTRIC_JETPACK, 'B', IC2Items.LAP_PACK);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "gravitation_jetpack"), new ItemStack(Registry.GRAVITATION_JETPACK), "SJS", "GTG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'J', IC2Items.JETPACK_ELECTRIC_COMPACT, 'G', Registry.GRAVITATION_ENGINE, 'T', IC2Blocks.TRANSFORMER_EV, 'U', Registry.ULTIMATE_LAPPACK);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "nuclear_gravitation_jetpack"), new ItemStack(Registry.NUCLEAR_GRAVITATION_JETPACK), "SJS", "GTG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'J', IC2Items.JETPACK_NUCLEAR_COMPACT, 'G', Registry.GRAVITATION_ENGINE, 'T', IC2Blocks.TRANSFORMER_EV, 'U', Registry.ULTIMATE_LAPPACK);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "nuclear_gravitation_jetpack_upgrade"), new ItemStack(Registry.NUCLEAR_GRAVITATION_JETPACK), "CTC", "RNR", "CAC", 'C', IC2Items.ADVANCED_CIRCUIT, 'T', IC2Blocks.TRANSFORMER_EV, 'R', IC2Blocks.REACTOR_CHAMBER, 'N', IC2Blocks.NUCLEAR_REACTOR, 'A', Registry.GRAVITATION_JETPACK);

        //lappacks
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "ultimate_lappack"), new ItemStack(Registry.ULTIMATE_LAPPACK), "LIL", "LQL", "LSL", 'L', IC2Items.GLOWTRONIC_CRYSTAL, 'I', IC2Items.PLATE_IRIDIUM, 'Q', IC2Items.QUANTUM_PACK, 'S', Registry.SUPER_CONDUCTOR);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "advanced_lappack"), new ItemStack(Registry.ADVANCED_LAPPACK), "L", "A", "C", 'L', IC2Items.LAP_PACK, 'A', IC2Items.ADVANCED_CIRCUIT, 'C', IC2Items.LAPATRON_CRYSTAL);
        registry.addShapedIC2Recipe("quantum_pack", new ItemStack(IC2Items.QUANTUM_PACK), " X ", "YCY", " V ", 'Y', IC2Items.PLATE_IRIDIUM, 'X', IC2Items.ADVANCED_CIRCUIT, 'C', Registry.ADVANCED_LAPPACK, 'V', IC2Items.LAPATRON_CRYSTAL);

        //tools
        ItemStack importTreeTap = new ItemStack(Registry.GRAVITOOL);
        CompoundTag nbt_import = importTreeTap.getOrCreateTag();
        nbt_import.putBoolean("inv_import", true);
        registry.addShapelessRecipe(new ResourceLocation(GravisuitClassic.MODID, "gravitool_upgrade"), importTreeTap, Registry.GRAVITOOL, Items.HOPPER, IC2Items.IMPORT_UPGRADE_SIMPLE);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "gravitool"), new ItemStack(Registry.GRAVITOOL), "CHC", "AEA", "WaT", new TreetapModifier(), 'C', IC2Items.CARBON_PLATE, 'H', IC2Items.ELECTRIC_HOE, 'A', IC2Items.PLATE_ADVANCED_ALLOY, 'E', IC2Items.ENERGY_CRYSTAL, 'W', IC2Items.PRECISION_WRENCH, 'a', IC2Items.ADVANCED_CIRCUIT, 'T', IC2Items.ELECTRIC_TREETAP);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "vajra"), new ItemStack(Registry.VAJRA), "IMI", "DVC", "ALA", 'I', IC2Items.PLATE_IRIDIUM, 'M', IC2Items.MINING_LASER, 'D', IC2Items.DRILL_ADVANCED, 'V', Registry.VAJRA_CORE, 'C', IC2Items.CHAINSAW_ADVANCED, 'A', IC2Items.PLATE_ADVANCED_ALLOY, 'L', IC2Items.LAPATRON_CRYSTAL);
        registry.addShapedRecipe(new ResourceLocation(GravisuitClassic.MODID, "relocator"), new ItemStack(Registry.RELOCATOR), "MEM", "ETE", "MEM", 'M', IC2Items.MEMORY_STICK, 'E', Items.ENDER_PEARL, 'T', IC2Items.PORTABLE_TELEPORTER);
    }

    public static class TreetapModifier implements IRecipeModifier {
        ItemStack original;

        public TreetapModifier() {
        }

        public TreetapModifier(JsonObject obj) {
        }

        public TreetapModifier(FriendlyByteBuf buffer) {
        }

        public void reset() {
            this.original = null;
        }

        public boolean isSlotValid(ItemStack input) {
            if (input.getItem() == IC2Items.ELECTRIC_TREETAP) {
                if (this.original != null) {
                    return false;
                }

                this.original = input.copy();
            }

            return true;
        }

        public boolean isOutputItem(ItemStack input) {
            return false;
        }

        public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
            if (this.original != null) {
                CompoundTag nbt_import = this.original.getOrCreateTag();
                if (nbt_import.contains("inv_import") && nbt_import.getBoolean("inv_import")){
                    input.getOrCreateTag().putBoolean("inv_import", true);
                }
            }
            return input;
        }

        public void serialize(FriendlyByteBuf buffer) {
        }

        public JsonObject serialize() {
            return new JsonObject();
        }
    }

    /*static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;
    public static void init(){
        initShapedRecipes();
        initOverrideRecipes();
    }

    static IRecipeModifier treetap(ItemStack compare, String id){
        return new IRecipeModifier() {

            boolean tag = false;

            @Override
            public void clear() {
                tag = false;
            }

            @Override
            public boolean isStackValid(ItemStack provided) {
                if (StackUtil.isStackEqual(compare, provided)){
                    NBTTagCompound nbt = StackUtil.getNbtData(provided);
                    if (nbt.getBoolean(id)) {
                        tag = true;
                    }
                }

                return true;
            }

            @Override
            public ItemStack getOutput(ItemStack output, boolean forDisplay) {
                if (forDisplay) {
                    StackUtil.addToolTip(output, "Upgrades get transferred");
                } else {
                    NBTTagCompound nbt = StackUtil.getOrCreateNbtData(output);

                    if (tag){
                        nbt.setBoolean(id, true);
                    }
                }

                return output;
            }

            @Override
            public boolean isOutput(ItemStack possibleOutput) {
                return false;
            }
        };
    }

    static IRecipeModifier wrench( ItemStack provided){
        return new IRecipeModifier() {
            boolean tag = false;
            String id = "Import";
            @Override
            public void clear() {
                tag = false;
            }

            @Override
            public boolean isStackValid(ItemStack itemStack) {
                if (StackUtil.isStackEqual(provided, itemStack)){
                    NBTTagCompound nbt = StackUtil.getNbtData(itemStack);
                    return nbt.getBoolean("Lossless");
                }

                if (StackUtil.isStackEqual(Ic2Items.treeTap, itemStack)){
                    NBTTagCompound nbt = StackUtil.getNbtData(itemStack);
                    if (nbt.getBoolean(id)) {
                        tag = true;
                    }
                }

                return true;
            }

            @Override
            public ItemStack getOutput(ItemStack output, boolean forDisplay) {
                if (forDisplay) {
                    StackUtil.addToolTip(output, "Upgrades get transferred");
                } else {
                    NBTTagCompound nbt = StackUtil.getOrCreateNbtData(output);

                    if (tag){
                        nbt.setBoolean(id, true);
                    }
                }
                return output;
            }

            @Override
            public boolean isOutput(ItemStack itemStack) {
                return false;
            }
        };
    }

    public static void initShapedRecipes(){
        if (GravisuitConfig.enabledItems.enableMiscCraftingItems){
            recipes.addRecipe(new ItemStack(Registry.SUPER_CONDUCTOR_COVER, 3), "AIA", "CCC", "AIA", 'A', Ic2Items.advancedAlloy, 'I', Ic2Items.iridiumPlate, 'C', Ic2Items.carbonPlate);
            recipes.addRecipe(new ItemStack(Registry.SUPER_CONDUCTOR, 3), "SSS", "GUG", "SSS", 'S', Registry.SUPER_CONDUCTOR_COVER, 'G', Ic2Items.glassFiberCable, 'U', Ic2Items.uuMatter);
            recipes.addRecipe(new ItemStack(Registry.COOLING_CORE), "CAC", "HIH", "CAC", 'C', Ic2Items.reactorCoolantCellSix, 'A', Ic2Items.reactorHeatSwitchDiamond, 'H', Ic2Items.reactorPlatingHeat, 'I', Ic2Items.iridiumPlate);
            recipes.addRecipe(new ItemStack(Registry.GRAVITATION_ENGINE), "TST", "CIC", "TST", 'T', Ic2Items.teslaCoil, 'S', Registry.SUPER_CONDUCTOR, 'C', Registry.COOLING_CORE, 'I', Ic2Items.transformerIV);
            recipes.addRecipe(new ItemStack(Registry.MAGNETRON), "ICI", "CSC", "ICI", 'I', IC2.getRefinedIron(), 'C', "ingotCopper", 'S', Registry.SUPER_CONDUCTOR);
            recipes.addRecipe(new ItemStack(Registry.VAJRA_CORE), " M ", "ITI", "StS", 'M', Registry.MAGNETRON, 'I', Ic2Items.iridiumPlate, 'T', Ic2Items.teslaCoil, 'S', Registry.SUPER_CONDUCTOR, 't', Ic2Items.transformerIV);
            recipes.addRecipe(new ItemStack(Registry.ENGINE_BOOST), "GAG", "COC", "AHA", 'G', "dustGlowstone", 'A', Ic2Items.advancedAlloy, 'C', "circuitAdvanced", 'O', Ic2Items.overClockerUpgrade, 'H', Ic2Items.reactorVentDiamond);
        }
        if (GravisuitConfig.enabledItems.enableMiscCraftingItems && GravisuitConfig.enabledItems.enableUltimateLappack){
            recipes.addRecipe(new ItemStack(Registry.getUltimateLappack()), "LIL", "LQL", "LSL", 'L', Ic2Items.lapotronCrystal, 'I', Ic2Items.iridiumPlate, 'Q', Ic2Items.quantumPack, 'S', Registry.SUPER_CONDUCTOR);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedLappack()), "L", "A", "C", 'L', Ic2Items.lapPack, 'A', "circuitAdvanced", 'C', Ic2Items.lapotronCrystal);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedElectricJetpack && GravisuitConfig.enabledItems.enableMiscCraftingItems && GravisuitConfig.enabledItems.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedElectricJetpack()), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.electricJetpack, 'B', Registry.ENGINE_BOOST, 'L', Registry.getAdvancedLappack(), 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
        }
        if (GravisuitConfig.enabledItems.enableAdvancedNuclearJetpack && GravisuitConfig.enabledItems.enableMiscCraftingItems && GravisuitConfig.enabledItems.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedNuclearJetpack()), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.nuclearJetpack, 'B', Registry.ENGINE_BOOST, 'L', Registry.getAdvancedLappack(), 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
            if (GravisuitConfig.enabledItems.enableAdvancedElectricJetpack){
                recipes.addRecipe(new ItemStack(Registry.getAdvancedNuclearJetpack()), "CTC", "RNR", "CAC", 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.getAdvancedElectricJetpack());
            }
        }
        if (GravisuitConfig.enabledItems.enableMiscCraftingItems && GravisuitConfig.enabledItems.enableGravisuit && GravisuitConfig.enabledItems.enableAdvancedNanoChestplate && GravisuitConfig.enabledItems.enableUltimateLappack){
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GTG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'Q', Ic2Items.quantumJetplate, 'G', Registry.GRAVITATION_ENGINE, 'T', Ic2Items.transformerEV,   'U', Registry.getUltimateLappack());
            }else {
                recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GAG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'Q', Ic2Items.quantumJetplate, 'G', Registry.GRAVITATION_ENGINE, 'A', Registry.advancedNanoChestplate, 'U', Registry.getUltimateLappack());
            }
        }
        if (GravisuitConfig.enabledItems.enableMiscCraftingItems && GravisuitConfig.enabledItems.enableNuclearGravisuit && GravisuitConfig.enabledItems.enableAdvancedNuclearNanoChestplate && GravisuitConfig.enabledItems.enableUltimateLappack){
            if (GravisuitConfig.enabledItems.enableGravisuit){
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "CTC", "RNR", "CAC", 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.gravisuit);
            }
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "SQS", "GTG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'Q', Ic2Items.quantumNuclearJetplate, 'G', Registry.GRAVITATION_ENGINE, 'T', Ic2Items.transformerEV, 'U', Registry.getUltimateLappack());
            }else {
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "SQS", "GAG", "SUS", 'S', Registry.SUPER_CONDUCTOR, 'Q', Ic2Items.quantumNuclearJetplate, 'G', Registry.GRAVITATION_ENGINE, 'A', Registry.advancedNuclearNanoChestplate, 'U', Registry.getUltimateLappack());
            }
        }
        if (GravisuitConfig.enabledItems.enableGravitool){
            recipes.addShapelessRecipe(new ItemStack(Registry.GRAVITOOL), (new FlagModifierMetaLess(new ItemStack(Registry.GRAVITOOL), "Import", true)).setUsesInput(), Registry.GRAVITOOL, Blocks.HOPPER, Ic2Items.importBasicUpgrade.copy());
            ItemStack wrench = Loader.isModLoaded("ic2c_extras") && Ic2cExtrasCodeHelper.isOverridingLossy() ? Ic2Items.electricWrench : Ic2Items.precisionWrench;
            IRecipeModifier modifier = (Loader.isModLoaded("ic2c_extras") && Ic2cExtrasCodeHelper.isOverridingLossy()) || !GravisuitConfig.enableGravitoolRequiresLosslessPrecisionWrench ? treetap(Ic2Items.treeTap, "Import") : wrench(Ic2Items.precisionWrench);
            if (Loader.isModLoaded("gtc_expansion")){
                recipes.addRecipe(new ItemStack(Registry.GRAVITOOL), "HCS", "AEA", "WaT", modifier, 'C', StackUtil.copyWithSize(Ic2Items.carbonPlate, 2), 'S', GameRegistry.makeItemStack("gtc_expansion:electric_screwdriver", 0, 1, null), 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', wrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
            } else {
                recipes.addRecipe(new ItemStack(Registry.GRAVITOOL), "CHC", "AEA", "WaT", modifier, 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', wrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
            }
        }
        if (GravisuitConfig.enabledItems.enableAdvancedDrill){
            recipes.addRecipe(new ItemStack(Registry.advancedDiamondDrill), "ODO", "AOA", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.diamondDrill, 'A', "circuitAdvanced");
        }
        if (GravisuitConfig.enabledItems.enableAdvancedChainsaw){
            recipes.addRecipe(new ItemStack(Registry.advancedChainsaw), " d ", "ODO", "AOA", 'd', "gemDiamond", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.chainSaw, 'A', "circuitAdvanced");
        }
        if (GravisuitConfig.enabledItems.enableVajra && GravisuitConfig.enabledItems.enableAdvancedChainsaw && GravisuitConfig.enabledItems.enableAdvancedDrill && GravisuitConfig.enabledItems.enableMiscCraftingItems){
            recipes.addRecipe(new ItemStack(Registry.VAJRA), "IMI", "DVC", "ALA", 'I', Ic2Items.iridiumPlate, 'M', Ic2Items.miningLaser, 'D', Registry.advancedDiamondDrill, 'V', Registry.VAJRA_CORE, 'C', Registry.advancedChainsaw, 'A', Ic2Items.advancedAlloy, 'L', Ic2Items.lapotronCrystal);
        }
    }

    public static boolean areOverrideRecipesValid(){
        return GravisuitConfig.enableIc2JetpackRecipOverrides && GravisuitConfig.enableCompactedElectricJetpackOverride && GravisuitConfig.enableCompactedNuclearJetpackOverride && GravisuitConfig.enableQuantumJetplateOverride && GravisuitConfig.enableQuantumNuclearJetplateOverride;
    }

    public static void initOverrideRecipes(){
        if (GravisuitConfig.enabledItems.enableAdvancedLappack){
            recipes.overrideRecipe("shaped_item.itemarmorquantumpack_447958198", Ic2Items.quantumPack, " A ", "ILI", " l ", 'A', "circuitAdvanced", 'I', Ic2Items.iridiumPlate, 'L', Registry.getAdvancedLappack(), 'l', Ic2Items.lapotronCrystal);
        }
        if (areOverrideRecipesValid()){
            recipes.overrideRecipe("shaped_item.itemarmorcombinedjetpack_1604687096", Ic2Items.compactedElectricJetpack, " T ", "CBC", "JLJ", 'T', Ic2Items.transformerUpgrade, 'C', new RecipeInputOreDict("circuitAdvanced", 4), 'B', Ic2Items.batPack, 'J', Registry.getAdvancedElectricJetpack(), 'L', Ic2Items.lapPack);
            recipes.overrideRecipe("shaped_item.itemarmornuclearcombindedjetpack_983003988", Ic2Items.compactedNuclearJetpack, " L ", "CNC", "BJB", 'L', Ic2Items.lapPack, 'C', new RecipeInputOreDict("circuitAdvanced", 4), 'N', Registry.getAdvancedNuclearJetpack(), 'B', Ic2Items.batPack, 'J', Registry.getAdvancedElectricJetpack());
            recipes.overrideRecipe("shaped_item.itemquantumarmorjetpack_320254911", Ic2Items.quantumJetplate, "ACA", "ILI", "IAI", 'A', Ic2Items.advancedAlloy, 'C', Registry.advancedNanoChestplate, 'I', Ic2Items.iridiumPlate, 'L', Ic2Items.lapotronCrystal);
            recipes.overrideRecipe("shaped_item.itemquantumarmornuclearjetplate_695318402", Ic2Items.quantumNuclearJetplate, "ACA", "ILI", "IAI", 'A', Ic2Items.advancedAlloy, 'C', Registry.advancedNuclearNanoChestplate, 'I', Ic2Items.iridiumPlate, 'L', Ic2Items.lapotronCrystal);
            recipes.addRecipe(Ic2Items.quantumJetplate, "ICI", "cQc", "GLG", 'I', StackUtil.copyWithSize(Ic2Items.iridiumPlate, 2), 'C', Ic2Items.compactedElectricJetpack, 'c', "circuitAdvanced", 'Q', Ic2Items.quantumChest, 'G', Ic2Items.glassFiberCable, 'L', Ic2Items.lapotronCrystal);
            recipes.addRecipe(Ic2Items.quantumNuclearJetplate, "ICI", "cQc", "GLG", 'I', StackUtil.copyWithSize(Ic2Items.iridiumPlate, 2), 'C', Ic2Items.compactedNuclearJetpack, 'c', "circuitAdvanced", 'Q', Ic2Items.quantumChest, 'G', Ic2Items.glassFiberCable, 'L', Ic2Items.lapotronCrystal);
        }
    }*/
}
