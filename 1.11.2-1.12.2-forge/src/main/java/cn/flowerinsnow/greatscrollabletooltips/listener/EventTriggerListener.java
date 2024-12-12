package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.event.ClientTickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;

@SideOnly(Side.CLIENT)
public class EventTriggerListener {
    private GuiScreen oldScreen;

    @SubscribeEvent
    public void clientTickEndTrigger(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MinecraftForge.EVENT_BUS.post(new ClientTickEndEvent());
        }
    }

    @SubscribeEvent
    public void screenCloseTrigger(ClientTickEndEvent event) {
        GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        if (this.oldScreen != currentScreen) {
            if (this.oldScreen != null) {
                MinecraftForge.EVENT_BUS.post(new ScreenCloseEvent(this.oldScreen));
            }
            this.oldScreen = currentScreen;
        }
    }
}
