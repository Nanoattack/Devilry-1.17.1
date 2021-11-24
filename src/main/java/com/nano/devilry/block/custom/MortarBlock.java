package com.nano.devilry.block.custom;

import com.nano.devilry.blockentity.MortarEntity;
import com.nano.devilry.container.MortarContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class MortarBlock extends HorizontalDirectionalBlock implements EntityBlock
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

    //BLOCK ENTITY STUFF

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new MortarEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MortarEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.devilry.mortar");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new MortarContainer(windowId, level, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}

