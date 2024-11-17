package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class RenderTooltipEvent extends net.minecraftforge.fml.common.eventhandler.Event {
    protected final GuiContainer screen;

    protected RenderTooltipEvent(GuiContainer screen) {
        this.screen = screen;
    }

    public GuiContainer getScreen() {
        return this.screen;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RenderTooltipEvent that = (RenderTooltipEvent) object;
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
        return "RenderMouseoverTooltipEvent{" +
                "screen=" + screen +
                '}';
    }

    public static class Pre extends RenderTooltipEvent {
        private final int x;
        private final int y;
        private final float partialTicks;
        private final Slot slot;

        public Pre(GuiContainer screen, int x, int y, float partialTicks, Slot slot) {
            super(screen);
            this.x = x;
            this.y = y;
            this.partialTicks = partialTicks;
            this.slot = slot;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public float getPartialTicks() {
            return partialTicks;
        }

        public Slot getSlot() {
            return this.slot;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;
            Pre that = (Pre) object;
            return this.x == that.x && this.y == that.y && Float.compare(this.partialTicks, that.partialTicks) == 0 && Objects.equals(this.slot, that.slot);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + super.hashCode();
            result = 31 * result + this.x;
            result = 31 * result + this.y;
            result = 31 * result + Float.hashCode(this.partialTicks);
            result = 31 * result + (this.slot != null ? this.slot.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Post{" +
                    "super=" + super.toString() +
                    ", x=" + this.x +
                    ", y=" + this.y +
                    ", partialTicks=" + this.partialTicks +
                    ", slot=" + this.slot +
                    '}';
        }
    }

    public static class Miss extends RenderTooltipEvent {
        public Miss(GuiContainer screen) {
            super(screen);
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + super.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Miss{" +
                    "super=" + super.toString() +
                    '}';
        }
    }
}
