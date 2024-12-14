package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiContainer.class)
@SideOnly(Side.CLIENT)
public class MixinHandledScreen extends GuiScreen {
    @Unique
    private final GuiContainer THIS = (GuiContainer) (Object) this;

    @Shadow
    private Slot hoveredSlot;

    @Inject(
            method = "renderHoveredToolTip",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preRenderTooltip(int x, int y, CallbackInfo ci) {
        if (this.mc.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            if (MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Pre(this.THIS, x, y, this.hoveredSlot))) {
                ci.cancel();
            }
        } else {
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Miss(this.THIS));
        }
    }

    @Inject(
            method = "keyTyped",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void preKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PreScreenKeyPressedEvent(this.THIS, typedChar, keyCode))) {
            ci.cancel();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int amount = Mouse.getEventDWheel();
        if (amount != 0) {
            if (amount > 1) {
                amount = 1;
            } else if (amount < -1) {
                amount = -1;
            }
            if (MinecraftForge.EVENT_BUS.post(new PreScreenMouseScrollEvent(this.THIS, amount))) {
                return;
            }
        }
        super.handleMouseInput();
    }
}
