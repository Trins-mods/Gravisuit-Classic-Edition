/*
package trinsdar.gravisuit.items.container;

import ic2.core.inventory.gui.buttons.IC2Button;
import net.minecraft.client.Minecraft;

public class RelocatorButton extends IC2Button {

    int width;
    int height;
    boolean add;
    boolean close;

    public RelocatorButton(int buttonId, int x, int y, int widthIn, int heightIn, boolean add, boolean close, String text) {
        super(buttonId, x, y, widthIn, heightIn, text);
        this.width = widthIn;
        this.height = heightIn;
        this.add = add;
        this.close = close;
    }

    public RelocatorButton(int buttonId, int x, int y, int widthIn, int heightIn, boolean add, boolean close) {
        this(buttonId, x, y, widthIn, heightIn, add, close, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        hovered = ((mouseX >= x) && (mouseY >= y) && (mouseX < x + width) && (mouseY < y + height));
        int i = getHoverState(hovered);
        if (add){
            if (i > 1){
                int textureX = close ? 208 : 176;
                drawTexturedModalRect(x, y, textureX, 0, 26, 13);
            }
        } else {
            if (i > 1){
                if (close){
                    drawTexturedModalRect(x - 160, y - 1, 0, 142, 170, 11);
                } else {
                    drawTexturedModalRect(x - 1, y - 1, 0, 129, 170, 11);
                }
            }
        }
    }
}
*/
