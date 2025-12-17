package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.InteractionResult;

@Environment(EnvType.CLIENT)
public interface PreScreenMouseScrollEvent {
    Event<PreScreenMouseScrollEvent> EVENT = EventFactory.createArrayBacked(PreScreenMouseScrollEvent.class, listeners -> (screen, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
        for (PreScreenMouseScrollEvent listener : listeners) {
            InteractionResult interactionResult = listener.preScreenMouseScrolled(screen, mouseX, mouseY, horizontalAmount, verticalAmount);
            if (interactionResult != InteractionResult.PASS) {
                return interactionResult;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult preScreenMouseScrolled(AbstractContainerScreen<?> screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount);
}
