package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class MouseScrollListener {
    private final GreatScrollableTooltips main;

    public MouseScrollListener(GreatScrollableTooltips main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void onScreenMouseScroll(PreScreenMouseScrollEvent event) {
        if (!this.main.getConfig().enable) { // 只有启用时
            return;
        }

        ScrollSession<ItemStack> session = this.main.getScrollSession();

        if (!session.isRendering()) { // 只有渲染物品提示时才允许滚动
            return;
        }

        if (!GuiScreen.isShiftKeyDown()) {
            session.addVertical(event.getAmount());
        } else {
            session.addHorizontal(event.getAmount());
        }
    }
}
