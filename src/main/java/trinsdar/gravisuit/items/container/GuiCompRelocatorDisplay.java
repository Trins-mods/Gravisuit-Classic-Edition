package trinsdar.gravisuit.items.container;

import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.buttons.IC2Button;
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

public class GuiCompRelocatorDisplay extends GuiComponent {

    ItemStack relocator;
    EntityPlayer player;
    int y;
    String name;
    private Box2D BOX;

    public GuiCompRelocatorDisplay(ItemStack relocator, int y, EntityPlayer player, String name) {
        super(new Box2D(21, 3 + (y * 18), 1, 11));
        BOX = new Box2D(21, 3 + (y * 18), 18, 11);
        this.relocator = relocator;
        this.y = 3 + (y * 18);
        this.name = name;
        this.player = player;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.GuiInit, ActionRequest.ButtonNotify, ActionRequest.ToolTip);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiInit(GuiIC2 gui) {
        gui.registerButton((new IC2Button(2, bX(gui, 22), bY(gui, y + 1), 120, 9, "")));// -1
        gui.registerButton((new IC2Button(1, bX(gui, 145), bY(gui, y + 1), 9, 9, "")));// -64
    }

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
            NBTTagCompound nbt = StackUtil.getNbtData(this.relocator);
            nbt.setString("tempName", name);
            item.onButtonClick(this.relocator, 2, player);
            nbt.removeTag("tempName");
        }
        if (button.id == 1) {
            item.onButtonClick(this.relocator, 1, player);
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

        gui.drawTexturedModalRect(x + box.getX(), y + box.getY(), 0, 116, box.getLenght(),
                box.getHeight());
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
