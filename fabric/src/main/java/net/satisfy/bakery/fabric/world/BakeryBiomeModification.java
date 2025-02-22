package net.satisfy.bakery.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.satisfy.bakery.util.BakeryIdentifier;
import net.satisfy.bakery.world.feature.BakeryPlacedFeature;

import java.util.function.Predicate;


public class BakeryBiomeModification {
    public static void init() {
        BiomeModification world = BiomeModifications.create(new BakeryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> strawberryBiomes = getBakerySelector();

        world.add(ModificationPhase.ADDITIONS, strawberryBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BakeryPlacedFeature.STRAWBERRY_PATCH_CHANCE_KEY));
    }

    private static Predicate<BiomeSelectionContext> getBakerySelector() {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new BakeryIdentifier("spawns_strawberries")));
    }


}
