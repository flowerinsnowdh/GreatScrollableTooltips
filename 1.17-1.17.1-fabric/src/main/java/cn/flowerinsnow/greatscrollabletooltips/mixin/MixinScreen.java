package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Screen.class)
@Environment(EnvType.CLIENT)
public class MixinScreen {
    @ModifyVariable(
            method = "renderTooltipFromComponents",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 2
            ),
            index = 7
    )
    private int modifyX(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity;
    }

    @ModifyVariable(
            method = "renderTooltipFromComponents",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 1
            ),
            index = 8
    )
    private int modifyY(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getScrollSession().getVertical() * instance.getConfig().sensitivity;
    }
}
