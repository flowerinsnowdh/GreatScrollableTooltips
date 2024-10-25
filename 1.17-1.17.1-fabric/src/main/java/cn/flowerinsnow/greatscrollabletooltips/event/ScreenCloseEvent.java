package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenCloseEvent {
    Event<ScreenCloseEvent> EVENT = EventFactory.createArrayBacked(ScreenCloseEvent.class, listeners -> (screen) -> {
        for (ScreenCloseEvent event : listeners) {
            ActionResult actionResult = event.onScreenClose(screen);
            if (actionResult != ActionResult.PASS) {
                return actionResult;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onScreenClose(Screen oldScreen);
}
