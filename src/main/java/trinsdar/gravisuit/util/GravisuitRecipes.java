package trinsdar.gravisuit.util;


public class GravisuitRecipes {
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
        if (GravisuitConfig.enabledItems.enableAdvancedNanoChestplate && GravisuitConfig.enabledItems.enableAdvancedElectricJetpack){
            if (Loader.isModLoaded("ic2c_extras")){
                recipes.addShapelessRecipe(new ItemStack(Registry.advancedNanoChestplate), (new FlagModifier(new ItemStack(Registry.advancedNanoChestplate), "ReactorPlating", true)).setUsesInput(), new ItemStack(Registry.advancedNanoChestplate), Ic2Items.reactorPlating.copy());
            }
            if (areOverrideRecipesValid()) {
                recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", treetap(Ic2Items.nanoChest, "ReactorPlating"), 'C', Ic2Items.carbonPlate, 'A', Ic2Items.compactedElectricJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }else {
                recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", treetap(Ic2Items.nanoChest, "ReactorPlating"), 'C', Ic2Items.carbonPlate, 'A', Registry.getAdvancedElectricJetpack(), 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }
        }
        if (GravisuitConfig.enabledItems.enableAdvancedNuclearNanoChestplate && GravisuitConfig.enabledItems.enableAdvancedNuclearJetpack){
            if (Loader.isModLoaded("ic2c_extras")){
                recipes.addShapelessRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), (new FlagModifier(new ItemStack(Registry.advancedNuclearNanoChestplate), "ReactorPlating", true)).setUsesInput(), new ItemStack(Registry.advancedNuclearNanoChestplate), Ic2Items.reactorPlating.copy());
            }
            if (GravisuitConfig.enabledItems.enableAdvancedNanoChestplate){
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CTC", "RNR", "CAC", treetap(new ItemStack(Registry.advancedNanoChestplate), "ReactorPlating"), 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.advancedNanoChestplate);
            }
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CAC", "CNC", "GcG", treetap(Ic2Items.nanoChest, "ReactorPlating"), 'C', Ic2Items.carbonPlate, 'A', Ic2Items.compactedNuclearJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }else {
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CAC", "CNC", "GcG", treetap(Ic2Items.nanoChest, "ReactorPlating"), 'C', Ic2Items.carbonPlate, 'A', Registry.getAdvancedNuclearJetpack(), 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
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
