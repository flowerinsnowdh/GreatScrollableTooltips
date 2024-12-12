package cn.flowerinsnow.greatscrollabletooltips;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.config.Config;
import cn.flowerinsnow.greatscrollabletooltips.listener.CursorKeyListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.EventTrigger;
import cn.flowerinsnow.greatscrollabletooltips.listener.GreatScrollableTooltipsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;

@Mod(
        modid = GreatScrollableTooltips.MODID,
        name = GreatScrollableTooltips.NAME,
        version = GreatScrollableTooltips.VERSION,
        clientSideOnly = true,
        guiFactory = "cn.flowerinsnow.greatscrollabletooltips.screen.GreatScrollableTooltipsGuiFactory"
)
@SideOnly(Side.CLIENT)
public class GreatScrollableTooltips {
    public static final String MODID = "great-scrollable-tooltips";
    public static final String NAME = "Great Scrollable Tooltips";
    public static final String VERSION = "4.2.0";

    private static GreatScrollableTooltips instance;

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private Config config;

    private int horizontal;
    private int vertical;
    public static final KeyBinding KEY_BINDING_SCROLL_UP = new KeyBinding("great-scrollable-tooltips.key-binding.scroll-up", Keyboard.KEY_UP, "great-scrollable-tooltips.key-binding.category");
    public static final KeyBinding KEY_BINDING_SCROLL_LEFT = new KeyBinding("great-scrollable-tooltips.key-binding.scroll-left", Keyboard.KEY_LEFT, "great-scrollable-tooltips.key-binding.category");
    public static final KeyBinding KEY_BINDING_SCROLL_DOWN = new KeyBinding("great-scrollable-tooltips.key-binding.scroll-down", Keyboard.KEY_DOWN, "great-scrollable-tooltips.key-binding.category");
    public static final KeyBinding KEY_BINDING_SCROLL_RIGHT = new KeyBinding("great-scrollable-tooltips.key-binding.scroll-right", Keyboard.KEY_RIGHT, "great-scrollable-tooltips.key-binding.category");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        config = new Config(new File(event.getModConfigurationDirectory(), MODID + ".conf"));
        config.saveDefaultConfig();
        config.load();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new GreatScrollableTooltipsListener());
        MinecraftForge.EVENT_BUS.register(new EventTrigger());
        MinecraftForge.EVENT_BUS.register(new CursorKeyListener());

        ClientRegistry.registerKeyBinding(KEY_BINDING_SCROLL_UP);
        ClientRegistry.registerKeyBinding(KEY_BINDING_SCROLL_LEFT);
        ClientRegistry.registerKeyBinding(KEY_BINDING_SCROLL_DOWN);
        ClientRegistry.registerKeyBinding(KEY_BINDING_SCROLL_RIGHT);
    }

    public static GreatScrollableTooltips getInstance() {
        return instance;
    }

    public Config getConfig() {
        return config;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    @SuppressWarnings("UnusedReturnValue")
    public GreatScrollableTooltips setVertical(int vertical) {
        this.vertical = vertical;
        return this;
    }
}
