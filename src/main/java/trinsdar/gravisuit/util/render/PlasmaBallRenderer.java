package trinsdar.gravisuit.util.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import trinsdar.gravisuit.entity.PlasmaBall;

public class PlasmaBallRenderer extends EntityRenderer<PlasmaBall> {
    protected PlasmaBallRenderer(EntityRendererProvider.Context arg) {
        super(arg);

    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaBall entity) {
        return null;
    }
}
