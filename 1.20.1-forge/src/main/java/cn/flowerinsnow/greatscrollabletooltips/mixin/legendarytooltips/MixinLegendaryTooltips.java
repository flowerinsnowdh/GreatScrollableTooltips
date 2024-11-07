package cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips;

import com.anthonyhilyard.legendarytooltips.LegendaryTooltips;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LegendaryTooltips.class)
@OnlyIn(Dist.CLIENT)
public class MixinLegendaryTooltips {
    @ModifyArg(
            method = "onPostTooltipEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/anthonyhilyard/legendarytooltips/tooltip/TooltipDecor;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;IIII)V"
            ),
            remap = false,
            index = 1
    )
    private static int modifyDrawShadowX(int x) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return x + instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity;
    }

    @ModifyArg(
            method = "onPostTooltipEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/anthonyhilyard/legendarytooltips/tooltip/TooltipDecor;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;IIII)V"
            ),
            remap = false,
            index = 2
    )
    private static int modifyDrawShadowY(int y) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return y + instance.getScrollSession().getVertical() * instance.getConfig().sensitivity;
    }

    @ModifyArg(
            method = "onPostTooltipEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/anthonyhilyard/legendarytooltips/tooltip/TooltipDecor;drawBorder(Lcom/mojang/blaze3d/vertex/PoseStack;IIIILnet/minecraft/world/item/ItemStack;Ljava/util/List;Lnet/minecraft/client/gui/Font;Lcom/anthonyhilyard/legendarytooltips/config/LegendaryTooltipsConfig$FrameDefinition;ZI)V"
            ),
            remap = false,
            index = 1
    )
    private static int modifyDrawBorderX(int x) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return x + instance.getScrollSession().getHorizontal() * instance.getConfig().sensitivity;
    }

    @ModifyArg(
            method = "onPostTooltipEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/anthonyhilyard/legendarytooltips/tooltip/TooltipDecor;drawBorder(Lcom/mojang/blaze3d/vertex/PoseStack;IIIILnet/minecraft/world/item/ItemStack;Ljava/util/List;Lnet/minecraft/client/gui/Font;Lcom/anthonyhilyard/legendarytooltips/config/LegendaryTooltipsConfig$FrameDefinition;ZI)V"
            ),
            remap = false,
            index = 2
    )
    private static int modifyDrawBorderY(int y) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return y + instance.getScrollSession().getVertical() * instance.getConfig().sensitivity;
    }
}