/*
package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import ic2.core.IC2;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.network.PacketRelocator;

import java.util.Arrays;
import java.util.List;

public class GuiCompRelocatorAdd extends GuiComponent {

    ItemStack relocator;
    EntityPlayer player;
    EnumHand hand;
    int y;
    private Box2D BOX;
    private GuiTextField textBox;

    public GuiCompRelocatorAdd(EnumHand hand, EntityPlayer player) {
        super(new Box2D(21, 18, 1, 11));
        BOX = new Box2D(21, 18, 18, 11);
        this.relocator = player.getHeldItem(hand);
        this.hand = hand;
        this.y = 18;
        this.player = player;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.GuiInit, ActionRequest.ButtonNotify, ActionRequest.ToolTip, ActionRequest.KeyPressed, ActionRequest.MouseClick, ActionRequest.PostDraw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiInit(GuiIC2 gui) {
        gui.registerButton((new RelocatorButton(2, bX(gui, 59), bY(gui, 43), 26, 13, true, false)));// -1
        gui.registerButton((new RelocatorButton(1, bX(gui, 91), bY(gui, 43), 26, 13, true, true)));// -64
        this.textBox = new GuiTextField(3, gui.getFont(), bX(gui,14), bY(gui, 19), 148, 16);
        this.textBox.setMaxStringLength(32500);
        this.textBox.setFocused(true);
    }

    String name;

    @Override
    @SideOnly(Side.CLIENT)
    public void onButtonClick(GuiIC2 gui, GuiButton button) {
        if (button.id == 2) {
            if (!Strings.isNullOrEmpty(textBox.getText())){
                NBTTagCompound nbt = StackUtil.getNbtData(relocator);
                int x = (int)player.posX;
                int y = (int)player.posY;
                int z = (int)player.posZ;
                int dimId = player.getEntityWorld().provider.getDimension();
                String name = textBox.getText();
                ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(x, y, z, dimId, name);
                NBTTagCompound map = nbt.getCompoundTag("Locations");
                boolean successful = false;
                if (map.getKeySet().size() < 10){
                    successful = true;
                }
                if (successful){
                    GravisuitClassic.network.sendToServer(new PacketRelocator(location, PacketRelocator.ADDDESTINATION, PacketRelocator.handToBool(hand)));
                    IC2.platform.messagePlayer(player, name + " added to teleport list");
                } else {
                    IC2.platform.messagePlayer(player, "Max teleport Locations already reached!");
                }
                player.closeScreen();

            }
        }
        if (button.id == 1) {
            textBox.setText("");
        }
    }

    @Override
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
    }

    @Override
    public boolean onKeyTyped(GuiIC2 gui, char keyTyped, int keyCode) {
        this.textBox.textboxKeyTyped(keyTyped, keyCode);
        return gui.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode);
    }


    @Override
    public boolean onMouseClick(GuiIC2 gui, int mouseX, int mouseY, int mouseButton) {
        this.textBox.mouseClicked(mouseX, mouseY, mouseButton);
        return false;
    }

    @Override
    public void postDraw(GuiIC2 gui, int mouseX, int mouseY) {
        textBox.drawTextBox();
    }

    private int bX(GuiIC2 gui, int position) {
        return gui.getXOffset() + position;
    }

    private int bY(GuiIC2 gui, int position) {
        return gui.getYOffset() + position;
    }

    public static boolean within(int value, int low, int high) {
        return (value >= low) && (value <= high);
    }
}
*/
