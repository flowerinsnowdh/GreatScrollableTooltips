package online.flowerinsnow.greatscrollabletooltips.listener;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import online.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;

public class EventTriggerListener implements ClientTickEvents.EndTick {
    private Screen oldScreen;

    @Override
    public void onEndTick(MinecraftClient client) {
        if (this.oldScreen != client.currentScreen) {
            ScreenCloseEvent.EVENT.invoker().onScreenClose(this.oldScreen);
            this.oldScreen = client.currentScreen;
        }
    }
}
