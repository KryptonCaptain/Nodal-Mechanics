package shukaro.nodalmechanics.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.util.ArrayList;

public class NodalRecipes
{
    public static ShapedArcaneRecipe matrixRecipe;
    public static ShapedArcaneRecipe variedAttuneRecipe;
    public static ShapedArcaneRecipe sameAttuneRecipe;
    public static InfusionRecipe variedNodeRecipe;
    public static InfusionRecipe sameNodeRecipe;
    
    public NodalRecipes()
    {
    	//BASE CATALYST
        matrixRecipe = new ShapedArcaneRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), 
        		new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), 
        		new Object[] {
	                "SLS",
	                "LJL",
	                "SLS",
	                'S', new ItemStack(ConfigItems.itemResource,1,14) ,
	                'L', new ItemStack(ConfigBlocks.blockMagicalLog,1,0),
	                'J', new ItemStack(ConfigBlocks.blockJar,1,0)
        	});
        
        

        //VARIED ATTUNE
        ItemStack variedAttune = new ItemStack(NodalItems.itemMatrix);
        ArrayList<Aspect> primalAspects = Aspect.getPrimalAspects();
        AspectList variedAspectList = new AspectList();
        for (int i = 0; i < primalAspects.size(); i++)
        {
            variedAspectList.add(primalAspects.get(i), RecipeAttune.ESSENTIA_MULTIPLIER);
        }
        
        NBTTagCompound variedTagCompound = new NBTTagCompound();
        variedAspectList.writeToNBT(variedTagCompound);
        variedAttune.setTagCompound(variedTagCompound);
        
        variedAttuneRecipe = new ShapedArcaneRecipe("NODECATALYZATION", variedAttune, RecipeAttune.CRAFTING_COST, 
        		"ABC", 
        		" X ", 
        		"DEF", 
	        		'A', makePhial(Aspect.AIR), 
	        		'B', makePhial(Aspect.EARTH), 
	        		'C', makePhial(Aspect.FIRE), 
	        		'D', makePhial(Aspect.WATER), 
	        		'E', makePhial(Aspect.ORDER), 
	        		'F', makePhial(Aspect.ENTROPY), 
	        		'X', NodalItems.itemMatrix
    		);
        
        
        //SAME ATTUNE
        ItemStack sameAttune = new ItemStack(NodalItems.itemMatrix);
        AspectList sameAspectList = new AspectList().add(Aspect.FIRE, 8*RecipeAttune.ESSENTIA_MULTIPLIER);
        NBTTagCompound sameTagCompound = new NBTTagCompound();
        sameAspectList.writeToNBT(sameTagCompound);
        sameAttune.setTagCompound(sameTagCompound);

        sameAttuneRecipe = new ShapedArcaneRecipe("NODECATALYZATION", sameAttune, RecipeAttune.CRAFTING_COST, 
        		"AAA", 
        		"ABA", 
        		"AAA", 
        			'A', makePhial(Aspect.FIRE), 
        			'B', NodalItems.itemMatrix);
        
        
        //INFUSION ESSENTIA COST
        int infusionCost = RecipeNode.COST_MULTIPLIER;
        
        //VARIED INFUSION
        NBTTagCompound variedNodeTagCompound = new NBTTagCompound();
        AspectList variedNodeAspectList = new AspectList()
        				.add(Aspect.AIR, RecipeNode.NODE_MULTIPLIER).add(Aspect.EARTH, RecipeNode.NODE_MULTIPLIER).add(Aspect.FIRE, RecipeNode.NODE_MULTIPLIER)
        				.add(Aspect.WATER, RecipeNode.NODE_MULTIPLIER).add(Aspect.ORDER, RecipeNode.NODE_MULTIPLIER).add(Aspect.ENTROPY, RecipeNode.NODE_MULTIPLIER);
        variedNodeAspectList.writeToNBT(variedNodeTagCompound);
        variedNodeTagCompound.setInteger("nodetype", 0);
        ItemStack variedNode = ItemApi.getItem("itemJarNode", 0);
        variedNode.setTagCompound(variedNodeTagCompound);
        
        variedNodeRecipe = new InfusionRecipe("NODECATALYZATION", variedNode, RecipeNode.INSTABILITY, 
        		new AspectList().add(Aspect.AIR, infusionCost).add(Aspect.EARTH, infusionCost).add(Aspect.FIRE, infusionCost).add(Aspect.WATER, infusionCost).add(Aspect.ORDER, infusionCost).add(Aspect.ENTROPY, infusionCost), 
        		variedAttune,
        		new ItemStack[] {
        			new ItemStack(ConfigItems.itemResource,1,14),
        			new ItemStack(ConfigItems.itemResource,1,14),
        			new ItemStack(ConfigItems.itemResource,1,14),
        			new ItemStack(ConfigItems.itemResource,1,14)
                });
        
        
        //SAME INFUSION
        NBTTagCompound sameNodeTagCompound = new NBTTagCompound();
        AspectList sameNodeAspectList = new AspectList().add(Aspect.FIRE, 8*RecipeNode.NODE_MULTIPLIER);
        sameNodeAspectList.writeToNBT(sameNodeTagCompound);
        sameNodeTagCompound.setInteger("nodetype", 0);
        ItemStack sameNode = ItemApi.getItem("itemJarNode", 0);
        sameNode.setTagCompound(sameNodeTagCompound);
        
        sameNodeRecipe = new InfusionRecipe("NODECATALYZATION", sameNode, RecipeNode.INSTABILITY, 
        		new AspectList().add(Aspect.FIRE, 8*infusionCost), 
        		sameAttune,
                new ItemStack[] {
        			new ItemStack(ConfigItems.itemResource,1,14),
            		new ItemStack(ConfigItems.itemResource,1,14),
            		new ItemStack(ConfigItems.itemResource,1,14),
            		new ItemStack(ConfigItems.itemResource,1,14)
        		});
    }
    
    
    @SuppressWarnings("unchecked")
    public void initRecipes()
    {
        ThaumcraftApi.addArcaneCraftingRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), 
        		new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), 
        		new Object[]{
		        	"SLS",
		            "LJL",
		            "SLS",
		            'S', new ItemStack(ConfigItems.itemResource,1,14) ,
		            'L', new ItemStack(ConfigBlocks.blockMagicalLog,1,0),
		            'J', new ItemStack(ConfigBlocks.blockJar,1,0)
        });

        RecipeAttune recipeAttune = new RecipeAttune();
        ThaumcraftApi.getCraftingRecipes().add(recipeAttune);
        RecipeNode recipeNode = new RecipeNode();
        ThaumcraftApi.getCraftingRecipes().add(recipeNode);
    }
    
    //Borrowed from TNexus, because NM was doing it a REALLY stupid way
    public static ItemStack makePhial(final Aspect aspect) {
        return makePhial(aspect, 1);
    }
    
    public static ItemStack makePhial(final Aspect aspect, final int n) {
        final ItemStack is = new ItemStack(ConfigItems.itemEssence, n, 1);
        ((IEssentiaContainerItem)ConfigItems.itemEssence).setAspects(is, new AspectList().add(aspect, 8));
        return is;
    }
    //
}
