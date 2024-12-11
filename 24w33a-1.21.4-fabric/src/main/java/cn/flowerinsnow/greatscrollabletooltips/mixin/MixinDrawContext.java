package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.util.AbsoluteHoveredTooltipPositioner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DrawContext.class)
@Environment(EnvType.CLIENT)
public class MixinDrawContext {
    @Unique
    private List<TooltipComponent> headComponents;

    @Inject(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At("HEAD")
    )
    public void drawTooltipHead(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, @Nullable Identifier texture, CallbackInfo ci) {
        this.headComponents = components;
    }

    @ModifyVariable(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At("HEAD"),
            index = 5,
            argsOnly = true
    )
    public TooltipPositioner absoluteHoveredTooltipPositioner(TooltipPositioner value) {
        if (this.isBundleTooltip()) { // 如果是收纳袋选择的物品的提示
            return new AbsoluteHoveredTooltipPositioner(); // 删除防越界机制
        }
        return value;
    }

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
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();

        if (!this.isBundleTooltip()) {
            return x + (scrollSession.getHorizontal() * main.getConfig().sensitivity);
        }
        // 如果是收纳袋选择的物品的提示，则坐标会跟随上次更改，无需再次更改
        return x;
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
    public int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();

        if (!this.isBundleTooltip()) {
            return y + (scrollSession.getVertical() * main.getConfig().sensitivity);
        }
        // 如果是收纳袋选择的物品的提示，则坐标会跟随上次更改，无需再次更改
        return y;
    }

    @Unique
    private boolean isBundleTooltip() {
        ItemStack lastItemStackRendered = GreatScrollableTooltips.getInstance().getScrollSession().getLastItemStackRendered();
        // 收纳袋选择的物品提示的绘制是一个单独的 OrderedTextTooltipComponent
        return lastItemStackRendered != null && lastItemStackRendered.isIn(ItemTags.BUNDLES) && this.headComponents.size() == 1 && this.headComponents.getFirst() instanceof OrderedTextTooltipComponent;
    }
}
