package trinsdar.gravisuit.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList.IRecipeModifier;
import ic2.core.IC2;
import ic2.core.item.recipe.entry.RecipeInputOreDict;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GravisuitRecipes {
    static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;
    public static void init(){
        initShapedRecipes();
        initOverrideRecipes();
    }

    static IRecipeModifier wrench( ItemStack provided){
        return new IRecipeModifier() {
            @Override
            public void clear() {

            }

            @Override
            public boolean isStackValid(ItemStack itemStack) {
                if (StackUtil.isStackEqual(provided, itemStack)){
                    NBTTagCompound nbt = StackUtil.getNbtData(itemStack);
                    return nbt.getBoolean("Lossless");
                }

                return true;
            }

            @Override
            public ItemStack getOutput(ItemStack itemStack, boolean b) {
                return new ItemStack(Registry.gravitool);
            }

            @Override
            public boolean isOutput(ItemStack itemStack) {
                return false;
            }
        };
    }

    public static void initShapedRecipes(){
        if (Config.enableMiscCraftingItems){
            recipes.addRecipe(new ItemStack(Registry.superConductorCover, 3), "AIA", "CCC", "AIA", 'A', Ic2Items.advancedAlloy, 'I', Ic2Items.iridiumPlate, 'C', Ic2Items.carbonPlate);
            recipes.addRecipe(new ItemStack(Registry.superConductor, 3), "SSS", "GUG", "SSS", 'S', Registry.superConductorCover, 'G', Ic2Items.glassFiberCable, 'U', Ic2Items.uuMatter);
            recipes.addRecipe(new ItemStack(Registry.coolingCore), "CAC", "HIH", "CAC", 'C', Ic2Items.reactorCoolantCellSix, 'A', Ic2Items.reactorHeatSwitchDiamond, 'H', Ic2Items.reactorPlatingHeat, 'I', Ic2Items.iridiumPlate);
            recipes.addRecipe(new ItemStack(Registry.gravitationEngine), "TST", "CIC", "TST", 'T', Ic2Items.teslaCoil, 'S', Registry.superConductor, 'C', Registry.coolingCore, 'I', Ic2Items.transformerIV);
            recipes.addRecipe(new ItemStack(Registry.magnetron), "ICI", "CSC", "ICI", 'I', IC2.getRefinedIron(), 'C', "ingotCopper", 'S', Registry.superConductor);
            recipes.addRecipe(new ItemStack(Registry.vajraCore), " M ", "ITI", "StS", 'M', Registry.magnetron, 'I', Ic2Items.iridiumPlate, 'T', Ic2Items.teslaCoil, 'S', Registry.superConductor, 't', Ic2Items.transformerIV);
            recipes.addRecipe(new ItemStack(Registry.engineBoost), "GAG", "COC", "AHA", 'G', "dustGlowstone", 'A', Ic2Items.advancedAlloy, 'C', "circuitAdvanced", 'O', Ic2Items.overClockerUpgrade, 'H', Ic2Items.reactorVentDiamond);
        }
        if (Config.enableMiscCraftingItems && Config.enableUltimateLappack){
            recipes.addRecipe(new ItemStack(Registry.getUltimateLappack()), "LIL", "LQL", "LSL", 'L', Ic2Items.lapotronCrystal, 'I', Ic2Items.iridiumPlate, 'Q', Ic2Items.quantumPack, 'S', Registry.superConductor);
        }
        if (Config.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedLappack()), "L", "A", "C", 'L', Ic2Items.lapPack, 'A', "circuitAdvanced", 'C', Ic2Items.lapotronCrystal);
        }
        if (Config.enableAdvancedElectricJetpack && Config.enableMiscCraftingItems && Config.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedElectricJetpack()), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.electricJetpack, 'B', Registry.engineBoost, 'L', Registry.getAdvancedLappack(), 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
        }
        if (Config.enableAdvancedNuclearJetpack && Config.enableMiscCraftingItems && Config.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.getAdvancedNuclearJetpack()), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.nuclearJetpack, 'B', Registry.engineBoost, 'L', Registry.getAdvancedLappack(), 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
            if (Config.enableAdvancedElectricJetpack){
                recipes.addRecipe(new ItemStack(Registry.getAdvancedNuclearJetpack()), "CTC", "RNR", "CAC", 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.getAdvancedElectricJetpack());
            }
        }
        if (Config.enableMiscCraftingItems && Config.enableGravisuit && Config.enableAdvancedNanoChestplate && Config.enableUltimateLappack){
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GTG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumJetplate, 'G', Registry.gravitationEngine, 'T', Ic2Items.transformerEV,   'U', Registry.getUltimateLappack());
            }else {
                recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GAG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumJetplate, 'G', Registry.gravitationEngine, 'A', Registry.advancedNanoChestplate, 'U', Registry.getUltimateLappack());
            }
        }
        if (Config.enableMiscCraftingItems && Config.enableNuclearGravisuit && Config.enableAdvancedNuclearNanoChestplate && Config.enableUltimateLappack){
            if (Config.enableGravisuit){
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "CTC", "RNR", "CAC", 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.gravisuit);
            }
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "SQS", "GTG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumNuclearJetplate, 'G', Registry.gravitationEngine, 'T', Ic2Items.transformerEV, 'U', Registry.getUltimateLappack());
            }else {
                recipes.addRecipe(new ItemStack(Registry.nuclearGravisuit), "SQS", "GAG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumNuclearJetplate, 'G', Registry.gravitationEngine, 'A', Registry.advancedNuclearNanoChestplate, 'U', Registry.getUltimateLappack());
            }
        }
        if (Config.enableAdvancedNanoChestplate && Config.enableAdvancedElectricJetpack){
            if (areOverrideRecipesValid()) {
                recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Ic2Items.compactedElectricJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }else {
                recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Registry.getAdvancedElectricJetpack(), 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }
        }
        if (Config.enableAdvancedNuclearNanoChestplate && Config.enableAdvancedNuclearJetpack){

            if (Config.enableAdvancedNanoChestplate){
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CTC", "RNR", "CAC", 'C', "circuitBasic", 'T', Ic2Items.transformerEV, 'R', Ic2Items.reactorChamber, 'N', Ic2Items.nuclearReactor, 'A', Registry.advancedNanoChestplate);
            }
            if (areOverrideRecipesValid()){
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Ic2Items.compactedNuclearJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }else {
                recipes.addRecipe(new ItemStack(Registry.advancedNuclearNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Registry.getAdvancedNuclearJetpack(), 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
            }

        }
        if (Config.enableGravitool){
            if (Config.enableGravitoolRequiresLosslessPrecisionWrench){
                recipes.addRecipe(new ItemStack(Registry.gravitool), "CHC", "AEA", "WaT", wrench(Ic2Items.precisionWrench), 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', Ic2Items.precisionWrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
            }else {
                recipes.addRecipe(new ItemStack(Registry.gravitool), "CHC", "AEA", "WaT", 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', Ic2Items.precisionWrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
            }

        }
        if (Config.enableAdvancedDrill){
            recipes.addRecipe(new ItemStack(Registry.advancedDiamondDrill), "ODO", "AOA", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.diamondDrill, 'A', "circuitAdvanced");
        }
        if (Config.enableAdvancedChainsaw){
            recipes.addRecipe(new ItemStack(Registry.advancedChainsaw), " d ", "ODO", "AOA", 'd', "gemDiamond", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.chainSaw, 'A', "circuitAdvanced");
        }
        if (Config.enableVajra && Config.enableAdvancedChainsaw && Config.enableAdvancedDrill && Config.enableMiscCraftingItems){
            recipes.addRecipe(new ItemStack(Registry.vajra), "IMI", "DVC", "ALA", 'I', Ic2Items.iridiumPlate, 'M', Ic2Items.miningLaser, 'D', Registry.advancedDiamondDrill, 'V', Registry.vajraCore, 'C', Registry.advancedChainsaw, 'A', Ic2Items.advancedAlloy, 'L', Ic2Items.lapotronCrystal);
        }

    }

    public static boolean areOverrideRecipesValid(){
        return Config.enableIc2JetpackRecipOverrides && Config.enableCompactedElectricJetpackOverride && Config.enableCompactedNuclearJetpackOverride && Config.enableQuantumJetplateOverride && Config.enableQuantumNuclearJetplateOverride;
    }

    public static void initOverrideRecipes(){
        if (Config.enableAdvancedLappack){
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
    }
}
