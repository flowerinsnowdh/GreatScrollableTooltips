package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.InteractionResult;

@Environment(EnvType.CLIENT)
public interface PreScreenKeyPressedEvent {
    Event<PreScreenKeyPressedEvent> EVENT = EventFactory.createArrayBacked(PreScreenKeyPressedEvent.class, listeners -> (screen, keyEvent) -> {
        for (PreScreenKeyPressedEvent listener : listeners) {
            InteractionResult interactionResult = listener.preScreenKeyPressed(screen, keyEvent);
            if (interactionResult != InteractionResult.PASS) {
                return interactionResult;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult preScreenKeyPressed(AbstractContainerScreen<?> screen, KeyEvent keyEvent);
}
