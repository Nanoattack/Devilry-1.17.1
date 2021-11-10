package com.nano.devilry.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups
 //Can't have capitals in json denotation else data generators stop functioning
{
        public static final CreativeModeTab MOD_MATERIAL_GROUP = new CreativeModeTab("devilrymaterials")
        {
            @Override
            public ItemStack makeIcon()
            {
                return new ItemStack(ModItems.TIN_INGOT.get());
            }
        };

}
