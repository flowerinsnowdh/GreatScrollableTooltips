package cn.flowerinsnow.greatscrollabletooltips.mixin.obscuretooltips;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import com.obscuria.tooltips.client.renderer.TooltipRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TooltipRenderer.class)
@OnlyIn(Dist.CLIENT)
public class MixinTooltipRenderer {
    // Line 49: Vector2ic rawPos = positioner.positionTooltip(renderer.width(), renderer.height(), x, y, size.x, size.y);
    @ModifyVariable(
            method = "render",
            at = @At(
                    value = "STORE",
                    opcode = Opcodes.ASTORE,
                    ordinal = 0
            ),
            index = 9,
            remap = false
    )
    private static Vector2ic render(Vector2ic value) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        GreatScrollableTooltipsConfig config = main.getConfig();
        ScrollSession<ItemStack> scrollSession = main.getScrollSession();

        return new Vector2i(
                value.x() + scrollSession.getHorizontal() * config.sensitivity,
                value.y() + scrollSession.getVertical() * config.sensitivity
        );
    }
}
