package trinsdar.gravisuit.items.container;

import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.buttons.IC2Button;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.util.math.Box2D;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
        gui.registerButton((new IC2Button(2, bX(gui, 59), bY(gui, 43), 26, 13, "")));// -1
        gui.registerButton((new IC2Button(1, bX(gui, 91), bY(gui, 43), 26, 13, "")));// -64
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
            item.onButtonClick(this.relocator, 3, player);
        }
        if (button.id == 1) {
            item.onButtonClick(this.relocator, 0, player);
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

    @Override
    public void drawFrontground(GuiIC2 gui, int mouseX, int mouseY) {
        super.drawFrontground(gui, mouseX, mouseY);
    }

    @Override
    public boolean onKeyTyped(GuiIC2 gui, char keyTyped, int keyCode) {
        return super.onKeyTyped(gui, keyTyped, keyCode);
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
