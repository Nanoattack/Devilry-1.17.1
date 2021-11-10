package com.nano.devilry.block;

import com.nano.devilry.ModMain;
import com.nano.devilry.item.ModItemGroups;
import com.nano.devilry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MOD_ID);

    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f)));

    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f)));

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5f).sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static<T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get()
                                    , new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}