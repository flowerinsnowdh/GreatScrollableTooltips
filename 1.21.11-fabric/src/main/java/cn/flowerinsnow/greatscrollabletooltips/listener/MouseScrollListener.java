package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.object.ScrollSession;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.InteractionResult;

@Environment(EnvType.CLIENT)
public record MouseScrollListener(GreatScrollableTooltips main) implements PreScreenMouseScrollEvent {
    @Override
    public InteractionResult preScreenMouseScrolled(AbstractContainerScreen<?> screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!this.main.config().enable()) {
            return InteractionResult.PASS;
        }

        final ScrollSession session = this.main.scrollSession();
        if (!session.hasItemRendering()) {
            return InteractionResult.PASS;
        }

        int i = Double.compare(horizontalAmount, 0.0) * (this.main.config().invertScrollingMouseX() ? -1 : 1);
        if (!Minecraft.getInstance().hasShiftDown()) {
            session.plusHorizontalAmount(i);
        } else {
            session.plusVerticalAmount(i);
        }

        i = Double.compare(verticalAmount, 0.0) * (this.main.config().invertScrollingMouseY() ? -1 : 1);
        if (!Minecraft.getInstance().hasShiftDown()) {
            session.plusVerticalAmount(i);
        } else {
            session.plusHorizontalAmount(i);
        }
        return InteractionResult.PASS;
    }
}
