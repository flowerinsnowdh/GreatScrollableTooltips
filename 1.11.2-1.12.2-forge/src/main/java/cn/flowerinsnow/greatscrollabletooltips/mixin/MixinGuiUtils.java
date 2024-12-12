package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiUtils.class)
@SideOnly(Side.CLIENT)
public class MixinGuiUtils {
    @ModifyVariable(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 5
            ),
            index = 12,
            remap = false
    )
    private static int modifyTooltipX(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getHorizontal() * instance.getConfig().sensitivity;
    }

    @ModifyVariable(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 5
            ),
            index = 13,
            remap = false
    )
    private static int modifyTooltipY(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getVertical() * instance.getConfig().sensitivity;
    }
}
