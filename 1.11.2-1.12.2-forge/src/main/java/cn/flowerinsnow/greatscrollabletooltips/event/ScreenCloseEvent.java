package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class ScreenCloseEvent extends Event {
    public final GuiScreen screen;

    public ScreenCloseEvent(GuiScreen screen) {
        this.screen = Objects.requireNonNull(screen);
    }
}
