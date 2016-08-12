package nex.block;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.item.ItemBlockVariantContainer;

/**
 * This is a base implementation for blocks
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/block/base/BlockMod.java
 */
public class BlockBase extends Block implements IVariantContainer
{
    private final String[] VARIANTS;

    public BlockBase(String name, Material material, SoundType type, String... variants)
    {
        super(material);

        if(variants.length == 0)
        {
            variants = new String[]{name};
        }

        VARIANTS = variants;

        setSoundType(type);
        setCreativeTab(NetherEx.CREATIVE_TAB);

        if(isBaseClass())
        {
            registerAndSetName(name);
        }
    }

    @Override
    public String[] getVariants()
    {
        return VARIANTS;
    }

    public boolean isBaseClass()
    {
        return true;
    }

    public void registerAndSetName(String name)
    {
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockVariantContainer(this), getRegistryName());
    }
}
