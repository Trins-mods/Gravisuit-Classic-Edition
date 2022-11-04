package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
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

import java.util.Arrays;
import java.util.Set;

public class GuiCompRelocatorAdd extends GuiWidget {

    ItemStack relocator;
    Player player;
    InteractionHand hand;
    int y;
    private Box2i BOX;
    private EditBox textBox;

    public GuiCompRelocatorAdd(InteractionHand hand, Player player) {
        super(new Box2i(59, 43, 58, 13));
        BOX = new Box2i(59, 43, 58, 13);
        this.relocator = player.getItemInHand(hand);
        this.hand = hand;
        this.y = 18;
        this.player = player;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.addAll(Arrays.asList(ActionRequest.GUI_INIT, ActionRequest.MOUSE_INPUT, ActionRequest.TOOLTIP, ActionRequest.KEY_INPUT, ActionRequest.DRAW_BACKGROUND));
    }

    @Override
    public void init(IC2Screen gui) {
        super.init(gui);
        this.textBox = new EditBox(gui.getFont(), bX(gui, 14), bY(gui, 19), 148, 16, Component.empty());
        this.textBox.setMaxLength(32500);
        gui.addRenderableWidget(textBox);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return super.isMouseOver(x, y);
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isWithinSetButton(mouseX, mouseY) && mouseButton == 0) {
            if (!Strings.isNullOrEmpty(textBox.getValue())) {
                CompoundTag nbt = StackUtil.getNbtData(relocator);
                long pos = new BlockPos(player.position()).asLong();
                String dimId = player.getLevel().dimension().location().toString();
                String name = textBox.getValue();
                ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(pos, dimId, name);
                CompoundTag map = nbt.getCompound("Locations");
                boolean successful = false;
                if (map.size() < 11) {
                    successful = true;
                }
                if (map.contains(name)){
                    player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorContainsTeleport, ChatFormatting.RED), false);
                    player.playSound(SoundEvents.UI_BUTTON_CLICK);
                    player.closeContainer();
                    return true;
                }
                if (successful) {
                    GravisuitClassic.NETWORK.sendToServer(new PacketRelocator(location, PacketRelocator.TeleportFunction.ADDDESTINATION, hand));
                    player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorAddTeleport, ChatFormatting.GREEN, Component.literal(name).withStyle(ChatFormatting.YELLOW)), false);
                } else {
                    player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorMaxTeleport, ChatFormatting.RED), false);
                }
                player.playSound(SoundEvents.UI_BUTTON_CLICK);
                player.closeContainer();
                return true;
            }
            player.playSound(SoundEvents.UI_BUTTON_CLICK);
        }
        if (isWithinClearButton(mouseX, mouseY) && mouseButton == 0){
            this.textBox.setValue("");
            player.playSound(SoundEvents.UI_BUTTON_CLICK);
        }
        return super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    private boolean isWithinSetButton(int mouseX, int mouseY){
        return within(mouseX, BOX.getX() + 1, BOX.getX() + 25) && within(mouseY, BOX.getY() + 1, BOX.getY() + 12);
    }

    private boolean isWithinClearButton(int mouseX, int mouseY){
        return within(mouseX, BOX.getX() + 33, BOX.getX() + 57) && within(mouseY, BOX.getY() + 1, BOX.getY() + 12);
    }

    @Override
    public boolean onKeyTyped(int keyCode) {
        int keyCompare = gui.getMinecraft().options.keyInventory.getKey().getValue();
        if (keyCompare == keyCode & textBox.isFocused()) {
            return true;
        }
        return super.onKeyTyped(keyCode);
    }

    @Override
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.isWithinSetButton(mouseX, mouseY)){
            gui.drawTextureRegion(matrix, bX(gui,BOX.getX() + 1), bY(gui,BOX.getY() + 1), 177, 1, 24, 11);
        }
        if (this.isWithinClearButton(mouseX, mouseY)){
            gui.drawTextureRegion(matrix, bX(gui,BOX.getX() + 33), bY(gui,BOX.getY() + 1), 209, 1, 24, 11);
        }
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
    }

    String name;

    /*@Override
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

    /*@Override
    public boolean onKeyTyped(GuiIC2 gui, char keyTyped, int keyCode) {
        this.textBox.textboxKeyTyped(keyTyped, keyCode);
        return gui.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode);
    }*/

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
