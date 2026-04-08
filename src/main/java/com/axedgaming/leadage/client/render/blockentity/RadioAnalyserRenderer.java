package com.axedgaming.leadage.client.render.blockentity;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class RadioAnalyserRenderer implements BlockEntityRenderer<RadioAnalyserBlockEntity> {

    public RadioAnalyserRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RadioAnalyserBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {

        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        boolean hovered = isHoveringTicketSlot(blockEntity);

        if (hovered) {
            renderHoverCorners(poseStack, buffer);
        }

        ItemStack stack = blockEntity.getStoredTicket();
        if (!stack.isEmpty()) {
            int light = LevelRenderer.getLightColor(level, blockEntity.getBlockPos().above());

            poseStack.pushPose();
            poseStack.translate(0.5D, 1D, 0.5D);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

            float scale = 0.5F;
            poseStack.scale(scale, scale, scale);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    light,
                    packedOverlay,
                    poseStack,
                    buffer,
                    level,
                    0
            );

            poseStack.popPose();
        }
    }

    private boolean isHoveringTicketSlot(RadioAnalyserBlockEntity blockEntity) {
        Minecraft mc = Minecraft.getInstance();
        HitResult hit = mc.hitResult;

        if (!(hit instanceof BlockHitResult blockHit)) {
            return false;
        }

        BlockPos pos = blockEntity.getBlockPos();
        if (!blockHit.getBlockPos().equals(pos)) {
            return false;
        }

        double localX = blockHit.getLocation().x - pos.getX();
        double localY = blockHit.getLocation().y - pos.getY();
        double localZ = blockHit.getLocation().z - pos.getZ();

        return isInsideTicketZone(localX, localY, localZ);
    }

    private boolean isInsideTicketZone(double localX, double localY, double localZ) {
        return localX >= 0.25D && localX <= 0.75D
                && localY >= 0.70D && localY <= 1.00D
                && localZ >= 0.25D && localZ <= 0.75D;
    }

    private void renderHoverCorners(PoseStack poseStack, MultiBufferSource buffer) {
        poseStack.pushPose();

        VertexConsumer consumer = buffer.getBuffer(RenderType.debugQuads());
        PoseStack.Pose pose = poseStack.last();

        float y = 1F;

        float minX = 0.26F;
        float maxX = 0.74F;
        float minZ = 0.26F;
        float maxZ = 0.74F;

        float length = 0.10F;
        float thickness = 0.02F;
        float lift = 0.001F;

        float r = 1.0F;
        float g = 1.0F;
        float b = 1.0F;
        float a = 0.95F;

        float yy = y + lift;

        addFilledQuad(pose, consumer, minX, yy, minZ, minX + length, yy, minZ + thickness, r, g, b, a);
        addFilledQuad(pose, consumer, minX, yy, minZ, minX + thickness, yy, minZ + length, r, g, b, a);

        addFilledQuad(pose, consumer, maxX - length, yy, minZ, maxX, yy, minZ + thickness, r, g, b, a);
        addFilledQuad(pose, consumer, maxX - thickness, yy, minZ, maxX, yy, minZ + length, r, g, b, a);

        addFilledQuad(pose, consumer, minX, yy, maxZ - thickness, minX + length, yy, maxZ, r, g, b, a);
        addFilledQuad(pose, consumer, minX, yy, maxZ - length, minX + thickness, yy, maxZ, r, g, b, a);

        addFilledQuad(pose, consumer, maxX - length, yy, maxZ - thickness, maxX, yy, maxZ, r, g, b, a);
        addFilledQuad(pose, consumer, maxX - thickness, yy, maxZ - length, maxX, yy, maxZ, r, g, b, a);

        poseStack.popPose();
    }

    private void renderBar(PoseStack poseStack, VertexConsumer consumer,
                           float minX, float minY, float minZ,
                           float maxX, float maxY, float maxZ,
                           float r, float g, float b, float a) {
        LevelRenderer.renderLineBox(
                poseStack,
                consumer,
                minX, minY, minZ,
                maxX, maxY, maxZ,
                r, g, b, a
        );
    }

    private void addFilledQuad(PoseStack.Pose pose, VertexConsumer consumer,
                               float minX, float y1, float minZ,
                               float maxX, float y2, float maxZ,
                               float r, float g, float b, float a) {
        Matrix4f matrix = pose.pose();

        consumer.vertex(matrix, minX, y1, minZ).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, minX, y2, maxZ).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, maxX, y2, maxZ).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, maxX, y1, minZ).color(r, g, b, a).endVertex();
    }

    private void addLine(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         float r, float g, float b, float a) {

        float nx = x2 - x1;
        float ny = y2 - y1;
        float nz = z2 - z1;

        float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (len != 0.0F) {
            nx /= len;
            ny /= len;
            nz /= len;
        }

        consumer.vertex(matrix, x1, y1, z1)
                .color(r, g, b, a)
                .normal(normalMatrix, nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, x2, y2, z2)
                .color(r, g, b, a)
                .normal(normalMatrix, nx, ny, nz)
                .endVertex();
    }
}