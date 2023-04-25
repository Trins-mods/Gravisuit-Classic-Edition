package trinsdar.gravisuit.mixin;

import ic2.curioplugin.core.CurioPlugin;
import ic2.curioplugin.modules.TickingCurio;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

@Mixin(CurioPlugin.class)
public abstract class CurioPluginMixin {
	@Inject(method = "loadIMC", at = @At("TAIL"), remap = false)
	private void injectLoadIMC(InterModEnqueueEvent mod, CallbackInfo info) {
		InterModComms.sendTo(GravisuitClassic.MODID, "curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
	}

	@Inject(method = "createForItem", at = @At("TAIL"), remap = false, cancellable = true)
	private void injectCreateForItem(ItemStack stack, CallbackInfoReturnable<ICurio> info) {
		Item i = stack.getItem();

		if (i == Registry.ADVANCED_ELECTRIC_JETPACK || i == Registry.ADVANCED_NUCLEAR_JETPACK || i == Registry.GRAVITATION_JETPACK || i == Registry.NUCLEAR_GRAVITATION_JETPACK) info.setReturnValue(new TickingCurio(stack, false, true, true));
	}
}
