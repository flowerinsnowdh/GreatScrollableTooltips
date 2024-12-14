package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class RenderTooltipEvent extends Event {
    protected final GuiContainer screen;

    protected RenderTooltipEvent(GuiContainer screen) {
        this.screen = screen;
    }

    public GuiContainer getScreen() {
        return this.screen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenderTooltipEvent that = (RenderTooltipEvent) o;
        return Objects.equals(this.screen, that.screen);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.screen != null ? this.screen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return RenderTooltipEvent.class.getSimpleName() + "{" +
                "screen=" + this.screen +
                '}';
    }

    @Cancelable
    public static class Pre extends RenderTooltipEvent {
        private final int x;
        private final int y;
        private final Slot hoveredSlot;

        public Pre(GuiContainer screen, int x, int y, Slot hoveredSlot) {
            super(screen);
            this.x = x;
            this.y = y;
            this.hoveredSlot = hoveredSlot;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public Slot getHoveredSlot() {
            return this.hoveredSlot;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Pre pre = (Pre) o;
            return this.x == pre.x && this.y == pre.y && Objects.equals(this.hoveredSlot, pre.hoveredSlot);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + super.hashCode();
            result = 31 * result + this.x;
            result = 31 * result + this.y;
            result = 31 * result + (this.hoveredSlot != null ? this.hoveredSlot.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return Pre.class.getSimpleName() + "{" +
                    "super=" + super.toString() +
                    ", x=" + this.x +
                    ", y=" + this.y +
                    ", hoveredSlot=" + this.hoveredSlot +
                    '}';
        }
    }

    public static class Miss extends RenderTooltipEvent {
        public Miss(GuiContainer screen) {
            super(screen);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + super.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return Miss.class.getSimpleName() + "{" +
                    "super=" + super.toString() +
                    "}";
        }
    }
}
