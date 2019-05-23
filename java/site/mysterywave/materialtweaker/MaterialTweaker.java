package site.mysterywave.materialtweaker;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.init.Items;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.common.FMLCommonHandler;

@Mod(modid = MaterialTweaker.MODID, version = MaterialTweaker.VERSION)
public class MaterialTweaker {
    public static final String MODID = "materialtweaker";
    public static final String VERSION = "1.0";
    
    static final String[] replaceData = new String[] {
        "harvestLevel", "maxUses", "efficiencyOnProperMaterial", "enchantability"
    };
    
    static final Item.ToolMaterial[] toolMaterials = new Item.ToolMaterial[] {
        Item.ToolMaterial.GOLD,
        Item.ToolMaterial.WOOD,
        Item.ToolMaterial.STONE,
        Item.ToolMaterial.IRON,
        Item.ToolMaterial.EMERALD
    };
    
    static final ItemArmor.ArmorMaterial[] armorMaterials = new ItemArmor.ArmorMaterial[] {
        ItemArmor.ArmorMaterial.CLOTH,
        ItemArmor.ArmorMaterial.CHAIN,
        ItemArmor.ArmorMaterial.IRON,
        ItemArmor.ArmorMaterial.GOLD,
        ItemArmor.ArmorMaterial.DIAMOND
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        conf﻿ig.load();
        loadTools(config);
        loadArmors(config);
        config.getCategory("armor").setComment("Max damage values are calculated by multiplying the damage reduction of the armor piece and maxDamageFactor");
        config.getCategory("tool").setComment("Setting max damage values to 1 make the tools invincible");
        config.save();
    }
    
    public void loadTools(Configuration config) {
        for(Item.ToolMaterial m : toolMaterials) {
            String name = m.name().toLowerCase();
            if(name.equals("emerald")) {
                name = "diamond";
            }
            for(String s : replaceData) {
                Object o = ReflectionHelper.getPrivateValue(Item.ToolMaterial.class, m, s);
                if(o instanceof Integer) {
                    if(s.equals("maxUses")) {
                        Property p = config.get("tool" + Configuration.CATEGORY_SPLITTER + name, s, ((Integer)o).intValue() + 1);
                        ReflectionHelper.setPrivateValue(Item.ToolMaterial.class, m, p.getInt() - 1, s);
                    } else {
                        Property p = config.get("tool" + Configuration.CATEGORY_SPLITTER + name, s, ((Integer)o).intValue());
                        ReflectionHelper.setPrivateValue(Item.ToolMaterial.class, m, p.getInt(), s);
                    }
                } else if(o instanceof Float) {
                    Property p = config.get("tool" + Configuration.CATEGORY_SPLITTER + name, s, ((Float)o).doubleValue());
                    ReflectionHelper.setPrivateValue(Item.ToolMaterial.class, m, (float)p.getDouble(), s);
                } else if(o instanceof Double) {
                    Property p = config.get("tool" + Configuration.CATEGORY_SPLITTER + name, s, ((Double)o).doubleValue());
                    ReflectionHelper.setPrivateValue(Item.ToolMaterial.class, m, p.getDouble(), s);
                } else if(o instanceof Boolean) {
                    Property p = config.get("tool" + Configuration.CATEGORY_SPLITTER + name, s, ((Boolean)o).booleanValue());
                    ReflectionHelper.setPrivateValue(Item.ToolMaterial.class, m, p.getBoolean(), s);
                }
            }
        }
        
        // This value is stored outside the enum in Item.java, so we have to re-add it to everything
        Items.golden_shovel.setMaxDamage(Item.ToolMaterial.GOLD.getMaxUses());
        Items.wooden_shovel.setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
        Items.stone_shovel.setMaxDamage(Item.ToolMaterial.STONE.getMaxUses());
        Items.iron_shovel.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
        Items.diamond_shovel.setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
        
        Items.golden_pickaxe.setMaxDamage(Item.ToolMaterial.GOLD.getMaxUses());
        Items.wooden_pickaxe.setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
        Items.stone_pickaxe.setMaxDamage(Item.ToolMaterial.STONE.getMaxUses());
        Items.iron_pickaxe.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
        Items.diamond_pickaxe.setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
        
        Items.golden_axe.setMaxDamage(Item.ToolMaterial.GOLD.getMaxUses());
        Items.wooden_axe.setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
        Items.stone_axe.setMaxDamage(Item.ToolMaterial.STONE.getMaxUses());
        Items.iron_axe.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
        Items.diamond_axe.setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
        
        Items.golden_sword.setMaxDamage(Item.ToolMaterial.GOLD.getMaxUses());
        Items.wooden_sword.setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
        Items.stone_sword.setMaxDamage(Item.ToolMaterial.STONE.getMaxUses());
        Items.iron_sword.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
        Items.diamond_sword.setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
        
        Items.golden_hoe.setMaxDamage(Item.ToolMaterial.GOLD.getMaxUses());
        Items.wooden_hoe.setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
        Items.stone_hoe.setMaxDamage(Item.ToolMaterial.STONE.getMaxUses());
        Items.iron_hoe.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
        Items.diamond_hoe.setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
    }
    
