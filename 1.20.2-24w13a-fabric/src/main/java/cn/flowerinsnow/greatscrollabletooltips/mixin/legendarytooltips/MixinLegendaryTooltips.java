package cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips;

import com.anthonyhilyard.legendarytooltips.LegendaryTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LegendaryTooltips.class)
@Environment(EnvType.CLIENT)
public class MixinLegendaryTooltips {
    @ModifyVariable(
            method = "onPostTooltipEvent",
            at = @At("HEAD"),
            index = 2,
            argsOnly = true
    )
    private static int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return x + (main.getScrollSession().getVertical() * main.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "onPostTooltipEvent",
            at = @At("HEAD"),
            index = 3,
            argsOnly = true
    )
    private static int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return y + (main.getScrollSession().getHorizontal() * main.getConfig().sensitivity);
    }
}
