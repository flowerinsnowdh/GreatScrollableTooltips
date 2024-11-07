package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;

import java.util.Objects;

/**
 * 处理滚动状态
 */
public record ScrollingStatusListener(GreatScrollableTooltips main) {
    public ScrollingStatusListener(GreatScrollableTooltips main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void preRenderTooltip(RenderTooltipEvent.Pre event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setRendering(true);
        ItemStack itemStack = event.getSlot().getItem();
        if (itemStack != session.getLastItemStackRendered()) { // 如果正在渲染的物品和上一次渲染的物品不是同一个
            session.setLastItemStackRendered(itemStack);

            if (this.main.getConfig().autoReset) { // 仅当自动回正开启时自动回正
                session.resetScroll();
            }
        }
    }

    @SubscribeEvent
    public void missRenderTooltip(RenderTooltipEvent.Miss event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setRendering(false);
        session.setLastItemStackRendered(null);
        if (this.main.getConfig().autoReset) { // 仅当自动回正开启时自动回正
            session.resetScroll();
        }
    }

    @SubscribeEvent
    public void onScreenClose(ScreenCloseEvent event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setLastItemStackRendered(null);
        session.setRendering(false);
        session.resetScroll();
    }
}
