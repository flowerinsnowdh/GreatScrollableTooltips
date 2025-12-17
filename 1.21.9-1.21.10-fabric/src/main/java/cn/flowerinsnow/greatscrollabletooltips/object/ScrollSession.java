package cn.flowerinsnow.greatscrollabletooltips.object;

import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ScrollSession {
    private int horizontalAmount;
    private int verticalAmount;

    @Nullable private Slot lastSlotRendered;

    @Contract(pure = true)
    public int horizontalAmount() {
        return this.horizontalAmount;
    }

    @Contract(pure = true)
    public int verticalAmount() {
        return this.verticalAmount;
    }

    @Contract(pure = true)
    public @Nullable Slot lastSlotRendered() {
        return this.lastSlotRendered;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ScrollSession horizontalAmount(int horizontalAmount) {
        this.horizontalAmount = horizontalAmount;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int increaseHorizontalAmount() {
        return ++this.horizontalAmount;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int decreaseHorizontalAmount() {
        return --this.horizontalAmount;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int plusHorizontalAmount(int amount) {
        return this.horizontalAmount += amount;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public ScrollSession verticalAmount(int verticalAmount) {
        this.verticalAmount = verticalAmount;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int increaseVerticalAmount() {
        return ++this.verticalAmount;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int decreaseVerticalAmount() {
        return --this.verticalAmount;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(mutates = "this")
    public int plusVerticalAmount(int amount) {
        return this.verticalAmount += amount;
    }

    @Contract(value = "-> this", mutates = "this")
    public ScrollSession resetScrollAmount() {
        this.horizontalAmount = 0;
        this.verticalAmount = 0;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ScrollSession lastSlotRendered(@Nullable Slot lastSlotRendered) {
        this.lastSlotRendered = lastSlotRendered;
        return this;
    }

    @Contract(pure = true)
    public boolean hasItemRendering() {
        return this.lastSlotRendered() != null && this.lastSlotRendered().hasItem();
    }
}
