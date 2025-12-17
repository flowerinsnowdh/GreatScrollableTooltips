package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.SlotHoverEvent;
import cn.flowerinsnow.greatscrollabletooltips.object.ScrollSession;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

/**
 * 处理滚动状态
 */
@Environment(EnvType.CLIENT)
public record ScrollingStatusListener(GreatScrollableTooltips main) implements SlotHoverEvent, ScreenCloseEvent {
    @Override
    public InteractionResult onSlotHover(AbstractContainerScreen<?> screen, @Nullable Slot slot) {
        ScrollSession session = this.main.scrollSession();
        if (slot != session.lastSlotRendered()) {
            session.lastSlotRendered(slot);
            if (this.main.config().autoReset()) {
                session.horizontalAmount(0)
                        .verticalAmount(0);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onScreenClose(Screen oldScreen) {
        this.main.scrollSession().lastSlotRendered(null)
                .horizontalAmount(0)
                .verticalAmount(0);
        return InteractionResult.PASS;
    }
}
