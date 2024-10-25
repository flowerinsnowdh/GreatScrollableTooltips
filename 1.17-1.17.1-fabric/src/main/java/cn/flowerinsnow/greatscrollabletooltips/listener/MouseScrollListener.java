package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;

public record MouseScrollListener(GreatScrollableTooltips main) implements PreScreenMouseScrollEvent {
    @Override
    public ActionResult preScreenMouseScrolled(HandledScreen<?> screen, double mouseX, double mouseY, double amount) {
        if (!this.main.getConfig().enable) {
            return ActionResult.PASS;
        }

        ScrollSession<ItemStack> session = this.main.getScrollSession();
        if (!session.isRendering()) {
            return ActionResult.PASS;
        }

        int i = Double.compare(amount, 0.0);
        if (!Screen.hasShiftDown()) {
            session.addVertical(i);
        } else {
            session.addHorizontal(i);
        }
        return ActionResult.PASS;
    }
}
