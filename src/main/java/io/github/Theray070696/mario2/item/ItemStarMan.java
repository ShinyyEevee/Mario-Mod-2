package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.potion.PotionEffectsMario;
import io.github.Theray070696.raycore.api.RayCoreAPI;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class ItemStarMan extends ItemMario
{
    public ItemStarMan()
    {
        super();

        this.setTranslationKey("itemStarMan");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer) && !player.isPotionActive(PotionEffectsMario.potionStarman))
        {
            player.addPotionEffect(new PotionEffect(PotionEffectsMario.potionStarman, 258, 10));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 258, 5));

            if(player.getDisplayName().getUnformattedComponentText().equalsIgnoreCase("JasterMK3"))
            {
                world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler
                        .jasterStarMan, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }

            RayCoreAPI.playMovingSound(ModInfo.MOD_ID, "item.starman", SoundCategory.PLAYERS, world, player);

            itemStack.shrink(1);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(itemStack, player, tooltip, advanced);

        tooltip.add("Gives 12 seconds of invincibility and speed boost");
    }
}