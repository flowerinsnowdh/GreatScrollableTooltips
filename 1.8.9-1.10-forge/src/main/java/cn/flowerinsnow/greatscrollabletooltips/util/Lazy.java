package cn.flowerinsnow.greatscrollabletooltips.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class Lazy<T> {
    private T value;
    private final Supplier<T> supplier;
    private boolean supplied;

    public Lazy(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    public T get() {
        if (this.supplied) {
            return this.value;
        }
        this.supplied = true;
        return (this.value = this.supplier.get());
    }
}