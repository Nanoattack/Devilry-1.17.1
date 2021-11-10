package com.nano.devilry.data;

import com.nano.devilry.block.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ModBlockLootTables extends BlockLoot
{
    @Override
    protected void addTables()
    {
        dropSelf(ModBlocks.TIN_BLOCK.get());

        dropSelf(ModBlocks.TIN_ORE.get());

        dropSelf(ModBlocks.RAW_TIN_BLOCK.get());

        dropSelf(ModBlocks.DEEPSLATE_TIN_ORE.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
