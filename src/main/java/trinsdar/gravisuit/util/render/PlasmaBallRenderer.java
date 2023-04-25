package trinsdar.gravisuit.util.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.entity.PlasmaBall;

public class PlasmaBallRenderer extends EntityRenderer<PlasmaBall> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(GravisuitClassic.MODID, "textures/entity/plasma_ball.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE, false);
    public PlasmaBallRenderer(EntityRendererProvider.Context arg) {
        super(arg);

    }

    public void render(PlasmaBall entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.scale(2.0F, 2.0F, 2.0F);
        matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        PoseStack.Pose pose = matrixStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer arg, Matrix4f arg2, Matrix3f arg3, int i, float f, int j, int k, int l) {
        arg.vertex(arg2, f - 0.5F, (float)j - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)k, (float)l).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(arg3, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaBall entity) {
        return TEXTURE;
    }
}
