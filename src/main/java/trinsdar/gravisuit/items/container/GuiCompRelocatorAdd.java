package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import ic2.core.IC2;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.buttons.IconButton;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.util.math.Box2D;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
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
    private GuiTextField textBox;

    public GuiCompRelocatorAdd(ItemStack relocator, EntityPlayer player) {
        super(new Box2D(21, 18, 1, 11));
        BOX = new Box2D(21, 18, 18, 11);
        this.relocator = relocator;
        this.y = 18;
        this.player = player;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.GuiInit, ActionRequest.ButtonNotify, ActionRequest.ToolTip, ActionRequest.BackgroundDraw, ActionRequest.KeyPressed, ActionRequest.MouseClick, ActionRequest.PostDraw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiInit(GuiIC2 gui) {
        gui.registerButton((new IconButton(2, bX(gui, 59), bY(gui, 43), 26, 13).setIconOnly()));// -1
        gui.registerButton((new IconButton(1, bX(gui, 91), bY(gui, 43), 26, 13).setIconOnly()));// -64
        this.textBox = new GuiTextField(3, gui.getFont(), bX(gui,14), bY(gui, 19), 148, 16);
        this.textBox.setMaxStringLength(32500);
        this.textBox.setFocused(true);
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
            if (!Strings.isNullOrEmpty(textBox.getText())){
                NBTTagCompound nbt = StackUtil.getNbtData(relocator);
                NBTTagCompound compound = nbt.getCompoundTag("tempPosition");
                float x = compound.getFloat("x");
                float y = compound.getFloat("y");
                float z = compound.getFloat("z");
                int dimId = player.getEntityWorld().provider.getDimension();
                String name = textBox.getText();
                //item.onButtonClick(this.relocator, 3, player);
                if (!nbt.hasKey("map")){
                    nbt.setTag("map", new NBTTagCompound());
                }
                NBTTagCompound map = nbt.getCompoundTag("map");
                boolean successful;
                if (map.getKeySet().size() < 10){
                    NBTTagCompound teleportData = new NBTTagCompound();
                    if (!map.hasKey(name)){
                        teleportData.setFloat("x", x);
                        teleportData.setFloat("y", y);
                        teleportData.setFloat("z", z);
                        teleportData.setInteger("dimID", dimId);
                        map.setTag(name, teleportData);
                        successful = true;
                    }
                } else {
                    successful = false;
                }
                nbt.removeTag("tempPosition");
                relocator.setTagCompound(nbt);
                player.closeScreen();
                IC2.platform.messagePlayer(player, name + " added to teleport list");
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
