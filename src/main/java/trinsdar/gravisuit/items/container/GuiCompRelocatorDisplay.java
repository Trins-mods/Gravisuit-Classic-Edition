package trinsdar.gravisuit.items.container;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GuiCompRelocatorDisplay extends GuiWidget {
    List<GuiCompRelocatorBar> buttons;
    ItemStack relocator;
    Player player;
    InteractionHand hand;
    public GuiCompRelocatorDisplay(Player player, InteractionHand hand) {
        super(new Box2i(3, 3, 170, 110));
        this.relocator = player.getItemInHand(hand);
        this.player = player;
        this.hand = hand;
        buttons = new ArrayList<>();
    }

    @Override
    public void init(IC2Screen gui) {
        super.init(gui);
        reload();
    }

    private void reload(){
        CompoundTag nbt = StackUtil.getNbtData(relocator);
        buttons.clear();
        if (nbt.contains("Locations")){
            CompoundTag map = nbt.getCompound("Locations");
            if (map.size() > 0 && map.size() < 11){
                int i = 0;
                for (String name : map.getAllKeys()){
                    GuiCompRelocatorBar bar = new GuiCompRelocatorBar(hand, i, player, name, relocator);
                    bar.setOwner(gui);
                    buttons.add(bar);
                    i++;
                }
            }
        }
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.addAll(Arrays.asList(ActionRequest.GUI_INIT, ActionRequest.MOUSE_INPUT, ActionRequest.DRAW_BACKGROUND, ActionRequest.DRAW_FOREGROUND, ActionRequest.TOOLTIP));
    }

    @Override
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(g -> g.drawBackground(matrix, mouseX, mouseY, partialTicks));
    }

    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        buttons.forEach(g -> g.drawForeground(matrix, mouseX, mouseY));
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        boolean changed = buttons.stream().anyMatch(g -> g.onMouseClick(mouseX, mouseY, mouseButton));
        if (changed) reload();
        return changed;
    }
}
