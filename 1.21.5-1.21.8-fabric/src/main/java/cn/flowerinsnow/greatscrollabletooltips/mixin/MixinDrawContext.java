package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(DrawContext.class)
@Environment(EnvType.CLIENT)
public class MixinDrawContext {
    @ModifyVariable(
            method = "drawTooltipImmediately",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 12

    )
    public int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();

        return x + (scrollSession.getHorizontal() * main.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "drawTooltipImmediately",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ISTORE,
                    ordinal = 0
            ),
            index = 13

    )
    public int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();

        return y + (scrollSession.getVertical() * main.getConfig().sensitivity);
    }
}
