package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
public class EventTriggerListener implements ClientTickEvents.EndTick {
    private Screen oldScreen;

    @Override
    public void onEndTick(Minecraft client) {
        if (this.oldScreen != client.screen) {
            ScreenCloseEvent.EVENT.invoker().onScreenClose(this.oldScreen);
            this.oldScreen = client.screen;
        }
    }
}
