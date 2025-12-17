package cn.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionResult;

@Environment(EnvType.CLIENT)
public interface ScreenCloseEvent {
    Event<ScreenCloseEvent> EVENT = EventFactory.createArrayBacked(ScreenCloseEvent.class, listeners -> (screen) -> {
        for (ScreenCloseEvent event : listeners) {
            InteractionResult actionResult = event.onScreenClose(screen);
            if (actionResult != InteractionResult.PASS) {
                return actionResult;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onScreenClose(Screen oldScreen);
}
