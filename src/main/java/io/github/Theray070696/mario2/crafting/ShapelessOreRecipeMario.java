package io.github.Theray070696.mario2.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Theray070696 on 4/13/2017.
 * Code from Forge.
 */
public class ShapelessOreRecipeMario implements IMarioRecipe
{
    protected ItemStack output = null;
    protected ArrayList<Object> input = new ArrayList<Object>();

    public ShapelessOreRecipeMario(Block result, Object... recipe)
    {
        this(new ItemStack(result), recipe);
    }

    public ShapelessOreRecipeMario(Item result, Object... recipe)
    {
        this(new ItemStack(result), recipe);
    }

    public ShapelessOreRecipeMario(ItemStack result, Object... recipe)
    {
        output = result.copy();
        for(Object in : recipe)
        {
            if(in instanceof ItemStack)
            {
                input.add(((ItemStack) in).copy());
            } else if(in instanceof Item)
            {
                input.add(new ItemStack((Item) in));
            } else if(in instanceof Block)
            {
                input.add(new ItemStack((Block) in));
            } else if(in instanceof String)
            {
                input.add(OreDictionary.getOres((String) in));
            } else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for(Object tmp : recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    ShapelessOreRecipeMario(ShapelessRecipeMario recipe, Map<ItemStack, String> replacements)
    {
        output = recipe.getRecipeOutput();

        for(ItemStack ingredient : recipe.recipeItems)
        {
            Object finalObj = ingredient;
            for(Map.Entry<ItemStack, String> replace : replacements.entrySet())
            {
                if(OreDictionary.itemMatches(replace.getKey(), ingredient, false))
                {
                    finalObj = OreDictionary.getOres(replace.getValue());
                    break;
                }
            }
            input.add(finalObj);
        }
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return input.size();
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        return output.copy();
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for(int x = 0; x < inv.getSizeInventory(); x++)
        {
            ItemStack slot = inv.getStackInSlot(x);

            if(slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while(req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if(next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack) next, slot, false);
                    } else if(next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>) next).iterator();
                        while(itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if(match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if(!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public ArrayList<Object> getInput()
    {
        return this.input;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}
