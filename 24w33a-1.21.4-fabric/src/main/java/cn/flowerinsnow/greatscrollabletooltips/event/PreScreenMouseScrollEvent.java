package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public interface PreScreenMouseScrollEvent {
    Event<PreScreenMouseScrollEvent> EVENT = EventFactory.createArrayBacked(PreScreenMouseScrollEvent.class, listeners -> (screen, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
        for (PreScreenMouseScrollEvent listener : listeners) {
            ActionResult actionResult = listener.preScreenMouseScrolled(screen, mouseX, mouseY, horizontalAmount, verticalAmount);
            if (actionResult != ActionResult.PASS) {
                return actionResult;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult preScreenMouseScrolled(HandledScreen<?> screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount);
}
