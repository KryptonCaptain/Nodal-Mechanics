package shukaro.nodalmechanics;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Logger;

import shukaro.nodalmechanics.items.NodalItems;
import shukaro.nodalmechanics.net.CommonProxy;
import shukaro.nodalmechanics.recipe.NodalRecipes;
import shukaro.nodalmechanics.research.NodalResearch;

@Mod(
		modid = NodalMechanics.modID, 
		name = "Nodal Mechanics", 
		version = "${version}",
        dependencies = "required-after:Thaumcraft@[4.2.3.5,);"
    )

public class NodalMechanics
{
    @SidedProxy(clientSide = "shukaro.nodalmechanics.net.ClientProxy", serverSide = "shukaro.nodalmechanics.net.CommonProxy")
    public static CommonProxy proxy;

    public static final String modID = "NodalMechanics";

    public static Logger logger;

    public static CreativeTabs mainTab = new NodalTab(StatCollector.translateToLocal("nodalmechanics.tab"));

    @Mod.Instance(modID)
    public static NodalMechanics instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        logger = evt.getModLog();
        NodalItems.initItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt)
    {
        NodalRecipes recipes = new NodalRecipes();
        recipes.initRecipes();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt)
    {
        NodalResearch.initResearch();
    }
}
