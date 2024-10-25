package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;

public interface RenderTooltipEvent {
    interface Pre {
        Event<Pre> EVENT = EventFactory.createArrayBacked(Pre.class, listeners -> (screen, matrices, x, y, focusedSlot) -> {
            for (Pre listener : listeners) {
                ActionResult actionResult = listener.preRenderTooltip(screen, matrices, x, y, focusedSlot);
                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult preRenderTooltip(HandledScreen<?> screen, MatrixStack matrices, int x, int y, Slot focusedSlot);
    }

    interface Miss {
        Event<Miss> EVENT = EventFactory.createArrayBacked(Miss.class, listeners -> screen -> {
            for (Miss listener : listeners) {
                ActionResult actionResult = listener.missRenderTooltip(screen);
                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult missRenderTooltip(HandledScreen<?> screen);
    }
}
