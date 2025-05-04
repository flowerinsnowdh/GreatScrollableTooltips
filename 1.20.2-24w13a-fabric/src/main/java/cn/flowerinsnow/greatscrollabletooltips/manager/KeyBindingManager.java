package cn.flowerinsnow.greatscrollabletooltips.manager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import cn.flowerinsnow.greatscrollabletooltips.util.Lazy;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindingManager {
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_UP = Lazy.of(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-up", GLFW.GLFW_KEY_UP, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_LEFT = Lazy.of(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-left", GLFW.GLFW_KEY_LEFT, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_DOWN = Lazy.of(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-down", GLFW.GLFW_KEY_DOWN, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_RIGHT = Lazy.of(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-right", GLFW.GLFW_KEY_RIGHT, "great-scrollable-tooltips.key-binding.category"));

    public static void registerAll() {
        KeyBindingHelper.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_UP.get());
        KeyBindingHelper.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get());
        KeyBindingHelper.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get());
        KeyBindingHelper.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get());
    }
}
