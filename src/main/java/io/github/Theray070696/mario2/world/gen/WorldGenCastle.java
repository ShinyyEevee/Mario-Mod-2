package io.github.Theray070696.mario2.world.gen;

import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.dev.MarioDevStats;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

/**
 * Created by Theray070696 on 8/18/2017
 */
public class WorldGenCastle extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(!world.isRemote)
        {
            WorldServer worldServer = (WorldServer) world;
            MinecraftServer minecraftServer = world.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            ResourceLocation loc = new ResourceLocation(ModInfo.MOD_ID, "castle");
            Template template = templateManager.getTemplate(minecraftServer, loc);
            if(template != null)
            {
                IBlockState blockState = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, blockState, blockState, 3);

                for(int x = 0; x < template.getSize().getX(); x++)
                {
                    for(int z = 0; z < template.getSize().getZ(); z++)
                    {
                        BlockPos checkPos = pos.add(x, -1, z);
                        if(world.isAirBlock(checkPos) || !world.getBlockState(checkPos).isFullBlock())
                        {
                            return false;
                        }
                    }
                }

                PlacementSettings placementsettings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(Rotation.values()[rand.nextInt(4)
                        ]).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

                template.addBlocksToWorld(world, pos.down(), placementsettings);

                if(ConfigHandler.developerModeEnabled)
                {
                    MarioDevStats.castlesGenerated++;
                }

                return true;
            }
        }

        return false;
    }
}
