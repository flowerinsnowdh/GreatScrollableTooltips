package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
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
public class MixinHandledScreen<T extends ScreenHandler> extends Screen {
    @Unique
    @SuppressWarnings("unchecked")
    private final HandledScreen<T> THIS = (HandledScreen<T>) (Object) this;

    @Shadow
    @Final
    protected T handler;

    @Shadow
    protected Slot focusedSlot;

    protected MixinHandledScreen() {
        super(null);
    }

    @Inject(
            method = "drawMouseoverTooltip",
            at = @At("HEAD")
    )
    public void drawMouseoverTooltip(DrawContext context, int x, int y, CallbackInfo ci) {
        if (this.handler.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) {
            RenderTooltipEvent.Pre.EVENT.invoker().preRenderTooltip(this.THIS, context, x, y, this.focusedSlot);
        } else {
            RenderTooltipEvent.Miss.EVENT.invoker().missRenderTooltip(this.THIS);
        }
    }

    @Inject(
            method = "keyPressed",
            at = @At("HEAD")
    )
    public void preKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        PreScreenKeyPressedEvent.EVENT.invoker().preScreenKeyPressed(this.THIS, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        PreScreenMouseScrollEvent.EVENT.invoker().preScreenMouseScrolled(this.THIS, mouseX, mouseY, horizontalAmount, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
