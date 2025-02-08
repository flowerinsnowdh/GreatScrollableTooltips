package cn.flowerinsnow.greatscrollabletooltips.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderTooltipEvent extends Event {
    protected final AbstractContainerScreen<?> screen;

    protected RenderTooltipEvent(AbstractContainerScreen<?> screen) {
        this.screen = screen;
    }

    public AbstractContainerScreen<?> getScreen() {
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
        return RenderTooltipEvent.class.getSimpleName() + "{" +
                "screen=" + screen +
                '}';
    }

    public static class Pre extends RenderTooltipEvent {
        private final PoseStack poseStack;
        private final int x;
        private final int y;
        private final AbstractContainerMenu menu;
        private final Slot slot;

        public Pre(AbstractContainerScreen<?> screen, PoseStack poseStack, int x, int y, AbstractContainerMenu menu, Slot slot) {
            super(screen);
            this.poseStack = poseStack;
            this.x = x;
            this.y = y;
            this.menu = menu;
            this.slot = slot;
        }

        public PoseStack getPoseStack() {
            return this.poseStack;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public AbstractContainerMenu getMenu() {
            return menu;
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
            return this.x == that.x && this.y == that.y && Objects.equals(this.poseStack, that.poseStack) && Objects.equals(this.menu, that.menu) && Objects.equals(this.slot, that.slot);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + super.hashCode();
            result = 31 * result + (this.poseStack != null ? this.poseStack.hashCode() : 0);
            result = 31 * result + this.x;
            result = 31 * result + this.y;
            result = 31 * result + (this.menu != null ? this.menu.hashCode() : 0);
            result = 31 * result + (this.slot != null ? this.slot.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return Pre.class.getSimpleName() + "{" +
                    "super=" + super.toString() +
                    ", poseStack=" + this.poseStack +
                    ", x=" + this.x +
                    ", y=" + this.y +
                    ", menu=" + this.menu +
                    ", slot=" + this.slot +
                    '}';
        }
    }

    public static class Miss extends RenderTooltipEvent {
        public Miss(AbstractContainerScreen<?> screen) {
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
            return Miss.class.getSimpleName() + "{" +
                    "super=" + super.toString() +
                    '}';
        }
    }
}
