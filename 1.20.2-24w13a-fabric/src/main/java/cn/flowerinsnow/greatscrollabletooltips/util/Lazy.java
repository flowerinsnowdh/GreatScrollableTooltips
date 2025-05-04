package cn.flowerinsnow.greatscrollabletooltips.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Objects;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class Lazy<T> {
    private T value;
    private Supplier<T> supplier;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (this.supplier == null) {
            return this.value;
        }
        this.value = this.supplier.get();
        this.supplier = null;
        return this.value;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return new Lazy<>(supplier);
    }
}