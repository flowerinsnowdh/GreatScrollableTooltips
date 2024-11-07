package cn.flowerinsnow.greatscrollabletooltips.mixin;

import net.minecraft.client.gui.components.AbstractSliderButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSliderButton.class)
public interface AccessorAbstractSliderButton {
    @Accessor("value")
    double getValue();
}
