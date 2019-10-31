package trinsdar.gravisuit.util.render;

import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.item.armor.base.ItemArmorJetpackBase.HoverMode;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.plugins.IBaublesPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitConfig.Client.Positions;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.baubles.BaublesLoader;

import java.util.Random;

public class GUIHandler extends Gui {
	
	public GUIHandler(Minecraft mc) {

		int offset = 3;
		int xPos = offset;
		int yPos1 = offset;
		ScaledResolution scaledResolution = new ScaledResolution(mc);
	    if (GravisuitConfig.client.positions == Positions.BOTTOMLEFT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
			yPos1 = scaledResolution.getScaledHeight() - ((mc.fontRenderer.FONT_HEIGHT * 2) + 5);

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

		if (!armorStack.isEmpty() && (itemArmor == Registry.getAdvancedLappack() || itemArmor == Registry.getUltimateLappack() ||
									itemArmor == Registry.getAdvancedElectricJetpack() || itemArmor == Registry.getAdvancedNuclearJetpack() || 
									itemArmor == Registry.advancedNanoChestplate || itemArmor == Registry.advancedNuclearNanoChestplate || 
									itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit || 
									itemArmor == Ic2Items.compactedElectricJetpack.getItem() || itemArmor == Ic2Items.compactedNuclearJetpack.getItem() ||
									itemArmor == Ic2Items.quantumJetplate.getItem() || itemArmor == Ic2Items.quantumNuclearJetplate.getItem()))
		{
			int currCharge = getCharge(armorStack);
			int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
			energyLevelString = energyLevelName + energyStatus;
			if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
				xPos = scaledResolution.getScaledWidth() - 3 - mc.fontRenderer.getStringWidth(energyLevelString + "%");
			}else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE){
				xPos = (int)(scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(energyLevelString + "%") / 2);
			}
			drawString(mc.fontRenderer, energyLevelString + "%",  xPos, yPos1, getEnergyTextColor(energyStatus));
			if (itemArmor != Registry.getAdvancedLappack() && itemArmor != Registry.getUltimateLappack()){
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
				if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
					xPos = (scaledResolution.getScaledWidth() - 3) - mc.fontRenderer.getStringWidth(statusString);
				} else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE){
					xPos = (int)(scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(statusString) / 2);
				}
				drawString(mc.fontRenderer, statusString,  xPos, yPos2, 5635925);
			}
		} else if (armorStack.isEmpty()){
			if (Loader.isModLoaded("baubles") && IC2.loader.getPlugin("baubles", IBaublesPlugin.class) != null){
				if (!BaublesLoader.getBaublesChestSlot(player).isEmpty()){
					armorStack = BaublesLoader.getBaublesChestSlot(player);
					itemArmor = armorStack.getItem();

					if ((itemArmor == Registry.getAdvancedLappack() || itemArmor == Registry.getUltimateLappack() ||
							itemArmor == Registry.getAdvancedElectricJetpack() || itemArmor == Registry.getAdvancedNuclearJetpack() ||
							itemArmor == Registry.advancedNanoChestplate || itemArmor == Registry.advancedNuclearNanoChestplate ||
							itemArmor == Registry.gravisuit || itemArmor == Registry.nuclearGravisuit ||
							itemArmor == Ic2Items.compactedElectricJetpack.getItem() || itemArmor == Ic2Items.compactedNuclearJetpack.getItem() ||
							itemArmor == Ic2Items.quantumJetplate.getItem() || itemArmor == Ic2Items.quantumNuclearJetplate.getItem()))
					{
						int currCharge = getCharge(armorStack);
						int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
						energyLevelString = energyLevelName + energyStatus;
						if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
							xPos = scaledResolution.getScaledWidth() - 3 - mc.fontRenderer.getStringWidth(energyLevelString + "%");
						}else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE){
							xPos = (int)(scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(energyLevelString + "%") / 2);
						}
						drawString(mc.fontRenderer, energyLevelString + "%",  xPos, yPos1, getEnergyTextColor(energyStatus));
						if (itemArmor != Registry.getAdvancedLappack() && itemArmor != Registry.getUltimateLappack()){
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
							if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
								xPos = (scaledResolution.getScaledWidth() - 3) - mc.fontRenderer.getStringWidth(statusString);
							} else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE){
								xPos = (int)(scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(statusString) / 2);
							}
							drawString(mc.fontRenderer, statusString,  xPos, yPos2, 5635925);
						}
					}
				}
			}
		}
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
