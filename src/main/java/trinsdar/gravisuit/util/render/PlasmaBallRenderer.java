package trinsdar.gravisuit.util.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.entity.PlasmaBall;

public class PlasmaBallRenderer extends EntityRenderer<PlasmaBall> {
    ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/entity/plasma_ball.png");
    public PlasmaBallRenderer(EntityRendererProvider.Context arg) {
        super(arg);

    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaBall entity) {
        return TEXTURE;
    }
}
