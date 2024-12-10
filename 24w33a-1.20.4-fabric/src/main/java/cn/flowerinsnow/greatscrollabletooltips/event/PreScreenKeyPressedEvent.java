package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public interface PreScreenKeyPressedEvent {
    Event<PreScreenKeyPressedEvent> EVENT = EventFactory.createArrayBacked(PreScreenKeyPressedEvent.class, listeners -> (screen, keyCode, scanCode, modifiers) -> {
        for (PreScreenKeyPressedEvent listener : listeners) {
            ActionResult actionResult = listener.preScreenKeyPressed(screen, keyCode, scanCode, modifiers);
            if (actionResult != ActionResult.PASS) {
                return actionResult;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult preScreenKeyPressed(HandledScreen<?> screen, int keyCode, int scanCode, int modifiers);
}
