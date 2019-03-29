package trinsdar.gravisuit.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList.IRecipeModifier;
import ic2.core.IC2;
import ic2.core.item.recipe.upgrades.FlagModifier;
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
            recipes.addRecipe(new ItemStack(Registry.ultimateLappack), "LIL", "LQL", "LSL", 'L', Ic2Items.lapotronCrystal, 'I', Ic2Items.iridiumPlate, 'Q', Ic2Items.quantumPack, 'S', Registry.superConductor);
        }
        if (Config.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.advancedLappack), "L", "A", "C", 'L', Ic2Items.lapPack, 'A', "circuitAdvanced", 'C', Ic2Items.lapotronCrystal);
        }
        if (Config.enableAdvancedElectricJetpack && Config.enableMiscCraftingItems && Config.enableAdvancedLappack){
            recipes.addRecipe(new ItemStack(Registry.advancedElectricJetpack), "CEC", "BLB", "GAG", 'C', Ic2Items.carbonPlate, 'E', Ic2Items.electricJetpack, 'B', Registry.engineBoost, 'L', Registry.advancedLappack, 'G', Ic2Items.glassFiberCable, 'A', "circuitAdvanced");
        }
        if (Config.enableMiscCraftingItems && Config.enableGravisuit && Config.enableAdvancedNanoChestplate && Config.enableUltimateLappack){
            recipes.addRecipe(new ItemStack(Registry.gravisuit), "SQS", "GAG", "SUS", 'S', Registry.superConductor, 'Q', Ic2Items.quantumJetplate, 'G', Registry.gravitationEngine, 'A', Registry.advancedNanoChestplate, 'U', Registry.ultimateLappack);
        }
        if (Config.enableAdvancedNanoChestplate && Config.enableAdvancedElectricJetpack){
            recipes.addRecipe(new ItemStack(Registry.advancedNanoChestplate), "CAC", "CNC", "GcG", 'C', Ic2Items.carbonPlate, 'A', Registry.advancedElectricJetpack, 'N', Ic2Items.nanoChest, 'G', Ic2Items.glassFiberCable, 'c', "circuitAdvanced");
        }
        if (Config.enableGravitool){
            recipes.addRecipe(new ItemStack(Registry.gravitool), "CHC", "AEA", "WaT", wrench(Ic2Items.precisionWrench), 'C', Ic2Items.carbonPlate, 'H', Ic2Items.electricHoe, 'A', Ic2Items.advancedAlloy, 'E', Ic2Items.energyCrystal, 'W', Ic2Items.precisionWrench, 'a', "circuitAdvanced", 'T', Ic2Items.electricTreeTap);
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

    public static void initOverrideRecipes(){
        if (Config.enableAdvancedLappack){
            recipes.overrideRecipe("shaped_Quantum Pack", Ic2Items.quantumPack, " A ", "ILI", " l ", 'A', "circuitAdvanced", 'I', Ic2Items.iridiumPlate, 'L', Registry.advancedLappack, 'l', Ic2Items.lapotronCrystal);
        }
    }
}
