package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.DrawHoveringTextEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiUtils.class)
@SideOnly(Side.CLIENT)
public class MixinGuiUtils {
    @Inject(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void preDrawHoveringText(ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new DrawHoveringTextEvent.Pre(stack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font))) {
            ci.cancel();
        }
    }

    @Inject(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At("RETURN"),
            remap = false
    )
    private static void postDrawHoveringText(ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new DrawHoveringTextEvent.Post(stack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font));
    }

    @ModifyVariable(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 5
            ),
            index = 12,
            remap = false
    )
    private static int modifyTooltipX(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getHorizontal() * instance.getConfig().sensitivity;
    }

    @ModifyVariable(
            method = "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 5
            ),
            index = 13,
            remap = false
    )
    private static int modifyTooltipY(int value) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        return value + instance.getVertical() * instance.getConfig().sensitivity;
    }
}
