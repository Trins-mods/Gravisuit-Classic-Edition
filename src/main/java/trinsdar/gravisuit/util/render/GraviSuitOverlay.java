package trinsdar.gravisuit.util.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.electric.ElectricItem;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import trinsdar.gravisuit.items.armor.IGravitationJetpack;
import trinsdar.gravisuit.items.armor.IHasOverlay;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

import java.util.ArrayList;
import java.util.List;

public class GraviSuitOverlay implements IGuiOverlay {

	public static Minecraft mc;
	public static Font fontRenderer;

	static int offset = 3;
	static int yPos = offset;
	static int yPos1, yPos2, yPos3;
	static int lineHeight;

	public static List<Item> electricArmors = new ArrayList<>();
	public static List<Item> jetpacks = new ArrayList<>();

	public GraviSuitOverlay(Minecraft mc) {
		GraviSuitOverlay.mc = mc;
		fontRenderer = mc.font;
	}

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
		Player player = mc.player;
		assert player != null;
		ItemStack stackArmor = player.getItemBySlot(EquipmentSlot.CHEST);
		Item itemArmor = stackArmor.getItem();

		switch (GravisuitConfig.CLIENT.POSITIONS) {
			case TOPLEFT, TOPRIGHT -> yPos1 = yPos = offset;
			case BOTTOMLEFT, BOTTOMRIGHT -> {
				yPos = screenHeight - (fontRenderer.lineHeight + offset);
				yPos1 = screenHeight - ((fontRenderer.lineHeight * 3) + offset * 3);
				lineHeight = fontRenderer.lineHeight + offset;
			}
		}

		yPos2 = yPos1 + offset + fontRenderer.lineHeight;
		yPos3 = yPos2 + offset + fontRenderer.lineHeight;

