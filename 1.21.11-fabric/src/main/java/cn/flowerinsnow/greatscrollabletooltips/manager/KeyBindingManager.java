package cn.flowerinsnow.greatscrollabletooltips.manager;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import cn.flowerinsnow.greatscrollabletooltips.util.Lazy;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindingManager {
    private static final Lazy<KeyMapping.Category> CATEGORY = new Lazy<>(() -> KeyMapping.Category.register(Identifier.fromNamespaceAndPath(GreatScrollableTooltips.MODID, "name")));

    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_UP = new Lazy<>(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-up", GLFW.GLFW_KEY_UP, CATEGORY.get()));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_LEFT = new Lazy<>(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-left", GLFW.GLFW_KEY_LEFT, CATEGORY.get()));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_DOWN = new Lazy<>(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-down", GLFW.GLFW_KEY_DOWN, CATEGORY.get()));
    public static final Lazy<KeyMapping> KEY_BINDING_SCROLL_RIGHT = new Lazy<>(() -> new KeyMapping("great-scrollable-tooltips.key-binding.scroll-right", GLFW.GLFW_KEY_RIGHT, CATEGORY.get()));

    public static void registerAll() {
        KeyBindingHelper.registerKeyBinding(KEY_BINDING_SCROLL_UP.get());
        KeyBindingHelper.registerKeyBinding(KEY_BINDING_SCROLL_LEFT.get());
        KeyBindingHelper.registerKeyBinding(KEY_BINDING_SCROLL_DOWN.get());
        KeyBindingHelper.registerKeyBinding(KEY_BINDING_SCROLL_RIGHT.get());
    }
}
