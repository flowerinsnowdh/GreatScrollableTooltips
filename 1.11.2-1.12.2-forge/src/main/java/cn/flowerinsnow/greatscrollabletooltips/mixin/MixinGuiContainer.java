package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.event.GuiContainerKeyTypedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
@SideOnly(Side.CLIENT)
public class MixinGuiContainer {
    private final GuiContainer THIS = (GuiContainer) (Object) this;

    @Inject(
            method = "func_73869_a",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void preKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new GuiContainerKeyTypedEvent.Pre(THIS, typedChar, keyCode))) {
            ci.cancel();
        }
    }

    @Inject(
            method = "func_73869_a",
            at = @At("RETURN"),
            remap = false
    )
    public void postKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new GuiContainerKeyTypedEvent.Post(THIS, typedChar, keyCode));
    }
}
