package trinsdar.gravisuit.mixin;

import ic2.core.platform.player.KeyboardClient;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.GravisuitKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(KeyboardClient.class)
public class KeyboardClientMixin {

    @Shadow private KeyMapping[] array;

    @Inject(method = "init", at = @At("TAIL"), remap = false)
    private void injectInit(CallbackInfo info){
        List<KeyMapping> list = new ArrayList<>(Arrays.asList(array));
        list.add(GravisuitKeys.G_KEY);
        array = list.toArray(new KeyMapping[0]);
    }
}
