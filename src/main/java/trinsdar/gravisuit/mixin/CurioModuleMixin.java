package trinsdar.gravisuit.mixin;

import ic2.core.platform.registries.IC2Tags;
import ic2.curioplugin.CurioModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.Registry;

@Mixin(CurioModule.class)
public abstract class CurioModuleMixin {
	@Inject(method = "postInit", at = @At("TAIL"), remap = false)
	private void injectPostInit(CallbackInfo info) {
		IC2Tags.registerSimpleTag("curios", "back", Registry.ADVANCED_ELECTRIC_JETPACK, Registry.ADVANCED_NUCLEAR_JETPACK, Registry.GRAVITATION_JETPACK, Registry.NUCLEAR_GRAVITATION_JETPACK, Registry.ADVANCED_LAPPACK, Registry.ULTIMATE_LAPPACK);
	}
}
