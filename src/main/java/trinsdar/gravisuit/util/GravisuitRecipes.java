package trinsdar.gravisuit.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.core.IC2;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import net.minecraft.item.ItemStack;

public class GravisuitRecipes {
    static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;
    public static void init(){
        initShapedRecipes();
        initOverrideRecipes();
    }

    public static void initShapedRecipes(){
        recipes.addRecipe(new ItemStack(Registry.superConductorCover), "AIA", "CCC", "AIA", 'A', Ic2Items.advancedAlloy, 'I', Ic2Items.iridiumPlate, 'C', Ic2Items.carbonPlate);
        recipes.addRecipe(new ItemStack(Registry.superConductor, 3), "SSS", "GUG", "SSS", 'S', Registry.superConductorCover, 'G', Ic2Items.glassFiberCable, 'U', Ic2Items.uuMatter);
        recipes.addRecipe(new ItemStack(Registry.coolingCore), "CAC", "HIH", "CAC", 'C', Ic2Items.reactorCoolantCellSix, 'A', Ic2Items.reactorHeatSwitchDiamond, 'H', Ic2Items.reactorPlatingHeat, 'I', Ic2Items.iridiumPlate);
        recipes.addRecipe(new ItemStack(Registry.gravitationEngine), "TST", "CIC", "TST", 'T', Ic2Items.teslaCoil, 'S', Registry.superConductor, 'C', Registry.coolingCore, 'I', Ic2Items.transformerIV);
        recipes.addRecipe(new ItemStack(Registry.magnetron), "ICI", "CSC", "ICI", 'I', IC2.getRefinedIron(), 'C', "ingotCopper", 'S', Registry.superConductor);
        recipes.addRecipe(new ItemStack(Registry.vajraCore), " M ", "ITI", "StS", 'M', Registry.magnetron, 'I', Ic2Items.iridiumPlate, 'T', Ic2Items.teslaCoil, 'S', Registry.superConductor, 't', Ic2Items.transformerIV);
        recipes.addRecipe(new ItemStack(Registry.engineBoost), "GAG", "COC", "AHA", 'G', "dustGlowstone", 'A', Ic2Items.advancedAlloy, 'C', "circuitAdvanced", 'O', Ic2Items.overClockerUpgrade, 'H', Ic2Items.reactorVentDiamond);

        recipes.addRecipe(new ItemStack(Registry.ultimateLappack), "LIL", "LQL", "LSL", 'L', Ic2Items.lapotronCrystal, 'I', Ic2Items.iridiumPlate, 'Q', Ic2Items.quantumPack, 'S', Registry.superConductor);
        recipes.addRecipe(new ItemStack(Registry.advancedLappack), "L", "A", "C", 'L', Ic2Items.lapPack, 'A', "circuitAdvanced", 'C', Ic2Items.lapotronCrystal);
        recipes.addRecipe(new ItemStack(Registry.advancedElectricJetpack), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.electricJetpack, 'B', Registry.engineBoost, 'L', Registry.advancedLappack, 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GAG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumJetplate, 'G', Registry.gravitationEngine, 'A', Registry.advancedElectricJetpack, 'U', Registry.ultimateLappack);
        recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Registry.advancedElectricJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
        //recipes.addRecipe(new ItemStack(Registry.gravitool), "CHC", "AEA", "WaT", 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', Ic2Items.precisionWrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
        recipes.addRecipe(new ItemStack(Registry.advancedDiamondDrill), "ODO", "AOA", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.diamondDrill, 'A', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.advancedChainsaw), " d ", "ODO", "AOA", 'd', "gemDiamond", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.chainSaw, 'A', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.vajra), "IMI", "DVC", "ALA", 'I', Ic2Items.iridiumPlate, 'M', Ic2Items.miningLaser, 'D', Registry.advancedDiamondDrill, 'V', Registry.vajraCore, 'C', Registry.advancedChainsaw, 'A', Ic2Items.advancedAlloy, 'L', Ic2Items.lapotronCrystal);
    }

    public static void initOverrideRecipes(){
        recipes.overrideRecipe("shaped_Quantum Pack", Ic2Items.quantumPack, " A ", "ILI", " l ", 'A', "circuitAdvanced", 'I', Ic2Items.iridiumPlate, 'L', Registry.advancedLappack, 'l', Ic2Items.lapotronCrystal);
        recipes.overrideRecipe("shaped_QuantumSuit Bodyarmor", Ic2Items.quantumChest, "ANA", "ILI", "IAI", 'A', Ic2Items.advancedAlloy, 'N', Registry.advancedNanoChestplate, 'I', Ic2Items.iridiumPlate, 'L', Ic2Items.lapotronCrystal);
    }
}
