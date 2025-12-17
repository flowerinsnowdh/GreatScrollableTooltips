package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.event.SlotHoverEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
@Environment(EnvType.CLIENT)
public class MixinAbstractContainerScreen<T extends AbstractContainerMenu> extends Screen {
    @Unique
    @SuppressWarnings("unchecked")
    private final AbstractContainerScreen<T> THIS = (AbstractContainerScreen<T>) (Object) this;

    @Shadow
    protected Slot hoveredSlot;

    @Inject(
            method = "renderTooltip",
            at = @At("HEAD")
    )
    public void renderTooltip(GuiGraphics guiGraphics, int x, int y, CallbackInfo ci) {
        SlotHoverEvent.EVENT.invoker().onSlotHover(this.THIS, this.hoveredSlot);
    }

    @Inject(
            method = "keyPressed",
            at = @At("HEAD")
    )
    public void preKeyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        PreScreenKeyPressedEvent.EVENT.invoker().preScreenKeyPressed(this.THIS, keyEvent);
    }

    @Inject(
            method = "mouseScrolled",
            at = @At("TAIL")
    )
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
        PreScreenMouseScrollEvent.EVENT.invoker().preScreenMouseScrolled(this.THIS, mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    // 以下代码为为满足 Java 编译语法要求而没有实际意义的代码
    protected MixinAbstractContainerScreen() {
        super(null);
    }
}
