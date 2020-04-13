package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.buttons.IconButton;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.util.math.Box2D;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.items.tools.ItemRelocator;

import java.util.Arrays;
import java.util.List;

public class GuiCompRelocatorAdd extends GuiComponent {

    ItemStack relocator;
    EntityPlayer player;
    int y;
    private Box2D BOX;

    public GuiCompRelocatorAdd(ItemStack relocator, EntityPlayer player) {
        super(new Box2D(21, 18, 1, 11));
        BOX = new Box2D(21, 18, 18, 11);
        this.relocator = relocator;
        this.y = 18;
        this.player = player;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.GuiInit, ActionRequest.ButtonNotify, ActionRequest.ToolTip, ActionRequest.BackgroundDraw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiInit(GuiIC2 gui) {
        gui.registerButton((new IconButton(2, bX(gui, 59), bY(gui, 43), 26, 13).setIconOnly()));// -1
        gui.registerButton((new IconButton(1, bX(gui, 91), bY(gui, 43), 26, 13).setIconOnly()));// -64
    }

    String name;

    @Override
    @SideOnly(Side.CLIENT)
    public void onButtonClick(GuiIC2 gui, GuiButton button) {
        ItemRelocator item;
        if (relocator.getItem() instanceof ItemRelocator){
            item = (ItemRelocator)relocator.getItem();
        } else {
            return;
        }
        if (button.id == 2) {
            if (!Strings.isNullOrEmpty(name)){
                NBTTagCompound nbt = StackUtil.getNbtData(relocator);
                nbt.getCompoundTag("tempPosition").setString("name", name);
                item.onButtonClick(this.relocator, 3, player);
            }
        }
        if (button.id == 1) {
            if (gui instanceof GuiRelocator){
                ((GuiRelocator)gui).getTextBox().setText("");
            }
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
    public void drawBackground(GuiIC2 gui, int mouseX, int mouseY, float particalTicks) {
        int x = gui.getXOffset();
        int y = gui.getYOffset();
        Box2D box = this.getPosition();
        if (this.isMouseOver(mouseX, mouseY) && within(mouseY, 43, 55)){
            if (within(mouseX, 59, 85)){
                gui.drawTexturedModalRect(x + 60, y + 44, 177, 1, 24,
                        11);
            }
            if (within(mouseX, 91, 117)){
                gui.drawTexturedModalRect(x + 92, y + 44, 209, 1, 24,
                        11);
            }
        }
    }

    @Override
    public void onGuiTick(GuiIC2 gui) {
        if (gui instanceof GuiRelocator){
            name = ((GuiRelocator)gui).getTextBox().getText();
        }
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
