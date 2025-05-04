package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
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
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 11
    )
    public int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();
        return x + (scrollSession.getVertical() * main.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 12
    )
    public int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();
        return y + (scrollSession.getHorizontal() * main.getConfig().sensitivity);
    }
}
