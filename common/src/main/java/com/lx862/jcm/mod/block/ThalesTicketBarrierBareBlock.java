package com.lx862.jcm.mod.block;

import com.lx862.jcm.mod.block.base.DirectionalBlock;
import com.lx862.jcm.mod.data.BlockProperties;
import mtr.block.BlockGlassFence;
import mtr.block.IBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThalesTicketBarrierBareBlock extends DirectionalBlock {
    public static final IntegerProperty FENCE_TYPE = BlockProperties.BARRIER_FENCE_TYPE;
    public static final BooleanProperty FLIPPED = BlockProperties.BARRIER_FLIPPED;

    public ThalesTicketBarrierBareBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        boolean hasFence = IBlock.getStatePropertySafe(state, FENCE_TYPE) != 0;
        boolean flipped = IBlock.getStatePropertySafe(state, FLIPPED);
        VoxelShape mainBarrierShape = IBlock.getVoxelShapeByDirection(12, 0, 0, 16, 16, 16, IBlock.getStatePropertySafe(state, FACING));

        VoxelShape vx1;
        if(hasFence) {
            if(flipped) {
                vx1 = IBlock.getVoxelShapeByDirection(0, 0, 13, 12, 19, 16, IBlock.getStatePropertySafe(state, FACING));
            } else {
                vx1 = IBlock.getVoxelShapeByDirection(0, 0, 0, 12, 19, 3, IBlock.getStatePropertySafe(state, FACING));
            }
        } else {
            vx1 = Shapes.empty();
        }
        return Shapes.or(mainBarrierShape, vx1);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        boolean hasFence = IBlock.getStatePropertySafe(state, FENCE_TYPE) != 0;
        boolean flipped = IBlock.getStatePropertySafe(state, FLIPPED);

        VoxelShape mainBarrierShape = IBlock.getVoxelShapeByDirection(12, 0, 0, 16, 24, 16, IBlock.getStatePropertySafe(state, FACING));

        VoxelShape vx1;
        if(hasFence) {
            if(flipped) {
                vx1 = IBlock.getVoxelShapeByDirection(0, 0, 13, 12, 24, 16, IBlock.getStatePropertySafe(state, FACING));
            } else {
                vx1 = IBlock.getVoxelShapeByDirection(0, 0, 0, 12, 24, 3, IBlock.getStatePropertySafe(state, FACING));
            }
        } else {
            vx1 = Shapes.empty();
        }
        return Shapes.or(mainBarrierShape, vx1);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return getFenceState(neighborState, direction, state, world, pos);
    }

    private BlockState getFenceState(BlockState stateNear, Direction direction, BlockState state, LevelAccessor world, BlockPos pos) {
        if(stateNear.getBlock() instanceof ThalesTicketBarrier || stateNear.getBlock() instanceof ThalesTicketBarrierBareBlock) {
            return state;
        }

        Direction thisDirection = IBlock.getStatePropertySafe(state, FACING);
        if(stateNear.getBlock() instanceof BlockGlassFence) {
            Direction nearbyFacing = IBlock.getStatePropertySafe(stateNear, FACING);
            boolean valid = (nearbyFacing == thisDirection) || (nearbyFacing == thisDirection.getOpposite());
            boolean flipped = nearbyFacing != thisDirection;

            /* Don't connect fence that are placed 90deg to the barrier */
            if(direction != thisDirection.getClockWise() && direction != thisDirection.getCounterClockWise()) {
                valid = false;
            }

            if(valid) {
                int fenceType = 0;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_CIO.get())) fenceType = 1;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_CKT.get())) fenceType = 2;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_HEO.get())) fenceType = 3;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_MOS.get())) fenceType = 4;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_PLAIN.get())) fenceType = 5;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_SHM.get())) fenceType = 6;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_STAINED.get())) fenceType = 7;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_STW.get())) fenceType = 8;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_TSH.get())) fenceType = 9;
                if(stateNear.is(mtr.Blocks.GLASS_FENCE_WKS.get())) fenceType = 10;
                return state.setValue(FENCE_TYPE, fenceType).setValue(FLIPPED, flipped);
            }
        }

        // Some builder prefer the fence to stay connected to a wall instead of getting updated without a fence, leaving a hole
        // In this case we don't touch the state and just leave it as is
        BlockPos nextToPos = pos.relative(thisDirection.getCounterClockWise());
        boolean hasBlockNextToFence = !world.getBlockState(nextToPos).isAir();
        if(hasBlockNextToFence) {
            return state;
        }

        return state.setValue(FENCE_TYPE, 0);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FENCE_TYPE, FLIPPED);
    }
}
