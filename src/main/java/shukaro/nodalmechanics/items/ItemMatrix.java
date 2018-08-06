package shukaro.nodalmechanics.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.text.WordUtils;

import shukaro.nodalmechanics.NodalMechanics;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMatrix
    extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    public ItemMatrix()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(NodalMechanics.mainTab);
        this.setUnlocalizedName("nodalmechanics.matrix");
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            return this.getUnlocalizedName() + ".attuned";
        }
        else
        {
            return this.getUnlocalizedName() + ".unattuned";
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        for (Aspect aspect : Aspect.getPrimalAspects())
        {
            ItemStack itemStack = new ItemStack(item, 1, 0);
            AspectList aspectList = new AspectList().add(aspect, 100);
            NBTTagCompound tagCompound = new NBTTagCompound();
            aspectList.writeToNBT(tagCompound);
            itemStack.setTagCompound(tagCompound);
            list.add(itemStack);
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return this.icon;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.icon = iconRegister.registerIcon(NodalMechanics.modID + ":" + "itemMatrix");
    }
    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return itemStack.hasTagCompound() ? EnumRarity.uncommon : EnumRarity.common;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int pass)
    {
        return itemStack.hasTagCompound();
    }
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedTooltips)
    {
        if (itemStack.hasTagCompound())
        {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            AspectList aspectList = new AspectList();
            aspectList.readFromNBT(tagCompound);
            for (Aspect aspect : aspectList.getAspects())
            {
                list.add("\u00A7" + (aspect.getChatcolor() != null ? aspect.getChatcolor() : "7") +
                         WordUtils.capitalize(aspect.getName()) + EnumChatFormatting.RESET + EnumChatFormatting.WHITE +
                         " x " + aspectList.getAmount(aspect));
            }
            list.add(EnumChatFormatting.DARK_GRAY +""+ EnumChatFormatting.ITALIC +
                     StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.attuned"));
        }
        else
        {
            list.add(EnumChatFormatting.DARK_GRAY +""+ EnumChatFormatting.ITALIC +
                     StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.unattuned"));
        }
        list.add(EnumChatFormatting.GRAY +
                 StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.recipe"));
    }
}
