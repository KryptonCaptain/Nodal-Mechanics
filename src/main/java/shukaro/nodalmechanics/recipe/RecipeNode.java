package shukaro.nodalmechanics.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;

import java.util.ArrayList;
import java.util.Random;

public class RecipeNode
    extends InfusionRecipe
{
    private static final int ESSENTIA_MULTIPLIER = 4;
    private static final int INSTABILITY = 10;
    private final Random random;
    public RecipeNode()
    {
        super("NODECATALYZATION", ItemApi.getItem("itemJarNode", 0), INSTABILITY, new AspectList(),
              new ItemStack(NodalItems.itemMatrix),
              new ItemStack[] {ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14),
                               ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14)});
        random = new Random();
    }
    @Override
    public boolean matches(ArrayList<ItemStack> input, ItemStack central, World world, EntityPlayer player)
    {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), getResearch()))
        {
            return false;
        }
        ItemStack[] components = getComponents();
        if (input == null || input.size() != components.length)
        {
            return false;
        }
        for (int i = 0; i < components.length; i++)
        {
            if (!OreDictionary.itemMatches(components[i], input.get(i), true))
            {
                return false;
            }
        }
        if (central != null && central.getItem().equals(NodalItems.itemMatrix))
        {
            if (central.hasTagCompound())
            {
                AspectList nodeAspectList = new AspectList();
                nodeAspectList.readFromNBT(central.getTagCompound());
                if (nodeAspectList.size() == 0)
                {
                    return false;
                }
                AspectList recipeAspectList = new AspectList();
                for (Aspect aspect : nodeAspectList.getAspects())
                {
                    recipeAspectList.add(aspect, nodeAspectList.getAmount(aspect) * ESSENTIA_MULTIPLIER);
                }
                this.aspects = recipeAspectList;
                NBTTagCompound tagCompound = new NBTTagCompound();
                nodeAspectList.writeToNBT(tagCompound);
                tagCompound.setInteger("nodetype", getNodeType());
                if (random.nextInt(100) < 75)
                {
                    tagCompound.setInteger("nodemod", getNodeModifier());
                }
                ((ItemStack) this.recipeOutput).setTagCompound(tagCompound);
                return true;
            }
        }
        return false;
    }
    private int getNodeType()
    {
        int chance = random.nextInt(100);
        if (chance < 75)
        {
            return NodeType.NORMAL.ordinal();
        }
        if (chance < 80)
        {
            return NodeType.UNSTABLE.ordinal();
        }
        if (chance < 85)
        {
            return NodeType.DARK.ordinal();
        }
        if (chance < 90)
        {
            return NodeType.TAINTED.ordinal();
        }
        if (chance < 95)
        {
            return NodeType.HUNGRY.ordinal();
        }
        return NodeType.PURE.ordinal();
    }
    private int getNodeModifier()
    {
        int chance = random.nextInt(100);
        if (chance < 75)
        {
            return NodeModifier.FADING.ordinal();
        }
        if (chance < 90)
        {
            return NodeModifier.PALE.ordinal();
        }
        return NodeModifier.BRIGHT.ordinal();
    }
}
