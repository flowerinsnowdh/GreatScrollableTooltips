package cn.flowerinsnow.greatscrollabletooltips;

import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.common.provider.ModEnvironmentProvider;
import cn.flowerinsnow.greatscrollabletooltips.listener.KeyScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.MouseScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.ScrollingStatusListener;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.listener.EventTriggerListener;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

@Mod(
        modid = GreatScrollableTooltips.MODID,
        name = GreatScrollableTooltips.NAME,
        version = GreatScrollableTooltips.VERSION,
        clientSideOnly = true,
        guiFactory = "cn.flowerinsnow.greatscrollabletooltips.screen.GreatScrollableTooltipsGuiFactory",
        acceptedMinecraftVersions = "[1.12.2]"
)
@SideOnly(Side.CLIENT)
public class GreatScrollableTooltips {
    public static final String MODID = "great-scrollable-tooltips";
    public static final String NAME = "Great Scrollable Tooltips";
    public static final String VERSION = "4.3.0+forge";

    private static GreatScrollableTooltips instance;

    private GreatScrollableTooltipsConfig config;
    private ScrollSession<ItemStack> scrollSession;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GreatScrollableTooltips.instance = this;
        this.scrollSession = new ScrollSession<>();

        this.initConfig(event.getModConfigurationDirectory());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.initListeners();
        this.initKeyBindings();
    }

    private void initConfig(File configDir) {
        this.config = new GreatScrollableTooltipsConfig(new ModEnvironmentProvider() {
            @Override
            public InputStream getDefaultConfigAsStream() {
                return GreatScrollableTooltips.class.getResourceAsStream("/config.toml");
            }

            @Override
            public Path getConfigFile() {
                return configDir.toPath().resolve(GreatScrollableTooltips.MODID + ".toml");
            }

            @Override
            public void crash(Throwable throwable, String msg) {
                Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(throwable, msg));
            }
        });
        this.config.saveDefaultConfig();
        this.config.load();
    }

    private void initListeners() {
        EventBus eventBus = MinecraftForge.EVENT_BUS;

        eventBus.register(new EventTriggerListener());

        eventBus.register(new MouseScrollListener(this));
        eventBus.register(new ScrollingStatusListener(this));
        eventBus.register(new KeyScrollListener(this));
    }

    private void initKeyBindings() {
        KeyBindingManager.registerAll();
    }

    public static GreatScrollableTooltips getInstance() {
        return GreatScrollableTooltips.instance;
    }

    public GreatScrollableTooltipsConfig getConfig() {
        return this.config;
    }

    public ScrollSession<ItemStack> getScrollSession() {
        return this.scrollSession;
    }
}
