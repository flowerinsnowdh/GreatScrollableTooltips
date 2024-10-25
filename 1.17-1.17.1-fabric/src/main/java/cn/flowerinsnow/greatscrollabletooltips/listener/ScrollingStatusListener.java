package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;

/**
 * 处理滚动状态
 */
public record ScrollingStatusListener(GreatScrollableTooltips main) implements RenderTooltipEvent.Pre, RenderTooltipEvent.Miss, ScreenCloseEvent {
    @Override
    public ActionResult preRenderTooltip(HandledScreen<?> screen, MatrixStack matrices, int x, int y, Slot focusedSlot) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setRendering(true);
        ItemStack itemStack = focusedSlot.getStack();
        if (itemStack != session.getLastItemStackRendered()) {
            session.setLastItemStackRendered(itemStack);

            if (this.main.getConfig().autoReset) {
                session.resetScroll();
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult missRenderTooltip(HandledScreen<?> screen) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setRendering(false);
        session.setLastItemStackRendered(null);
        if (this.main.getConfig().autoReset) {
            session.resetScroll();;
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult onScreenClose(Screen oldScreen) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setLastItemStackRendered(null);
        session.setRendering(false);
        session.resetScroll();
        return ActionResult.PASS;
    }
}
