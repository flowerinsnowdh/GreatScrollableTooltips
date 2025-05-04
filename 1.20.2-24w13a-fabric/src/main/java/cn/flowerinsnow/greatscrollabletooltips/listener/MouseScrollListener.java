package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public record MouseScrollListener(GreatScrollableTooltips main) implements PreScreenMouseScrollEvent {
    @Override
    public ActionResult preScreenMouseScrolled(HandledScreen<?> screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!this.main.getConfig().enable) { // 只有启用时
            return ActionResult.PASS;
        }

        ScrollSession<ItemStack> session = this.main.getScrollSession();
        if (!session.isRendering()) { // 只有渲染物品提示时才允许滚动
            return ActionResult.PASS;
        }

        int horizontal = Double.compare(horizontalAmount, 0.0);
        int vertical = Double.compare(verticalAmount, 0.0);
        if (!Screen.hasShiftDown()) {
            session.addHorizontal(horizontal);
            session.addVertical(vertical);
        } else {
            session.addHorizontal(vertical);
            session.addVertical(horizontal);
        }
        return ActionResult.PASS;
    }
}
