package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ClientTickEndEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;

public class EventTriggerListener {
    @SubscribeEvent
    public void onClientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MinecraftForge.EVENT_BUS.post(new ClientTickEndEvent());
        }
    }

    private Screen oldScreen;
    @SubscribeEvent
    public void onScreenClose(ClientTickEndEvent event) {
        Screen currentScreen = Minecraft.getInstance().screen;
        if (this.oldScreen != currentScreen) {
            if (this.oldScreen != null) {
                MinecraftForge.EVENT_BUS.post(new ScreenCloseEvent(this.oldScreen));
            }
            this.oldScreen = currentScreen;
        }
    }
}
