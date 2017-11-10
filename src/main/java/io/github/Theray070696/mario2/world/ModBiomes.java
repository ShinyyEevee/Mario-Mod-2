package io.github.Theray070696.mario2.world;

import io.github.Theray070696.mario2.world.biome.BiomeMario;
import io.github.Theray070696.mario2.world.biome.BiomeMarioForest;
import io.github.Theray070696.mario2.world.biome.BiomeMarioPlains;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class ModBiomes
{
    public static BiomeMario biomeMarioPlains;
    public static BiomeMario biomeMarioForest;
    public static BiomeMario biomeMarioForestHills;

    public static void initBiomes()
    {
        biomeMarioPlains = GameRegistry.register(new BiomeMarioPlains());
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioPlains, 2));
        BiomeManager.addSpawnBiome(biomeMarioPlains);
        BiomeDictionary.registerBiomeType(biomeMarioPlains, BiomeDictionary.Type.PLAINS);

        biomeMarioForest = GameRegistry.register(new BiomeMarioForest("biomeMarioForest", new Biome.BiomeProperties("Mario Forest").setBaseBiome
                ("Forest").setTemperature(0.7F).setRainfall(0.8F)));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForest, 2));
        BiomeManager.addSpawnBiome(biomeMarioForest);
        BiomeDictionary.registerBiomeType(biomeMarioForest, BiomeDictionary.Type.FOREST);

        biomeMarioForestHills = GameRegistry.register(new BiomeMarioForest("biomeMarioForestHills", new Biome.BiomeProperties("Mario Forest Hills")
                .setBaseBiome("ForestHills").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.7F).setRainfall(0.8F)));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForestHills, 2));
        BiomeManager.addSpawnBiome(biomeMarioForestHills);
        BiomeDictionary.registerBiomeType(biomeMarioForestHills, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);
    }
}
