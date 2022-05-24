package trinsdar.gravisuit.util;

import ic2.jeiIntigration.SubModul;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime arg0) {
        // empty method for construction
    }
    @Override
    public void register(@Nonnull IModRegistry registry) {
        if (SubModul.load) {
            IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();

            if (!GravisuitConfig.enabledItems.enableAdvancedElectricJetpack){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.getAdvancedElectricJetpack()));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedNuclearJetpack){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.getAdvancedNuclearJetpack()));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedNanoChestplate){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.advancedNanoChestplate));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedNuclearNanoChestplate){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.advancedNuclearNanoChestplate));
            }
            if (!GravisuitConfig.enabledItems.enableGravisuit){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.gravisuit));
            }
            if (!GravisuitConfig.enabledItems.enableNuclearGravisuit){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.nuclearGravisuit));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedLappack){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.getAdvancedLappack()));
            }
            if (!GravisuitConfig.enabledItems.enableUltimateLappack){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.getUltimateLappack()));
            }
            if (!GravisuitConfig.enabledItems.enableGravitool){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.GRAVITOOL));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedDrill){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.advancedDiamondDrill));
            }
            if (!GravisuitConfig.enabledItems.enableAdvancedChainsaw){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.advancedChainsaw));
            }
            if (!GravisuitConfig.enabledItems.enableVajra){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.VAJRA));
            }
            if (!GravisuitConfig.enabledItems.enableMiscCraftingItems){
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.SUPER_CONDUCTOR_COVER));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.SUPER_CONDUCTOR));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.COOLING_CORE));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.GRAVITATION_ENGINE));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.MAGNETRON));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.VAJRA_CORE));
                blacklist.addIngredientToBlacklist(new ItemStack(Registry.ENGINE_BOOST));
            }

        }
    }
}
