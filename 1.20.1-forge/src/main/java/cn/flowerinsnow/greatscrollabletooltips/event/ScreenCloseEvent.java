package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.eventbus.api.Event;

import java.util.Objects;

public class ScreenCloseEvent extends Event {
    public final Screen oldScreen;

    public ScreenCloseEvent(Screen oldScreen) {
        this.oldScreen = oldScreen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenCloseEvent that = (ScreenCloseEvent) o;
        return Objects.equals(this.oldScreen, that.oldScreen);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.oldScreen != null ? this.oldScreen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScreenCloseEvent{" +
                "oldScreen=" + this.oldScreen +
                '}';
    }
}
