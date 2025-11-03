package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public interface RenderTooltipEvent {
    interface Pre {
        Event<Pre> EVENT = EventFactory.createArrayBacked(Pre.class, listeners -> (screen, context, x, y, focusedSlot) -> {
            for (Pre listener : listeners) {
                ActionResult actionResult = listener.preRenderTooltip(screen, context, x, y, focusedSlot);
                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult preRenderTooltip(HandledScreen<?> screen, DrawContext context, int x, int y, Slot focusedSlot);
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
