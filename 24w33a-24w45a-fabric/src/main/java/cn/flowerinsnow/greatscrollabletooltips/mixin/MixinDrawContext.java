package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DrawContext.class)
@Environment(EnvType.CLIENT)
public class MixinDrawContext {
    @ModifyVariable(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 12
    )
    public int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return x + (main.getScrollSession().getHorizontal() * main.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 13
    )
    public int modifyY(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return x + (main.getScrollSession().getVertical() * main.getConfig().sensitivity);
    }
}
