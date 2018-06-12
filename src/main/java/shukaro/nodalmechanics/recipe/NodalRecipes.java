package shukaro.nodalmechanics.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

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
        matrixRecipe = new ShapedArcaneRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), new Object[] {
                "XYX",
                "YZY",
                "XYX",
                Character.valueOf('X'), ItemApi.getItem("itemResource", 14),
                Character.valueOf('Y'), ItemApi.getBlock("blockMagicalLog", 0),
                Character.valueOf('Z'), ItemApi.getBlock("blockJar", 0)
        });

        NBTTagCompound variedAttuneTag = new NBTTagCompound();
        variedAttuneTag.setString("aspects", "aer,terra,ignis,aqua,ordo,perditio");
        ItemStack variedAttune = new ItemStack(NodalItems.itemMatrix);
        ItemStack[] phials = new ItemStack[6];
        ArrayList<Aspect> primalAspects = Aspect.getPrimalAspects();
        AspectList variedAspectList = new AspectList();
        for (int i = 0; i < primalAspects.size(); i++)
        {
            variedAspectList.add(primalAspects.get(i), 1);
            phials[i] = ItemApi.getItem("itemEssence", 1);
            NBTTagCompound tagCompound = new NBTTagCompound();
            AspectList phialAspectList = new AspectList().add(primalAspects.get(i), 8);
            phialAspectList.writeToNBT(tagCompound);
            phials[i].setTagCompound(tagCompound);
        }
        NBTTagCompound variedTagCompound = new NBTTagCompound();
        variedAspectList.writeToNBT(variedTagCompound);
        variedAttune.setTagCompound(variedTagCompound);
        variedAttuneRecipe =
            new ShapedArcaneRecipe("NODECATALYZATION", variedAttune, attuneAspectList, "ABC", " X ", "DEF", 'A',
                                   phials[0], 'B', phials[1], 'C', phials[2], 'D', phials[3], 'E', phials[4], 'F',
                                   phials[5], 'X', NodalItems.itemMatrix);
        ItemStack sameAttune = new ItemStack(NodalItems.itemMatrix);
        AspectList sameAspectList = new AspectList().add(Aspect.FIRE, 8);
        NBTTagCompound sameTagCompound = new NBTTagCompound();
        sameAspectList.writeToNBT(sameTagCompound);
        sameAttune.setTagCompound(sameTagCompound);
        ItemStack phial = ItemApi.getItem("itemEssence", 1);
        NBTTagCompound phialTagCompound = new NBTTagCompound();
        sameAspectList.writeToNBT(phialTagCompound);
        phial.setTagCompound(phialTagCompound);
        sameAttuneRecipe =
            new ShapedArcaneRecipe("NODECATALYZATION", sameAttune, attuneAspectList, "AAA", "ABA", "AAA", 'A',
                                   phial.copy(), 'B', NodalItems.itemMatrix);
        NBTTagCompound variedNodeTagCompound = new NBTTagCompound();
        AspectList variedNodeAspectList = new AspectList().add(Aspect.AIR, 1)
                                                          .add(Aspect.EARTH, 1)
                                                          .add(Aspect.FIRE, 1)
                                                          .add(Aspect.WATER, 1)
                                                          .add(Aspect.ORDER, 1)
                                                          .add(Aspect.ENTROPY, 1);
        variedNodeAspectList.writeToNBT(variedNodeTagCompound);
        variedNodeTagCompound.setInteger("nodetype", 0);
        ItemStack variedNode = ItemApi.getItem("itemJarNode", 0);
        variedNode.setTagCompound(variedNodeTagCompound);
        variedNodeRecipe = new InfusionRecipe("NODECATALYZATION", variedNode, 10, new AspectList().add(Aspect.AIR, 4)
                                                                                                  .add(Aspect.EARTH, 4)
                                                                                                  .add(Aspect.FIRE, 4)
                                                                                                  .add(Aspect.WATER, 4)
                                                                                                  .add(Aspect.ORDER, 4)
                                                                                                  .add(Aspect.ENTROPY,
                                                                                                       4), variedAttune,
                                              new ItemStack[] {ItemApi.getItem("itemResource", 14),
                                                               ItemApi.getItem("itemResource", 14),
                                                               ItemApi.getItem("itemResource", 14),
                                                               ItemApi.getItem("itemResource", 14)});
        NBTTagCompound sameNodeTagCompound = new NBTTagCompound();
        AspectList sameNodeAspectList = new AspectList().add(Aspect.FIRE, 8);
        sameNodeAspectList.writeToNBT(sameNodeTagCompound);
        sameNodeTagCompound.setInteger("nodetype", 0);
        ItemStack sameNode = ItemApi.getItem("itemJarNode", 0);
        sameNode.setTagCompound(sameNodeTagCompound);
        sameNodeRecipe =
            new InfusionRecipe("NODECATALYZATION", sameNode, 10, new AspectList().add(Aspect.FIRE, 32), sameAttune,
                               new ItemStack[] {ItemApi.getItem("itemResource", 14),
                                                ItemApi.getItem("itemResource", 14),
                                                ItemApi.getItem("itemResource", 14),
                                                ItemApi.getItem("itemResource", 14)});
    }
    @SuppressWarnings("unchecked")
    public void initRecipes()
    {
        ThaumcraftApi.addArcaneCraftingRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), new Object[]{
                "XYX",
                "YZY",
                "XYX",
                Character.valueOf('X'), ItemApi.getItem("itemResource", 14),
                Character.valueOf('Y'), ItemApi.getBlock("blockMagicalLog", 0),
                Character.valueOf('Z'), ItemApi.getBlock("blockJar", 0)
        });

        RecipeAttune recipeAttune = new RecipeAttune();
        ThaumcraftApi.getCraftingRecipes().add(recipeAttune);
        RecipeNode recipeNode = new RecipeNode();
        ThaumcraftApi.getCraftingRecipes().add(recipeNode);
    }
}
