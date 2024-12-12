package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.DrawHoveringTextEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GreatScrollableTooltipsListener {
    private long lastEventNanoseconds = -1L;

    @SubscribeEvent
    public void onGuiClose(ScreenCloseEvent event) {
        GreatScrollableTooltips.getInstance().setVertical(0);
    }

    @SubscribeEvent
    public void preDrawHoveringText(DrawHoveringTextEvent.Pre event) {
        GreatScrollableTooltips instance = GreatScrollableTooltips.getInstance();
        if (Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative || !instance.getConfig().enable) {
            return;
        }
        int eventDWheel = Mouse.getEventDWheel();
        long eventNanoseconds = Mouse.getEventNanoseconds();
        if (lastEventNanoseconds != eventNanoseconds) {
            lastEventNanoseconds = eventNanoseconds;
            boolean shiftDown = GuiScreen.isShiftKeyDown();
            if (shiftDown) {
                instance.setHorizontal(instance.getHorizontal() + eventDWheel / 100);
            } else {
                instance.setVertical(instance.getVertical() + eventDWheel / 100);
            }
        }
    }
}
