package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiGraphics.class)
@Environment(EnvType.CLIENT)
public class MixinGuiGraphics {
    @ModifyVariable(
            method = "renderTooltip",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            index = 12

    )
    public int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();

        return x + (main.scrollSession().horizontalAmount() * main.config().sensitivity());
    }

    @ModifyVariable(
            method = "renderTooltip",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            index = 13

    )
    public int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();

        return y + (main.scrollSession().verticalAmount() * main.config().sensitivity());
    }
}
