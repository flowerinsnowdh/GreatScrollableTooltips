package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
@Cancelable
public class PreScreenKeyPressedEvent extends Event {
    private final GuiContainer screen;
    private final char typedChar;
    private final int keyCode;

    public PreScreenKeyPressedEvent(GuiContainer screen, char typedChar, int keyCode) {
        this.screen = screen;
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public GuiContainer getScreen() {
        return this.screen;
    }

    public char getTypedChar() {
        return this.typedChar;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreScreenKeyPressedEvent that = (PreScreenKeyPressedEvent) o;
        return this.typedChar == that.typedChar && this.keyCode == that.keyCode && Objects.equals(this.screen, that.screen);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.screen != null ? this.screen.hashCode() : 0);
        result = 31 * result + (int) this.typedChar;
        result = 31 * result + this.keyCode;
        return result;
    }

    @Override
    public String toString() {
        return PreScreenKeyPressedEvent.class.getSimpleName() + "{" +
                "screen=" + this.screen +
                ", typedChar=" + this.typedChar +
                ", keyCode=" + this.keyCode +
                '}';
    }
}
