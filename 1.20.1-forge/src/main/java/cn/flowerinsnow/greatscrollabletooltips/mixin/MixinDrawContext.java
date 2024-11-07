package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiGraphics.class)
@OnlyIn(Dist.CLIENT)
public class MixinDrawContext {
    @ModifyVariable(
            method = "renderTooltipInternal",
            at = @At(
                    value = "STORE",
                    ordinal = 0,
                    opcode = Opcodes.ISTORE
            ),
            index = 12
    )
    private int modifyX(int x) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return x + (instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "renderTooltipInternal",
            at = @At(
                    value = "STORE",
                    ordinal = 0,
                    opcode = Opcodes.ISTORE
            ),
            index = 13
    )
    private int modifyY(int y) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return y + (instance.getScrollSession().getVertical() * instance.getConfig().sensitivity);
    }
}
