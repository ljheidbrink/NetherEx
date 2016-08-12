package nex.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.api.block.NEXBlocks;

import java.util.List;
import java.util.Random;

public class BlockEnokiStem extends BlockBase
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockEnokiStem()
    {
        super("enoki_stem", Material.PLANTS, SoundType.WOOD);

        setHardness(0.4F);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!canSurviveAt(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        Block block1 = world.getBlockState(pos.up()).getBlock();
        Block block2 = world.getBlockState(pos.north()).getBlock();
        Block block3 = world.getBlockState(pos.east()).getBlock();
        Block block4 = world.getBlockState(pos.south()).getBlock();
        Block block5 = world.getBlockState(pos.west()).getBlock();

        return state.withProperty(DOWN, block == this || block == NEXBlocks.enokiCap).withProperty(UP, block1 == this || block1 == Blocks.NETHERRACK || block1 == NEXBlocks.netherrack || block1 == NEXBlocks.enokiCap).withProperty(NORTH, block2 == this || block2 == NEXBlocks.enokiCap).withProperty(EAST, block3 == this || block3 == NEXBlocks.enokiCap).withProperty(SOUTH, block4 == this || block4 == NEXBlocks.enokiCap).withProperty(WEST, block5 == this || block5 == NEXBlocks.enokiCap);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);

        float f1 = state.getValue(WEST) ? 0.0F : 0.1875F;
        float f2 = state.getValue(DOWN) ? 0.0F : 0.1875F;
        float f3 = state.getValue(NORTH) ? 0.0F : 0.1875F;
        float f4 = state.getValue(EAST) ? 1.0F : 0.8125F;
        float f5 = state.getValue(UP) ? 1.0F : 0.8125F;
        float f6 = state.getValue(SOUTH) ? 1.0F : 0.8125F;

        return new AxisAlignedBB((double) f1, (double) f2, (double) f3, (double) f4, (double) f5, (double) f6);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn)
    {
        state = state.getActualState(world, pos);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));

        if(state.getValue(WEST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
        }

        if(state.getValue(EAST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
        }

        if(state.getValue(UP))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
        }

        if(state.getValue(DOWN))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
        }

        if(state.getValue(NORTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
        }

        if(state.getValue(SOUTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && canSurviveAt(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
    {
        if(!canSurviveAt(world, pos))
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && (side != EnumFacing.UP);
    }

    private boolean canSurviveAt(World wordIn, BlockPos pos)
    {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());

        for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = wordIn.getBlockState(blockpos).getBlock();

            if(block == this)
            {
                if(!flag && !flag1)
                {
                    return false;
                }

                Block block1 = wordIn.getBlockState(blockpos.up()).getBlock();

                if(block1 == this || block1 == Blocks.NETHERRACK || block1 == NEXBlocks.netherrack)
                {
                    return true;
                }
            }
        }

        Block block2 = wordIn.getBlockState(pos.up()).getBlock();
        return block2 == this || block2 == Blocks.NETHERRACK || block2 == NEXBlocks.netherrack;
    }
}
