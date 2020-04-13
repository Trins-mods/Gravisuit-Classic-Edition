package trinsdar.gravisuit.items.container;

import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.GuiComponentContainer;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class GuiRelocator extends GuiComponentContainer {
    ContainerComponent container;
    private GuiTextField textBox;
    boolean add;
    public GuiRelocator(ContainerComponent container) {
        super(container);
        this.container = container;
    }

    @Override
    public void initGui() {
        this.textBox = new GuiTextField(3, this.fontRenderer, getXOffset() + 14, getYOffset() + 19, 148, 16);
        this.textBox.setMaxStringLength(32500);
        this.textBox.setFocused(true);
        super.initGui();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.textBox.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textBox.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public GuiTextField getTextBox() {
        return textBox;
    }
}
