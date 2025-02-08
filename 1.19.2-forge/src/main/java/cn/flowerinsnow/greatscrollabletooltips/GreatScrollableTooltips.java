package cn.flowerinsnow.greatscrollabletooltips;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.listener.*;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.common.provider.ModEnvironmentProvider;
import cn.flowerinsnow.greatscrollabletooltips.screen.ConfigScreen;

import java.io.InputStream;
import java.nio.file.Path;

@Mod(GreatScrollableTooltips.MODID)
public class GreatScrollableTooltips {
    public static final String MODID = "great_scrollable_tooltips";

    private static GreatScrollableTooltips instance;

    private GreatScrollableTooltipsConfig config;

    private ScrollSession<ItemStack> scrollSession;

    private final FMLJavaModLoadingContext context;

    public GreatScrollableTooltips(FMLJavaModLoadingContext context) {
        this.context = context;
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::onClientSetup);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        GreatScrollableTooltips.instance = this;
        this.scrollSession = new ScrollSession<>();

        this.initConfig();
        this.initListeners();
    }

    private void initConfig() {
        this.config = new GreatScrollableTooltipsConfig(new ModEnvironmentProvider() {
            @Override
            public InputStream getDefaultConfigAsStream() {
                return GreatScrollableTooltips.class.getResourceAsStream("/config.toml");
            }

            @Override
            public Path getConfigFile() {
                return FMLPaths.CONFIGDIR.get().resolve(GreatScrollableTooltips.MODID + ".toml");
            }

            @Override
            public void crash(Throwable throwable, String msg) {
                Minecraft.crash(CrashReport.forThrowable(throwable, msg));
            }
        });
        this.config.saveDefaultConfig();
        this.config.load();

        this.context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ConfigScreen(parent, GreatScrollableTooltips.this.config))
        );
    }

    private void initListeners() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        eventBus.register(new EventTriggerListener());

        // 鼠标滚动时
        eventBus.register(new MouseScrollListener(this));

        // 渲染物品提示时
        eventBus.register(new ScrollingStatusListener(this));

        // 按键滚动时
        eventBus.register(new KeyScrollListener(this));

        eventBus.addListener(this::initKeyBindings);
    }

    public void initKeyBindings(RegisterKeyMappingsEvent event) {
        KeyBindingManager.registerAll(event);
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
