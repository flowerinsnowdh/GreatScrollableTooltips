package cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips;

import com.anthonyhilyard.legendarytooltips.tooltip.ItemModelComponent;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelComponent.class)
@Environment(EnvType.CLIENT)
public class MixinItemModelComponent {
    @ModifyVariable(
            method = "drawItems",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 0
            ),
            index = 2,
            argsOnly = true
    )
    public int modifyX(int x) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return x + (main.getScrollSession().getVertical() + main.getConfig().sensitivity);
    }

    @ModifyVariable(
            method = "drawItems",
            at = @At(
                    value = "LOAD",
                    opcode = Opcodes.ILOAD,
                    ordinal = 0
            ),
            index = 3,
            argsOnly = true
    )
    public int modifyY(int y) {
        GreatScrollableTooltips main = GreatScrollableTooltips.getInstance();
        return y + (main.getScrollSession().getHorizontal() + main.getConfig().sensitivity);
    }
}
