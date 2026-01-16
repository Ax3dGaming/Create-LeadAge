package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.Objects;

public class ModBlockStateGens {
    public static <P extends EncasedPipeBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> encasedPipe(String casing) {
        return (c, p) -> {
            ModelFile open = Objects.requireNonNull(createModelInBlock(p, "encased_pipe/" + casing + "/block_open")).parent(new ModelFile.UncheckedModelFile(LeadAge.asResource("block/templates/pipe_block_open"))).texture("1",getCasingTexture(casing)).texture("particle",getCasingTexture(casing));
            ModelFile flat = Objects.requireNonNull(createModelInBlock(p, "encased_pipe/" + casing + "/block_flat")).parent(new ModelFile.UncheckedModelFile(Create.ID + ":block/encased_fluid_pipe/block_flat")).texture("0",getCasingTexture(casing)).texture("particle",getCasingTexture(casing));
            MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
            for (boolean flatPass : Iterate.trueAndFalse)
                for (Direction d : Iterate.directions) {
                    int verticalAngle = d == Direction.UP ? 90 : d == Direction.DOWN ? -90 : 0;
                    builder.part()
                            .modelFile(flatPass ? flat : open)
                            .rotationX(verticalAngle)
                            .rotationY((int) (d.toYRot() + (d.getAxis()
                                    .isVertical() ? 90 : 0)) % 360)
                            .addModel()
                            .condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(d), !flatPass)
                            .end();
                }
        };
    }

    public static ModelBuilder<? extends ModelBuilder<?>> createModelInBlock(RegistrateProvider p, String path){
        if (p instanceof RegistrateBlockstateProvider provider)
            return provider.models()
                    .getBuilder("block/"+path);
        else if (p instanceof RegistrateItemModelProvider provider)
            return provider.getBuilder("block/"+path);
        return null;
    }

    public static String getCasingTexture(String casing){
        if (casing.equals("normal")) return Create.ID+":block/andesite_casing";
        String modid = getModForCasing(casing);
        if (casing.equals("industrial_iron") || casing.equals("weathered_iron")) return modid + ":block/"+casing+"_block";
        return modid + ":block/"+casing+"_casing";
    }

    public static String getModForCasing(String casing){
        if (casing.equals("brass") || casing.equals("andesite") || casing.equals("copper") || casing.equals("railway") || casing.equals("industrial_iron") || casing.equals("creative") || casing.equals("weathered_iron") || casing.equals("shadow_steel") || casing.equals("refined_radiance")) return Create.ID;
        return LeadAge.MOD_ID;
    }
}
