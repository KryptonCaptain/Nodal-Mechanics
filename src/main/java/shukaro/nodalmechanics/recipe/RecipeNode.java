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
import thaumcraft.common.config.ConfigItems;

import java.util.ArrayList;
import java.util.Random;

public class RecipeNode extends InfusionRecipe {
	
	/**
	Infusion essentia cost per matrix point
	 */
    public static final int COST_MULTIPLIER = 10; //old 10; make config option?
    /**
    Conversion rate of (1) matrix points to (n) node points
     */
    public static final int NODE_MULTIPLIER = 1;
    public static final int INSTABILITY = 10; //dangerous
    private final Random random;
    
    public RecipeNode()
    {
        super("NODECATALYZATION", new ItemStack(ConfigItems.itemJarNode,1,0), INSTABILITY, new AspectList(),
              new ItemStack(NodalItems.itemMatrix),
              new ItemStack[] {
        			new ItemStack(ConfigItems.itemResource,1,14), 
        			new ItemStack(ConfigItems.itemResource,1,14),
        			new ItemStack(ConfigItems.itemResource,1,14), 
        			new ItemStack(ConfigItems.itemResource,1,14)
    			});
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
                AspectList outputAspectList = new AspectList();
                for (Aspect aspect : nodeAspectList.getAspects())
                {
                    recipeAspectList.add(aspect, nodeAspectList.getAmount(aspect) * COST_MULTIPLIER); //infusion recipe cost
                    outputAspectList.add(aspect, nodeAspectList.getAmount(aspect) * NODE_MULTIPLIER); //node output yield
                }
                this.aspects = recipeAspectList;
                NBTTagCompound tagCompound = new NBTTagCompound();
                outputAspectList.writeToNBT(tagCompound);
                tagCompound.setInteger("nodetype", getNodeType());
                if (random.nextInt(100) < 75) //old < 60 ; chance to have a node mod at all
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
        
        //GTNH
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
        /*
        //old
		if (chance < 80)
			return NodeType.NORMAL.ordinal();
		else if (chance < 85)
			return NodeType.UNSTABLE.ordinal();
		else if (chance < 90)
			return NodeType.HUNGRY.ordinal();
		else if (chance < 93)
			return NodeType.DARK.ordinal();	
		else if (chance < 96)
			return NodeType.TAINTED.ordinal();	
		else
			return NodeType.PURE.ordinal();
        */
    }
    
    private int getNodeModifier()
    {
        int chance = random.nextInt(100);
        /*
        //GTNH
        if (chance < 75)
        {
            return NodeModifier.FADING.ordinal();
        }
        if (chance < 90)
        {
            return NodeModifier.PALE.ordinal();
        }
        return NodeModifier.BRIGHT.ordinal();
        */
        
        //old
        if (chance < 40)
        	return NodeModifier.BRIGHT.ordinal();
    	else if (chance < 80)
    		return NodeModifier.PALE.ordinal();
		else
			return NodeModifier.FADING.ordinal();
         
    }
}
