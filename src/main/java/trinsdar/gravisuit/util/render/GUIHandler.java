package trinsdar.gravisuit.util.render;

import ic2.api.item.IElectricItem;
import ic2.core.item.armor.base.ItemArmorJetpackBase.HoverMode;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

import java.util.Random;

public class GUIHandler extends Gui {
	
	public GUIHandler(Minecraft mc) {

		int xPos;
		int yPos1;
		ScaledResolution scaledResolution = new ScaledResolution(mc);
	    if (GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.TOPLEFT || GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.BOTTOMLEFT){
	    	xPos = 3;
	    	if (GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.TOPLEFT){
	    		yPos1 = 3;
			} else {
	    		yPos1 = scaledResolution.getScaledHeight() - (mc.fontRenderer.FONT_HEIGHT * 3) + 5;
			}
		} else if (GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.TOPRIGHT || GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.BOTTOMRIGHT){
	    	xPos = (int)(scaledResolution.getScaledWidth() * 0.70F);
			if (GravisuitConfig.client.positions == GravisuitConfig.Client.Positions.TOPRIGHT){
				yPos1 = 3;
			} else {
				yPos1 = scaledResolution.getScaledHeight() - (mc.fontRenderer.FONT_HEIGHT * 3) + 5;
			}
		} else {
	    	yPos1 = 3;
	    	xPos = (int)(scaledResolution.getScaledWidth() * 0.40F);
		}

	    int yPos2 = yPos1 + mc.fontRenderer.FONT_HEIGHT + 2;
		
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
									itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit || 
									itemArmor == Ic2Items.compactedElectricJetpack.getItem() || itemArmor == Ic2Items.compactedNuclearJetpack.getItem() ||
									itemArmor == Ic2Items.quantumJetplate.getItem() || itemArmor == Ic2Items.quantumNuclearJetplate.getItem()))   
		{
			int currCharge = getCharge(armorStack);
			int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
			energyLevelString = energyLevelName + energyStatus;
			drawString(mc.fontRenderer, energyLevelString + "%",  xPos, yPos1, getEnergyTextColor(energyStatus)); 
		}
		
		if ((armorStack != null) && (itemArmor == Registry.getAdvancedElectricJetpack() || itemArmor == Registry.getAdvancedNuclearJetpack() || 
									itemArmor == Registry.advancedNanoChestplate || itemArmor == Registry.advancedNuclearNanoChestplate || 
									itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit || 
									itemArmor == Ic2Items.compactedElectricJetpack.getItem() || itemArmor == Ic2Items.compactedNuclearJetpack.getItem() || 
									itemArmor == Ic2Items.quantumJetplate.getItem() || itemArmor == Ic2Items.quantumNuclearJetplate.getItem())) 
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
			drawString(mc.fontRenderer, statusString,  xPos, yPos2, 5635925);
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
