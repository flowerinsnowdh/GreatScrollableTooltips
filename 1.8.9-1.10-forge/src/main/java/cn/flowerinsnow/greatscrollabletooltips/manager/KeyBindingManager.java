package cn.flowerinsnow.greatscrollabletooltips.manager;

import cn.flowerinsnow.greatscrollabletooltips.util.Lazy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindingManager {
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_UP    = new Lazy<>(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-up",Keyboard.KEY_UP, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_LEFT  = new Lazy<>(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-left", Keyboard.KEY_LEFT, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_DOWN  = new Lazy<>(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-down", Keyboard.KEY_DOWN, "great-scrollable-tooltips.key-binding.category"));
    public static final Lazy<KeyBinding> KEY_BINDING_SCROLL_RIGHT = new Lazy<>(() -> new KeyBinding("great-scrollable-tooltips.key-binding.scroll-right", Keyboard.KEY_RIGHT, "great-scrollable-tooltips.key-binding.category"));

    public static void registerAll() {
        ClientRegistry.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_UP.get());
        ClientRegistry.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get());
        ClientRegistry.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get());
        ClientRegistry.registerKeyBinding(KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get());
    }
}
