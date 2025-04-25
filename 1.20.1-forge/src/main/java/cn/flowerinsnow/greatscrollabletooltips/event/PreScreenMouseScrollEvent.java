package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class PreScreenMouseScrollEvent extends Event {
    private final AbstractContainerScreen<?> screen;
    private final double mouseX;
    private final double mouseY;
    private final double amount;

    public PreScreenMouseScrollEvent(AbstractContainerScreen<?> screen, double mouseX, double mouseY, double amount) {
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.amount = amount;
    }

    public AbstractContainerScreen<?> getScreen() {
        return this.screen;
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreScreenMouseScrollEvent that = (PreScreenMouseScrollEvent) o;
        return Double.compare(this.mouseX, that.mouseX) == 0 && Double.compare(this.mouseY, that.mouseY) == 0 && Double.compare(this.amount, that.amount) == 0 && Objects.equals(this.screen, that.screen);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.screen != null ? this.screen.hashCode() : 0);
        result = 31 * result + Double.hashCode(this.mouseX);
        result = 31 * result + Double.hashCode(this.mouseY);
        result = 31 * result + Double.hashCode(this.amount);
        return result;
    }

    @Override
    public String toString() {
        return PreScreenMouseScrollEvent.class.getSimpleName() + "{" +
                "screen=" + this.screen +
                ", mouseX=" + this.mouseX +
                ", mouseY=" + this.mouseY +
                ", amount=" + this.amount +
                '}';
    }
}
