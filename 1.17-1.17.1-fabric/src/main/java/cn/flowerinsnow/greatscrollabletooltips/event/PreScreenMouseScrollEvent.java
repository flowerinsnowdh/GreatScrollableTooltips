package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.util.ActionResult;

public interface PreScreenMouseScrollEvent {
    Event<PreScreenMouseScrollEvent> EVENT = EventFactory.createArrayBacked(PreScreenMouseScrollEvent.class, listeners -> (screen, mouseX, mouseY, amount) -> {
        for (PreScreenMouseScrollEvent listener : listeners) {
            ActionResult actionResult = listener.preScreenMouseScrolled(screen, mouseX, mouseY, amount);
            if (actionResult != ActionResult.PASS) {
                return actionResult;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult preScreenMouseScrolled(HandledScreen<?> screen, double mouseX, double mouseY, double amount);
}
