package com.axedgaming.leadage.client.render;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.client.render.layer.LeadSoldierArmorLayer;
import com.axedgaming.leadage.common.entity.LeadSoldierEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

public class LeadSoldierRenderer
        extends MobRenderer<LeadSoldierEntity, HumanoidModel<LeadSoldierEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "textures/entities/lead_soldier.png");

    public LeadSoldierRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)), 0.5F);

        this.addLayer(new LeadSoldierArmorLayer(this, ctx));
        this.addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(LeadSoldierEntity entity) {
        return TEXTURE;
    }
}