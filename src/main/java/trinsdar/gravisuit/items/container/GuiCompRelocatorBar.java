package trinsdar.gravisuit.items.container;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.util.GravisuitLang;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GuiCompRelocatorBar extends GuiWidget {

    ItemStack relocator;
    Player player;
    int y;
    String name;
    InteractionHand hand;
    private final Box2i TEXT_BUTTON, DELETE_BUTTON;

    public GuiCompRelocatorBar(InteractionHand hand, int y, Player player, String name, ItemStack relocator) {
        super(new Box2i(3, 3 + (y * 11), 170, 11));
        TEXT_BUTTON = new Box2i(4, 4 + (y * 11), 156, 9);
        DELETE_BUTTON =  new Box2i(163, 4 + (y * 11), 9, 9);
        this.relocator = relocator;
        this.hand = hand;
        this.y = 3 + (y * 18);
        this.name = name;
        this.player = player;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.addAll(Arrays.asList(ActionRequest.TOOLTIP, ActionRequest.DRAW_BACKGROUND, ActionRequest.MOUSE_INPUT, ActionRequest.DRAW_FOREGROUND));
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) return false;
        if (TEXT_BUTTON.isInBox(mouseX, mouseY)){
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(name);
            CompoundTag nbt = StackUtil.getNbtData(relocator);
            CompoundTag map = nbt.getCompound("Locations");
            boolean successful = false;
            boolean close = false;
            if (map.contains(name)){
                successful = true;
                CompoundTag entry = map.getCompound(name);
                location = new ItemRelocator.TeleportData(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"), entry.getString("Dimension"), name);
            }
            if (successful) {
                boolean shift = Screen.hasShiftDown();
                if (shift) {
                    GravisuitClassic.NETWORK.sendToServer(new PacketRelocator(location, PacketRelocator.TeleportFunction.ADDDEFAULT, hand));
                    player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorAddDefaultTeleport, ChatFormatting.GREEN, name), false);
                    nbt.putString("DefaultLocation", name);
                } else {
                    GravisuitClassic.NETWORK.sendToServer(new PacketRelocator(location, PacketRelocator.TeleportFunction.TELEPORT, hand));
                    close = true;
                }
            } else {
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorNotContainsTeleport, ChatFormatting.RED), false);
            }
            player.playSound(SoundEvents.UI_BUTTON_CLICK);
            if (close) player.closeContainer();
            return successful;
        }
        if (DELETE_BUTTON.isInBox(mouseX, mouseY)){
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(name);
            CompoundTag nbt = StackUtil.getNbtData(relocator);
            CompoundTag map = nbt.getCompound("Locations");
            boolean successful = map.contains(name);
            if (successful) {
                GravisuitClassic.NETWORK.sendToServer(new PacketRelocator(location, PacketRelocator.TeleportFunction.REMOVEDESTINATION, hand));
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorRemoveTeleport, ChatFormatting.GREEN, name), false);
                map.remove(name);
            } else {
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorNotContainsTeleport, ChatFormatting.RED), false);
            }
            player.playSound(SoundEvents.UI_BUTTON_CLICK);
            return successful;
        }
        return super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void onToolTipCollecting(GuiIC2 gui, int mouseX, int mouseY, List<String> tooltips) {
        if (this.isMouseOver(mouseX, mouseY)) {
            if (within(mouseY, 40, 56)) {
                //tooltips.add(I18n.format(GTLang.BUTTON_AESU_SUB1));
            }
            if (within(mouseY, 57, 72)) {
                //tooltips.add(I18n.format(GTLang.BUTTON_AESU_SUB64));
            }
        }
    }*/

    @Override
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        CompoundTag nbt = StackUtil.getNbtData(relocator);
        int textureY = 116;
        if (nbt.contains("DefaultLocation") && nbt.getString("DefaultLocation").equals(name)){
            textureY = 155;
        }
        if (TEXT_BUTTON.isInBox(mouseX, mouseY)){
            textureY = 129;
        }
        if (DELETE_BUTTON.isInBox(mouseX, mouseY)){
            textureY = 142;
        }
        CompoundTag map = nbt.getCompound("Locations");
        if (map.contains(name)){
            gui.drawTextureRegion(matrix, x + box.getX(), y + box.getY(), 0, textureY, box.getWidth(), box.getHeight());
        }
    }

    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        CompoundTag nbt = StackUtil.getNbtData(relocator);
        CompoundTag map = nbt.getCompound("Locations");
        if (map.contains(name)){
            gui.drawString(matrix, Component.literal(name), 4, this.box.getY() + 2, Color.WHITE.getRGB());
        }
    }

    private int bX(IC2Screen gui, int position) {
        return gui.getGuiLeft() + position;
    }

    private int bY(IC2Screen gui, int position) {
        return gui.getGuiTop() + position;
    }

    public static boolean within(int value, int low, int high) {
        return (value >= low) && (value <= high);
    }
}
