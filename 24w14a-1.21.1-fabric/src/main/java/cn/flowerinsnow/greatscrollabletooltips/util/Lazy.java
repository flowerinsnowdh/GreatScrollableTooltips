package cn.flowerinsnow.greatscrollabletooltips.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Objects;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
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