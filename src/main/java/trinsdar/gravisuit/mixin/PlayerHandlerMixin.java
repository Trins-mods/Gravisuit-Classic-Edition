package trinsdar.gravisuit.mixin;

import ic2.core.platform.player.PlayerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;

@Mixin(PlayerHandler.class)
public class PlayerHandlerMixin implements IGravisuitPlayerHandler {

    @Unique
    boolean isFlightKeyDown;

    @Inject(method = "onKeyChanged", at = @At("TAIL"), remap = false)
    private void injectOnKeyChanged(int key, CallbackInfo info){
        isFlightKeyDown = (key & 256) != 0;
    }

    @Override
    public boolean isFlightKeyDown() {
        return isFlightKeyDown;
    }
}
