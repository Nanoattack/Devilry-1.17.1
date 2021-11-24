package com.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class MortarBlock extends HorizontalDirectionalBlock
{
    public MortarBlock(Properties builder) {
        super(builder);
    }

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_W = Stream.of(
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_S = Stream.of(
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_E = Stream.of(
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            default:
                return SHAPE_N;
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}

