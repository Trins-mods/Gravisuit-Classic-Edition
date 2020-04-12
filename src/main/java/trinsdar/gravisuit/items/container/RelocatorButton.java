package trinsdar.gravisuit.items.container;

import ic2.core.inventory.gui.buttons.IC2Button;

public class RelocatorButton extends IC2Button {

    int width;
    int height;

    public RelocatorButton(int buttonId, int x, int y, int widthIn, int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.width = widthIn;
        this.height = heightIn;
    }
}
