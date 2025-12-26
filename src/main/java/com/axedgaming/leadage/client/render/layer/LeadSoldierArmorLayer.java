package com.axedgaming.leadage.client.render.layer;

import com.axedgaming.leadage.common.entity.LeadSoldierEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class LeadSoldierArmorLayer
        extends RenderLayer<LeadSoldierEntity, HumanoidModel<LeadSoldierEntity>> {

    private static final ResourceLocation LEATHER_LAYER_1 =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/models/armor/leather_layer_1.png");

    private final HumanoidModel<LeadSoldierEntity> armorModel;

    public LeadSoldierArmorLayer(
            RenderLayerParent<LeadSoldierEntity, HumanoidModel<LeadSoldierEntity>> parent,
            net.minecraft.client.renderer.entity.EntityRendererProvider.Context ctx
    ) {
        super(parent);
        this.armorModel = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            LeadSoldierEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        ItemStack chest = entity.getTunic();
        if (!(chest.getItem() instanceof DyeableLeatherItem dyeable)) return;

        // Copier animations / rotations du mob
        this.getParentModel().copyPropertiesTo(this.armorModel);

        // Rendre uniquement le chestplate
        this.armorModel.setAllVisible(false);
        this.armorModel.body.visible = true;
        this.armorModel.leftArm.visible = true;
        this.armorModel.rightArm.visible = true;

        int color = dyeable.getColor(chest);
        float r = ((color >> 16) & 255) / 255f;
        float g = ((color >> 8) & 255) / 255f;
        float b = (color & 255) / 255f;

        this.armorModel.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(LEATHER_LAYER_1)),
                light,
                OverlayTexture.NO_OVERLAY,
                r, g, b, 1.0F
        );
    }
}