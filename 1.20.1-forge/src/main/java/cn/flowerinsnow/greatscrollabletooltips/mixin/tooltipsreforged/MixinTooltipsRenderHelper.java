package cn.flowerinsnow.greatscrollabletooltips.mixin.tooltipsreforged;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import com.iafenvoy.tooltipsreforged.render.TooltipsRenderHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TooltipsRenderHelper.class)
public class MixinTooltipsRenderHelper {
    @ModifyVariable(
            method = "drawTooltip",
            at = @At(
                    value = "STORE",
                    ordinal = 1,
                    opcode = Opcodes.ISTORE
            ),
            index = 19,
            remap = false
    )
    private static int modifyX(int x) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return x + (instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "drawTooltip",
            at = @At(
                    value = "STORE",
                    ordinal = 1,
                    opcode = Opcodes.ISTORE
            ),
            index = 20,
            remap = false
    )
    private static int modifyY(int y) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return y + (instance.getScrollSession().getVertical() * instance.getConfig().sensitivity);
    }
}
