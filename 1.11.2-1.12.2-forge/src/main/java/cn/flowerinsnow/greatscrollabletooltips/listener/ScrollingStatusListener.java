package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class ScrollingStatusListener {
    private final GreatScrollableTooltips main;

    public ScrollingStatusListener(GreatScrollableTooltips main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void preRenderTooltip(RenderTooltipEvent.Pre event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        ItemStack itemStack = event.getHoveredSlot().getStack();
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
        session.setLastItemStackRendered(null);
        if (this.main.getConfig().autoReset) { // 仅当自动回正开启时自动回正
            session.resetScroll();
        }
    }

    @SubscribeEvent
    public void onScreenClose(ScreenCloseEvent event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        session.setLastItemStackRendered(null);
        session.resetScroll();
    }
}
