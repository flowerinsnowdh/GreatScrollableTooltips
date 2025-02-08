package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Screen.class)
@OnlyIn(Dist.CLIENT)
public class MixinScreen {
    @ModifyVariable(
            method = "renderTooltipInternal",
            at = @At(
                    value = "LOAD",
                    ordinal = 2,
                    opcode = Opcodes.ILOAD
            ),
            index = 8
    )
    private int modifyX(int x) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return x + (instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "renderTooltipInternal",
            at = @At(
                    value = "LOAD",
                    ordinal = 1,
                    opcode = Opcodes.ILOAD
            ),
            index = 9
    )
    private int modifyY(int y) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return y + (instance.getScrollSession().getVertical() * instance.getConfig().sensitivity);
    }
}
