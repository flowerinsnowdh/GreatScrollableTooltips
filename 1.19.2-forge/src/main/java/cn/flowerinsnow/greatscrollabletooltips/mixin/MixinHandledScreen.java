package cn.flowerinsnow.greatscrollabletooltips.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
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

@Mixin(AbstractContainerScreen.class)
@OnlyIn(Dist.CLIENT)
public class MixinHandledScreen extends Screen {
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    private final AbstractContainerScreen<?> THIS = (AbstractContainerScreen<?>) (Object) this;

    @Shadow
    @Final
    protected AbstractContainerMenu menu;
    @Shadow
    protected Slot hoveredSlot;

    protected MixinHandledScreen() {
        //noinspection DataFlowIssue
        super(null);
    }

    @Inject(
            method = "renderTooltip",
            at = @At("HEAD")
    )
    public void renderTooltip(PoseStack poseStack, int x, int y, CallbackInfo ci) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Pre(this.THIS, poseStack, x, y, this.menu, this.hoveredSlot));
        } else {
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Miss(this.THIS));
        }
    }

    @Inject(
            method = "keyPressed",
            at = @At("HEAD")
    )
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        MinecraftForge.EVENT_BUS.post(new PreScreenKeyPressedEvent(this.THIS, keyCode, scanCode, modifiers));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        MinecraftForge.EVENT_BUS.post(new PreScreenMouseScrollEvent(this.THIS, mouseX, mouseY, amount));
        return super.mouseScrolled(mouseX, mouseY, amount);
    }
}
