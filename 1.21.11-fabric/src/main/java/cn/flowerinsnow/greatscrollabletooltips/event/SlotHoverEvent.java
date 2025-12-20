package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface SlotHoverEvent {
    Event<SlotHoverEvent> EVENT = EventFactory.createArrayBacked(SlotHoverEvent.class, listeners -> ((screen, slot) -> {
        for (SlotHoverEvent listener : listeners) {
            InteractionResult interactionResult = listener.onSlotHover(screen, slot);
            if (interactionResult != InteractionResult.PASS) {
                return interactionResult;
            }
        }
        return InteractionResult.PASS;
    }));

    InteractionResult onSlotHover(AbstractContainerScreen<?> screen, @Nullable Slot slot);
}
