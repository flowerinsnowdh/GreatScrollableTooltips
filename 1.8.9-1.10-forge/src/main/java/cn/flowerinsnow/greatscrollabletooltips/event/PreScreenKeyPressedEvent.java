package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class PreScreenKeyPressedEvent extends Event {
    private final GuiScreen screen;
    private final char typedChar;
    private final int keyCode;

    public PreScreenKeyPressedEvent(GuiScreen screen, char typedChar, int keyCode) {
        this.screen = screen;
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }

    public GuiScreen getScreen() {
        return this.screen;
    }

    public char getTypedChar() {
        return this.typedChar;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PreScreenKeyPressedEvent that = (PreScreenKeyPressedEvent) object;
        return this.typedChar == that.typedChar && this.keyCode == that.keyCode && Objects.equals(this.screen, that.screen);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.screen != null ? this.screen.hashCode() : 0);
        result = 31 * result + this.typedChar;
        result = 31 * result + this.keyCode;
        return result;
    }

    @Override
    public String toString() {
        return "PreScreenKeyPressedEvent{" +
                "screen=" + this.screen +
                ", typedChar=" + this.typedChar +
                ", keyCode=" + this.keyCode +
                '}';
    }
}
