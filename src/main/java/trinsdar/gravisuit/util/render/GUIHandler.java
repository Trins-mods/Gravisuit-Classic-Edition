package trinsdar.gravisuit.util.render;

import net.minecraft.client.MinecraftClient;

public class GUIHandler /*extends Gui*/ {
    //dummy constructor, TODO port the code
    public GUIHandler(MinecraftClient mc){}
	
	/*public GUIHandler(Minecraft mc) {

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

		if (!armorStack.isEmpty() && or(itemArmor,  Registry.getAdvancedLappack(), Registry.getUltimateLappack(), Registry.getAdvancedElectricJetpack(),
				Registry.getAdvancedNuclearJetpack(), Registry.advancedNanoChestplate, Registry.advancedNuclearNanoChestplate,
				Registry.gravisuit, Registry.nuclearGravisuit, Ic2Items.compactedElectricJetpack.getItem(), Ic2Items.compactedNuclearJetpack.getItem(),
				Ic2Items.quantumJetplate.getItem(), Ic2Items.quantumNuclearJetplate.getItem(), Ic2Items.lapPack.getItem(), Ic2Items.quantumPack.getItem())) {
			int currCharge = getCharge(armorStack);
			int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
			energyLevelString = energyLevelName + energyStatus;
			if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT){
				xPos = scaledResolution.getScaledWidth() - 3 - mc.fontRenderer.getStringWidth(energyLevelString + "%");
			}else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE){
				xPos = (int)(scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(energyLevelString + "%") / 2);
			}
			drawString(mc.fontRenderer, energyLevelString + "%",  xPos, yPos1, getEnergyTextColor(energyStatus));
			if (itemArmor != Registry.getAdvancedLappack() && itemArmor != Registry.getUltimateLappack() && itemArmor != Ic2Items.lapPack.getItem() && itemArmor != Ic2Items.quantumPack.getItem()){
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
		} else {
			if (Loader.isModLoaded("baubles") && IC2.loader.getPlugin("baubles", IBaublesPlugin.class) != null){
				armorStack = BaublesLoader.getBaublesChestSlot(player);
				itemArmor = armorStack.getItem();
				if (!armorStack.isEmpty() && or(itemArmor, Registry.getAdvancedLappack(), Registry.getUltimateLappack(), Registry.getAdvancedElectricJetpack(),
						Registry.getAdvancedNuclearJetpack(), Registry.advancedNanoChestplate, Registry.advancedNuclearNanoChestplate,
						Registry.gravisuit, Registry.nuclearGravisuit, Ic2Items.compactedElectricJetpack.getItem(), Ic2Items.compactedNuclearJetpack.getItem(),
						Ic2Items.quantumJetplate.getItem(), Ic2Items.quantumNuclearJetplate.getItem(), Ic2Items.lapPack.getItem(), Ic2Items.quantumPack.getItem())) {
					int currCharge = getCharge(armorStack);
					int energyStatus = (int) (currCharge / ((IElectricItem) itemArmor).getMaxCharge(armorStack) * 100);
					energyLevelString = energyLevelName + energyStatus;
					if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT) {
						xPos = scaledResolution.getScaledWidth() - 3 - mc.fontRenderer.getStringWidth(energyLevelString + "%");
					} else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE) {
						xPos = (int) (scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(energyLevelString + "%") / 2);
					}
					drawString(mc.fontRenderer, energyLevelString + "%", xPos, yPos1, getEnergyTextColor(energyStatus));
					if (itemArmor != Registry.getAdvancedLappack() && itemArmor != Registry.getUltimateLappack() && itemArmor != Ic2Items.lapPack.getItem() && itemArmor != Ic2Items.quantumPack.getItem()) {
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
						if (GravisuitConfig.client.positions == Positions.TOPRIGHT || GravisuitConfig.client.positions == Positions.BOTTOMRIGHT) {
							xPos = (scaledResolution.getScaledWidth() - 3) - mc.fontRenderer.getStringWidth(statusString);
						} else if (GravisuitConfig.client.positions == Positions.TOPMIDDLE) {
							xPos = (int) (scaledResolution.getScaledWidth() * 0.50F) - (mc.fontRenderer.getStringWidth(statusString) / 2);
						}
						drawString(mc.fontRenderer, statusString, xPos, yPos2, 5635925);
					}
				}
			}
		}
	}

	public boolean or(Item compare, Item... items){
		for (Item item : items){
			if (compare == item){
				return true;
			}
		}
		return false;
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
    }*/
	
}
