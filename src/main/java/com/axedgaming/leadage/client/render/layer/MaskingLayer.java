package com.axedgaming.leadage.client.render.layer;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.ModCapabilities;
import com.axedgaming.leadage.common.capability.IMaskingData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class MaskingLayer
        extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation WHITE =
            ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "textures/misc/white.png");

    public MaskingLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        boolean masked = player.getCapability(ModCapabilities.MASKING)
                .map(IMaskingData::isMasked)
                .orElse(false);

        if (!masked) return;

        PlayerModel<AbstractClientPlayer> model = this.getParentModel();

        model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        VertexConsumer vc = buffer.getBuffer(RenderType.entitySolid(WHITE));

        model.renderToBuffer(
                poseStack,
                vc,
                packedLight,
                NO_OVERLAY,
                1f, 1f, 1f, 1f
        );
    }
}