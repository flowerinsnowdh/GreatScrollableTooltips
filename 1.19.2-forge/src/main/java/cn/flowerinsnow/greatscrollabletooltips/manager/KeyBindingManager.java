package cn.flowerinsnow.greatscrollabletooltips.manager;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeyBindingManager {
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_UP = Lazy.of(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-up", GLFW.GLFW_KEY_UP, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_LEFT = Lazy.of(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-left", GLFW.GLFW_KEY_LEFT, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_DOWN = Lazy.of(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-down", GLFW.GLFW_KEY_DOWN, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_RIGHT = Lazy.of(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-right", GLFW.GLFW_KEY_RIGHT, "great-scrollable-tooltips.key-binding.category"));

    public static void registerAll(RegisterKeyMappingsEvent event) {
        event.register(KeyBindingManager.KEY_BINDING_SCROLL_UP.get());
        event.register(KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get());
        event.register(KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get());
        event.register(KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get());
    }
}
