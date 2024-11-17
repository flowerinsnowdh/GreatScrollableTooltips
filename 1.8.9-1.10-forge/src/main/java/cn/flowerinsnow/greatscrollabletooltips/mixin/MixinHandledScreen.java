package cn.flowerinsnow.greatscrollabletooltips.mixin;

import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    private final GuiContainer THIS = (GuiContainer) (Object) this;

    @Shadow
    private Slot theSlot;

    @Inject(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/InventoryPlayer;getItemStack()Lnet/minecraft/item/ItemStack;",
                    ordinal = 0
            )
    )
    public void preRenderTooltip(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        InventoryPlayer inventoryPlayer = this.mc.thePlayer.inventory;
        if (inventoryPlayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Pre(this.THIS, mouseX, mouseY, partialTicks, this.theSlot));
        } else {
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.Miss(this.THIS));
        }
    }

    @Inject(
            method = "keyTyped",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PreScreenKeyPressedEvent(this.THIS, typedChar, keyCode))) {
            ci.cancel();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int amount = Mouse.getEventDWheel();
        if (amount != 0) {
            if (amount > 1) {
                amount = 1;
            } else if (amount < -1) {
                amount = -1;
            }
            MinecraftForge.EVENT_BUS.post(new PreScreenMouseScrollEvent(this.THIS, amount));
        }
    }
}
