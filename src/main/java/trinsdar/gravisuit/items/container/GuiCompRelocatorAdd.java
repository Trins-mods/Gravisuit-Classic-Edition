package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GuiCompRelocatorAdd extends GuiWidget {

    ItemStack relocator;
    Player player;
    InteractionHand hand;
    int y;
    private Box2i BOX;
    private EditBox textBox;

    public GuiCompRelocatorAdd(InteractionHand hand, Player player) {
        super(new Box2i(21, 18, 1, 11));
        BOX = new Box2i(21, 18, 18, 11);
        this.relocator = player.getItemInHand(hand);
        this.hand = hand;
        this.y = 18;
        this.player = player;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.addAll(Arrays.asList(ActionRequest.GUI_INIT, ActionRequest.MOUSE_INPUT, ActionRequest.TOOLTIP, ActionRequest.KEY_INPUT, ActionRequest.DRAW_POST));
    }

    @Override
    public void init(IC2Screen gui) {
        super.init(gui);
        this.textBox = new EditBox(gui.getFont(), bX(gui, 14), bY(gui, 19), 148, 16, Component.empty());
        this.textBox.setMaxLength(32500);
        this.textBox.setFocus(true);
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!Strings.isNullOrEmpty(textBox.getValue())){
            CompoundTag nbt = StackUtil.getNbtData(relocator);
            int x = (int)player.getX();
            int y = (int)player.getY();
            int z = (int)player.getZ();
            String dimId = player.getLevel().dimension().location().toString();
            String name = textBox.getValue();
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(x, y, z, dimId, name);
            CompoundTag map = nbt.getCompound("Locations");
            boolean successful = false;
            if (map.size() < 10){
                successful = true;
            }
            if (successful){
                //GravisuitClassic.NETWORK.sendToServer(new PacketRelocator(location, PacketRelocator.TeleportFunction.ADDDESTINATION, hand));
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorAddTeleport, ChatFormatting.GREEN, name), false);
            } else {
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorMaxTeleport, ChatFormatting.RED), false);
            }
            player.closeContainer();
            return false;

        }
        return super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    private boolean isWithinSetButton(int mouseX, int mouseY){
        return mouseX >= bX(gui, 60) && mouseX <= bX(gui, 84) && mouseY >= bY(gui, 44) && mouseY <= bY(gui, 55);
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

    @Override
    public boolean onKeyTyped(int keyCode) {
        //this.textBox.k
        return super.onKeyTyped(keyCode);
    }

    /*@Override
    public boolean onKeyTyped(GuiIC2 gui, char keyTyped, int keyCode) {
        this.textBox.textboxKeyTyped(keyTyped, keyCode);
        return gui.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode);
    }*/

    @Override
    public void postDraw(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.postDraw(matrix, mouseX, mouseY, partialTicks);
        this.textBox.render(matrix, mouseX, mouseY, partialTicks);
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
