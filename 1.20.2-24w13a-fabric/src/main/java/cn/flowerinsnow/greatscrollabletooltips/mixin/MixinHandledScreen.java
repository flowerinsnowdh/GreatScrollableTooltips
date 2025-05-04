package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
@Environment(EnvType.CLIENT)
public abstract class MixinHandledScreen extends Screen {
    protected MixinHandledScreen() {
        super(null);
    }

    @Unique
    private final HandledScreen<?> THIS = (HandledScreen<?>) (Object) this;

    @Shadow
    @Final
    protected ScreenHandler handler;

    @Shadow
    protected Slot focusedSlot;

    @Inject(
            method = "drawMouseoverTooltip",
            at = @At("HEAD")
    )
    public void drawMouseoverTooltip(DrawContext context, int x, int y, CallbackInfo ci) {
        if (this.handler.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) { // 与原版判断一样，是否需要渲染物品提示
            RenderTooltipEvent.Pre.EVENT.invoker().preRenderTooltip(this.THIS, context, x, y, this.handler, this.focusedSlot);
        } else {
            RenderTooltipEvent.Miss.EVENT.invoker().missRenderTooltip(this.THIS);
        }
    }

    @Inject(
            method = "keyPressed",
            at = @At("HEAD")
    )
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        PreScreenKeyPressedEvent.EVENT.invoker().preScreenKeyPressed(this.THIS, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        PreScreenMouseScrollEvent.EVENT.invoker().preScreenMouseScrolled(this.THIS, mouseX, mouseY, verticalAmount, horizontalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
