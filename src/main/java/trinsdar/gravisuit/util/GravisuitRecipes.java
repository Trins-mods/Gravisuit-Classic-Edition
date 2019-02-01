package trinsdar.gravisuit.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
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

        recipes.addRecipe(new ItemStack(Registry.advancedLappack), "L", "A", "C", 'L', Ic2Items.lapPack, 'A', "circuitAdvanced", 'C', Ic2Items.lapotronCrystal);
        recipes.addRecipe(new ItemStack(Registry.advancedElectricJetpack), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.electricJetpack, 'B', Registry.engineBoost, 'L', Registry.advancedLappack, 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GAG", "SqS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumJetplate, 'G', Registry.gravitationEngine, 'q', Ic2Items.quantumPack);
        recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Registry.advancedElectricJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.gravitool), "CHC", "AEA", "WaT", 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', Ic2Items.precisionWrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
        recipes.addRecipe(new ItemStack(Registry.advancedDiamondDrill), "ODO", "AOA", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.diamondDrill, 'A', "circuitAdvanced");
        recipes.addRecipe(new ItemStack(Registry.advancedChainsaw), " d ", "ODO", "AOA", 'd', "gemDiamond", 'O', StackUtil.copyWithSize(Ic2Items.overClockerUpgrade, 2), 'D', Ic2Items.chainSaw, 'A', "circuitAdvanced");
    }

    public static void initOverrideRecipes(){
        recipes.overrideRecipe("shaped_Quantum Pack", Ic2Items.quantumPack, " A ", "ILI", " l ", 'A', "circuitAdvanced", 'I', Ic2Items.iridiumPlate, 'L', Registry.advancedLappack, 'l', Ic2Items.lapotronCrystal);
    }
}
