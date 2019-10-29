package trinsdar.gravisuit.util.render;

import ic2.api.item.IElectricItem;
import ic2.core.item.armor.base.ItemArmorJetpackBase.HoverMode;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import trinsdar.gravisuit.util.Registry;

public class GUIHandler extends Gui {
	
	public GUIHandler(Minecraft mc) {
		
	    String energyLevelString = "";
	    String statusString = "";
	    String hoverModeStatus = "";
	    String engineStatus = "";
	    String graviEngine = "";
		String energyLevelName = I18n.format("panelInfo.energyLevel") + ": ";
		
		EntityPlayer player = mc.player;
		ItemStack armorStack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		Item itemArmor = armorStack.getItem();
		
		if ((armorStack != null) && (itemArmor == Registry.getAdvancedLappack() || itemArmor == Registry.getUltimateLappack() ||
									itemArmor == Registry.getAdvancedElectricJetpack() || itemArmor == Registry.getAdvancedNuclearJetpack() || 
									itemArmor == Registry.advancedNanoChestplate || itemArmor == Registry.advancedNuclearNanoChestplate || 
									itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit)) 
		{
			int currCharge = getCharge(armorStack);
			int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
			energyLevelString = energyLevelName + energyStatus;
			drawString(mc.fontRenderer, energyLevelString + "%",  3, 3, getEnergyTextColor(energyStatus)); 
		}
		
		if ((armorStack != null) && (itemArmor == Registry.getAdvancedElectricJetpack() || itemArmor == Registry.getAdvancedNuclearJetpack() || 
									itemArmor == Registry.advancedNanoChestplate || itemArmor == Registry.advancedNuclearNanoChestplate || 
									itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit)) 
		{
			NBTTagCompound tag = StackUtil.getOrCreateNbtData(armorStack);
			if (tag.getBoolean("enabled")) {
				graviEngine = I18n.format("panelInfo.gravitationEngineOn");
				statusString = graviEngine; 
			}
			if (!tag.getBoolean("disabled") & !tag.getBoolean("enabled")) {
				engineStatus = I18n.format("panelInfo.jetpackEngineOn") + " ";
				HoverMode hoverMode = HoverMode.values()[tag.getByte("HoverMode")];
				statusString = engineStatus;
				if (hoverMode == HoverMode.Basic) {
					hoverModeStatus = "(" + I18n.format("panelInfo.jetpackHoverMode") + ")";
				} else if (hoverMode == HoverMode.Adv) {
					hoverModeStatus = "(" + I18n.format("panelInfo.jetpackExtremeHoverMode") + ")";
				}
				statusString = engineStatus + hoverModeStatus;
			} 
		}
			drawString(mc.fontRenderer, statusString,  3, 13, 5635925);
	}
		
	public static int getEnergyTextColor(int energyLevel) {
		if (energyLevel == 100) {
			return 5635925;
		}
		if ((energyLevel <= 100) && (energyLevel > 50)) {
			return 16755200;
		}
		if (energyLevel <= 50) {
			return 16733525;
		}
		return 0;
	}
	
    public static int getCharge(ItemStack stack) {
    	NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
    	int e = nbt.getInteger("charge");
    	return e;
    }
	
}
