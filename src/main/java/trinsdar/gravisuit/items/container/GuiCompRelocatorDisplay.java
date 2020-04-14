package trinsdar.gravisuit.items.container;

import ic2.core.IC2;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.util.math.Box2D;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.network.PacketRelocator;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class GuiCompRelocatorDisplay extends GuiComponent {

    ItemStack relocator;
    EntityPlayer player;
    int y;
    String name;
    private Box2D BOX;

    public GuiCompRelocatorDisplay(ItemStack relocator, int y, EntityPlayer player, String name) {
        super(new Box2D(3, 3 + (y * 18), 170, 11));
        BOX = new Box2D(3, 3 + (y * 18), 170, 11);
        this.relocator = relocator;
        this.y = 3 + (y * 18);
        this.name = name;
        this.player = player;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.GuiInit, ActionRequest.ButtonNotify, ActionRequest.ToolTip, ActionRequest.BackgroundDraw, ActionRequest.FrontgroundDraw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiInit(GuiIC2 gui) {
        gui.registerButton((new RelocatorButton(2, bX(gui, 4), bY(gui, y + 1), 156, 9, false, false)));// -1
        gui.registerButton((new RelocatorButton(1, bX(gui, 163), bY(gui, y + 1), 9, 9, false, true)));// -64
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
            NBTTagCompound nbt = StackUtil.getNbtData(relocator);
            int function = nbt.getByte("TeleportMode") == 0 ? PacketRelocator.TELEPORT : PacketRelocator.ADDDEFAULT;
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(name);
            GravisuitClassic.network.sendToServer(new PacketRelocator(location, function, relocator));
            if (function == PacketRelocator.TELEPORT) {
                player.closeScreen();
            } else {
                IC2.platform.messagePlayer(player, name + " set as default");
            }
        }
        if (button.id == 1) {
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(name);
            GravisuitClassic.network.sendToServer(new PacketRelocator(location, PacketRelocator.REMOVEDESTINATION, relocator));
            if (gui instanceof GuiRelocator){
                ((GuiRelocator)gui).setReloadGui(true);
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
        NBTTagCompound nbt = StackUtil.getNbtData(relocator);
        int textureY = 116;
        if (nbt.getString("DefaultLocation").equals(name)){
            textureY = 155;
        }
        NBTTagCompound map = nbt.getCompoundTag("Locations");
        if (map.hasKey(name)){
            gui.drawTexturedModalRect(x + box.getX(), y + box.getY(), 0, textureY, box.getLenght(),
                    box.getHeight());
        }
    }

    @Override
    public void drawFrontground(GuiIC2 gui, int mouseX, int mouseY) {
        int x = gui.getXOffset();
        int y = gui.getYOffset();
        Box2D box = this.getPosition();
        NBTTagCompound nbt = StackUtil.getNbtData(relocator);
        NBTTagCompound map = nbt.getCompoundTag("Locations");
        if (map.hasKey(name)){
            gui.drawString(name, 4, box.getY() + 2, Color.WHITE.getRGB());
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
