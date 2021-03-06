package io.github.Theray070696.mario2.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class ItemSuperLeaf extends ItemMario implements IBauble
{
    public ItemSuperLeaf()
    {
        super();

        this.setTranslationKey("itemSuperLeaf");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
    {
        if(!world.isRemote) // If we're on the server side...
        {
            if(entity != null && entity instanceof EntityPlayer && !(entity instanceof FakePlayer) && entity.motionY < 0.0f) // If the entity is
            // not null, is a player, is not a fake player, and is falling...
            {
                EntityPlayer player = (EntityPlayer) entity; // Save the player entity.

                for(int i = 0; i < 9; i++) // Loop from zero to nine.
                {
                    ItemStack itemStack = player.inventory.mainInventory.get(i); // Get the item in this hotbar slot.

                    if(!itemStack.isEmpty() && itemStack.isItemEqual(stack)) // If it's not null and is a cape...
                    {
                        player.fallDistance = 0.0F; // Cancel fall damage.
                        return; // Exit out of the function.
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer)) // If we're on the server side, the ItemStack
        // is not null, the player is not null, and the player is not a fake player...
        {
            player.fallDistance = 0.0F; // Cancel fall damage.

            //world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.leaf,
            // SoundCategory.PLAYERS, 1.0F, 1.0F); // Play a sound.
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(itemStack, player, tooltip, advanced);

        tooltip.add("Negates fall damage"); // Add helpful tooltip.
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player)
    {
        if(itemStack != ItemStack.EMPTY && player != null && !(player instanceof FakePlayer) && player.motionY < 0.0f) // If the ItemStack is not
            // empty, the player is not null, and the player is not a fake player...
        {
            player.fallDistance = 0.0F; // Cancel fall damage.
        }
    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase player)
    {
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase player)
    {
    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase player)
    {
        return true;
    }
}
