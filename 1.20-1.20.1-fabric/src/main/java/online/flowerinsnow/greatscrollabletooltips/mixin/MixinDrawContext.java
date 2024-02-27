package online.flowerinsnow.greatscrollabletooltips.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import online.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DrawContext.class)
@Environment(EnvType.CLIENT)
public class MixinDrawContext {
    @ModifyVariable(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(
                    value = "LOAD",
                    target = "Lnet/minecraft/client/gui/DrawContext;draw(Ljava/lang/Runnable;)V",
                    ordinal = 0,
                    opcode = Opcodes.ILOAD
            ),
            index = 12
    )
    public int modifyY(int value) {
        return value + GreatScrollableTooltips.getInstance().getVertical() * GreatScrollableTooltips.getInstance().getConfig().sensitivity;
    }
}