		if (itemArmor instanceof IHasOverlay overlay && overlay.isEnabled(stackArmor)) {

			CompoundTag tag = overlay.getArmorNBT(stackArmor, true);

			if (itemArmor instanceof IC2ModularElectricArmor armor){
				IC2JetpackBase base = armor.getJetpack(stackArmor);
				if (base != null){
					itemArmor = base;
				}
			}

			// Energy stats starts

			int currentCharge = ElectricItem.MANAGER.getCharge(stackArmor);
			int maxCapacity = ElectricItem.MANAGER.getCapacity(stackArmor);
			int energyLevel = (int) Math.round((double) currentCharge / (double) maxCapacity * 100);

			/** ENERGY STATS GENERAL */
			String energyString = "message.info.energy";
			Component energyToDisplay = formatComplexMessage(ChatFormatting.YELLOW, energyString, getEnergyTextColor(energyLevel), energyLevel + "%");

			// Jetpack Engine Starts

			boolean isEngineOn = !tag.getBoolean("disabled");
			String engineStatus = isEngineOn ? "message.info.on" : "message.info.off";
			ChatFormatting engineStatusColor = isEngineOn ? ChatFormatting.GREEN : ChatFormatting.RED;

			/** ENGINE STATUS GENERAL */

			String engineString = "message.info.jetpack.engine";
			Component engineToDisplay = formatComplexMessage(ChatFormatting.YELLOW, engineString, engineStatusColor, engineStatus);

			// Hover Start

			String hoverModeS = getWorkStatus(stackArmor);
			ChatFormatting hoverModeC = getWorkStatusColor(stackArmor);

			/** HOVER STATUS GENERAL */

			String hoverString = "message.info.jetpack.hover";
			Component hoverToDisplay = formatComplexMessage(ChatFormatting.YELLOW, hoverString, hoverModeC, hoverModeS);

			// Gravi Engine Starts

			boolean isGraviEngineOn = tag.getBoolean("engine_on");
			String graviEngineStatus = isGraviEngineOn ? "message.info.on" : "message.info.off";
			ChatFormatting graviEngineStatusColor = isGraviEngineOn ? ChatFormatting.GREEN : ChatFormatting.RED;

			/** ENGINE STATUS GENERAL */

			String graviEngineString = "message.info.gravitation";
			Component graviEngineToDisplay = formatComplexMessage(ChatFormatting.AQUA, graviEngineString, graviEngineStatusColor, graviEngineStatus);

			if (electricArmors.contains(itemArmor)) {
				fontRenderer.drawShadow(poseStack, energyToDisplay, getXOffset(energyToDisplay.getString(), gui.getMinecraft().getWindow()), yPos, 0);
			}
			if (jetpacks.contains(itemArmor)) {
				fontRenderer.drawShadow(poseStack, energyToDisplay, getXOffset(energyToDisplay.getString(), gui.getMinecraft().getWindow()), yPos1, 0);
				fontRenderer.drawShadow(poseStack, engineToDisplay, getXOffset(engineToDisplay.getString(), gui.getMinecraft().getWindow()), yPos2, 0);
				fontRenderer.drawShadow(poseStack, hoverToDisplay, getXOffset(hoverToDisplay.getString(), gui.getMinecraft().getWindow()), yPos3, 0);
			}
			if (itemArmor instanceof IGravitationJetpack) {
				fontRenderer.drawShadow(poseStack, energyToDisplay, getXOffset(energyToDisplay.getString(), gui.getMinecraft().getWindow()), yPos1 - lineHeight, 0);
				fontRenderer.drawShadow(poseStack, engineToDisplay, getXOffset(engineToDisplay.getString(), gui.getMinecraft().getWindow()), yPos2 - lineHeight, 0);
				fontRenderer.drawShadow(poseStack, hoverToDisplay, getXOffset(hoverToDisplay.getString(), gui.getMinecraft().getWindow()), yPos3 - lineHeight, 0);
				fontRenderer.drawShadow(poseStack, graviEngineToDisplay, getXOffset(graviEngineToDisplay.getString(), gui.getMinecraft().getWindow()), yPos3, 0);
			}
		}
	}

	private static int getXOffset(String value, Window window) {
		return switch (GravisuitConfig.CLIENT.POSITIONS) {
			case TOPLEFT, BOTTOMLEFT -> offset;
			case TOPRIGHT, BOTTOMRIGHT -> window.getGuiScaledWidth() - offset - fontRenderer.width(value);
			case TOPMIDDLE -> (int) (window.getGuiScaledWidth() * 0.50F) - (fontRenderer.width(value) / 2);
		};
	}

	public static ChatFormatting getEnergyTextColor(double energyLevel) {
		if (energyLevel == 100) {
			return ChatFormatting.GREEN;
		}
		if ((energyLevel <= 100) && (energyLevel > 50)) {
			return ChatFormatting.GOLD;
		}
		if (energyLevel <= 50) {
			return ChatFormatting.RED;
		}
		return null;
	}

	public static MutableComponent formatSimpleMessage(ChatFormatting color, String text) {
		return Component.translatable(text).withStyle(color);
	}

	public static MutableComponent formatComplexMessage(ChatFormatting color1, String text1, ChatFormatting color2, String text2) {
		return formatSimpleMessage(color1, text1).append(formatSimpleMessage(color2, text2));
	}

	private static IC2JetpackBase.HoverMode getHoverStatus(ItemStack stack) {
		CompoundTag tag = stack.getItem() instanceof IC2ModularElectricArmor ? StackUtil.getNbtData(stack).getCompound("jetpack_data") : StackUtil.getNbtData(stack);
		return IC2JetpackBase.HoverMode.byIndex(tag.getByte("HoverMode"));
	}

	public static String getWorkStatus(ItemStack stack) {
		IC2JetpackBase.HoverMode mode = getHoverStatus(stack);

		if (mode == IC2JetpackBase.HoverMode.BASIC) {
			return "message.info.basic";
		} else if (mode == IC2JetpackBase.HoverMode.ADV) {
			return "message.info.adv";
		} else {
			return "message.info.off";
		}
	}

	// Returns hover status color
	public static ChatFormatting getWorkStatusColor(ItemStack stack) {
		IC2JetpackBase.HoverMode mode = getHoverStatus(stack);

		if (mode == IC2JetpackBase.HoverMode.BASIC) {
			return ChatFormatting.GREEN;
		} else if (mode == IC2JetpackBase.HoverMode.ADV) {
			return ChatFormatting.AQUA;
		} else {
			return ChatFormatting.RED;
		}
	}

	static {
		electricArmors.add(IC2Items.BAT_PACK);
		electricArmors.add(IC2Items.LAP_PACK);
		electricArmors.add(IC2Items.QUANTUM_PACK);
		electricArmors.add(IC2Items.NANOSUIT_CHESTPLATE);
		electricArmors.add(IC2Items.QUANTUM_SUIT_CHESTPLATE);
		electricArmors.add(Registry.ADVANCED_LAPPACK);
		electricArmors.add(Registry.ULTIMATE_LAPPACK);

		jetpacks.add(IC2Items.JETPACK_ELECTRIC);
		jetpacks.add(IC2Items.JETPACK_NUCLEAR);
		jetpacks.add(IC2Items.JETPACK_ELECTRIC_COMPACT);
		jetpacks.add(IC2Items.JETPACK_NUCLEAR_COMPACT);
		jetpacks.add(Registry.ADVANCED_ELECTRIC_JETPACK);
		jetpacks.add(Registry.ADVANCED_NUCLEAR_JETPACK);
	}
}