    public void loadArmors(Configuration config) {
        for(ItemArmor.ArmorMaterial m : armorMaterials) {
            String name = m.name().toLowerCase();
            if(name.equals("cloth")) {
                name = "leather";
            }
            int[] damageReductionAmountArray = (int[])(ReflectionHelper.getPrivateValue(ItemArmor.ArmorMaterial.class, m, "damageReductionAmountArray"));
            Property[] p = new Property[] {
                config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "HelmetDamageReduction", damageReductionAmountArray[0]),
                config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "ChestplateDamageReduction", damageReductionAmountArray[1]),
                config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "LeggingDamageReduction", damageReductionAmountArray[2]),
                config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "BootsDamageReduction", damageReductionAmountArray[3])
            };
            damageReductionAmountArray[0] = p[0].getInt();
            damageReductionAmountArray[1] = p[1].getInt();
            damageReductionAmountArray[2] = p[2].getInt();
            damageReductionAmountArray[3] = p[3].getInt();
            ReflectionHelper.setPrivateValue(ItemArmor.ArmorMaterial.class, m, damageReductionAmountArray, "damageReductionAmountArray");
            int enchantability = ((Integer)ReflectionHelper.getPrivateValue(ItemArmor.ArmorMaterial.class, m, "enchantability")).intValue();
            Property p1 = config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "enchantability", enchantability);
            enchantability = p1.getInt();
            ReflectionHelper.setPrivateValue(ItemArmor.ArmorMaterial.class, m, enchantability, "enchantability");
            int maxDamageFactor = ((Integer)ReflectionHelper.getPrivateValue(ItemArmor.ArmorMaterial.class, m, "maxDamageFactor")).intValue();
            Property p2 = config.get("armor" + Configuration.CATEGORY_SPLITTER + name, "maxDamageFactor", maxDamageFactor);
            maxDamageFactor = p2.getInt();
            ReflectionHelper.setPrivateValue(ItemArmor.ArmorMaterial.class, m, maxDamageFactor, "maxDamageFactor");
        }
        Items.golden_helmet.setMaxDamage(ItemArmor.ArmorMaterial.GOLD.getDurability(0));
        Items.leather_helmet.setMaxDamage(ItemArmor.ArmorMaterial.CLOTH.getDurability(0));
        Items.chainmail_helmet.setMaxDamage(ItemArmor.ArmorMaterial.CHAIN.getDurability(0));
        Items.iron_helmet.setMaxDamage(ItemArmor.ArmorMaterial.IRON.getDurability(0));
        Items.diamond_helmet.setMaxDamage(ItemArmor.ArmorMaterial.DIAMOND.getDurability(0));
        
        Items.golden_chestplate.setMaxDamage(ItemArmor.ArmorMaterial.GOLD.getDurability(1));
        Items.leather_chestplate.setMaxDamage(ItemArmor.ArmorMaterial.CLOTH.getDurability(1));
        Items.chainmail_chestplate.setMaxDamage(ItemArmor.ArmorMaterial.CHAIN.getDurability(1));
        Items.iron_chestplate.setMaxDamage(ItemArmor.ArmorMaterial.IRON.getDurability(1));
        Items.diamond_chestplate.setMaxDamage(ItemArmor.ArmorMaterial.DIAMOND.getDurability(1));
        
        Items.golden_leggings.setMaxDamage(ItemArmor.ArmorMaterial.GOLD.getDurability(2));
        Items.leather_leggings.setMaxDamage(ItemArmor.ArmorMaterial.CLOTH.getDurability(2));
        Items.chainmail_leggings.setMaxDamage(ItemArmor.ArmorMaterial.CHAIN.getDurability(2));
        Items.iron_leggings.setMaxDamage(ItemArmor.ArmorMaterial.IRON.getDurability(2));
        Items.diamond_leggings.setMaxDamage(ItemArmor.ArmorMaterial.DIAMOND.getDurability(2));
        
        Items.golden_boots.setMaxDamage(ItemArmor.ArmorMaterial.GOLD.getDurability(3));
        Items.leather_boots.setMaxDamage(ItemArmor.ArmorMaterial.CLOTH.getDurability(3));
        Items.chainmail_boots.setMaxDamage(ItemArmor.ArmorMaterial.CHAIN.getDurability(3));
        Items.iron_boots.setMaxDamage(ItemArmor.ArmorMaterial.IRON.getDurability(3));
        Items.diamond_boots.setMaxDamage(ItemArmor.ArmorMaterial.DIAMOND.getDurability(3));
    }
}
