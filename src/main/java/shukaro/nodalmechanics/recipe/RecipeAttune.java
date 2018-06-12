package shukaro.nodalmechanics.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;

public class RecipeAttune
    implements IArcaneRecipe
{
    private static final int ESSENTIA_MULTIPLIER = 4;
    private ItemStack output;
    @Override
    public boolean matches(IInventory inventory, World world, EntityPlayer player)
    {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), getResearch()))
        {
            return false;
        }
        ItemStack matrix = null;
        ItemStack targetMatrix = new ItemStack(NodalItems.itemMatrix);
        ItemStack phial = ItemApi.getItem("itemEssence", 1);
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                ItemStack slotStack = ThaumcraftApiHelper.getStackInRowAndColumn(inventory, i, j);
                if (OreDictionary.itemMatches(targetMatrix, slotStack, true))
                {
                    if (matrix != null)
                    {
                        return false;
                    }
                    matrix = slotStack;
                }
            }
        }
        if (matrix == null)
        {
            return false;
        }
        AspectList aspectList = new AspectList();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                ItemStack slotStack = ThaumcraftApiHelper.getStackInRowAndColumn(inventory, i, j);
                if (slotStack == null || slotStack.getItem() == null)
                {
                    continue;
                }
                if (OreDictionary.itemMatches(matrix, slotStack, true))
                {
                    continue;
                }
                if (OreDictionary.itemMatches(phial, slotStack, true))
                {
                    if (!slotStack.hasTagCompound())
                    {
                        return false;
                    }
                    AspectList phialAspectList = new AspectList();
                    phialAspectList.readFromNBT(slotStack.getTagCompound());
                    if (phialAspectList.size() == 0)
                    {
                        return false;
                    }
                    for (Aspect aspect : phialAspectList.getAspects())
                    {
                        aspectList.add(aspect, phialAspectList.getAmount(aspect) / ESSENTIA_MULTIPLIER);
                    }
                    continue;
                }
                return false;
            }
        }
        if (aspectList.size() > 0)
        {
            output = matrix.copy();
            NBTTagCompound tagCompound = output.hasTagCompound() ? output.getTagCompound() : new NBTTagCompound();
            AspectList initialAspectList = new AspectList();
            initialAspectList.readFromNBT(tagCompound);
            aspectList.add(initialAspectList);
            aspectList.writeToNBT(tagCompound);
            output.setTagCompound(tagCompound);
            return true;
        }
        return false;
    }
    @Override
    public ItemStack getCraftingResult(IInventory inventory)
    {
        return output.copy();
    }
    @Override
    public int getRecipeSize()
    {
        return 9;
    }
    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }
    @Override
    public AspectList getAspects()
    {
        return new AspectList().add(Aspect.AIR, 1)
                               .add(Aspect.WATER, 1)
                               .add(Aspect.FIRE, 1)
                               .add(Aspect.ORDER, 1)
                               .add(Aspect.ENTROPY, 1)
                               .add(Aspect.EARTH, 1);
    }
    @Override
    public AspectList getAspects(IInventory inventory)
    {
        return getAspects();
    }
    @Override
    public String getResearch()
    {
        return "NODECATALYZATION";
    }
}
