/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import nex.init.NetherExMaterials;
import nex.util.BlockUtil;
import nex.util.NBTUtil;

public class ItemBoneHammer extends ItemNetherExPickaxe
{
    public ItemBoneHammer()
    {
        super("tool_hammer_bone", NetherExMaterials.TOOL_BONE_WITHERED_GOLD);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        NBTTagCompound compound = new NBTTagCompound();

        if(entity.dimension == DimensionType.NETHER.getId())
        {
            compound.setBoolean("Nether", true);
        }
        else
        {
            compound.setBoolean("Nether", false);
        }

        NBTUtil.setTag(stack, compound);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state)
    {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            if(stack.getTagCompound().getBoolean("Nether"))
            {
                return ToolMaterial.IRON.getHarvestLevel();
            }
        }

        return ToolMaterial.GOLD.getHarvestLevel();
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        boolean isNether = true;

        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            isNether = stack.getTagCompound().getBoolean("Nether");

            if(!isNether)
            {
                damage += 63;

                if(getDamage(stack) - 64 == 0)
                {
                    damage += 1;
                }
            }
        }

        super.setDamage(stack, isNether ? damage * 4 : damage);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
    {
        return BlockUtil.mine3x3(player.getEntityWorld(), stack, pos, player);
    }
}
