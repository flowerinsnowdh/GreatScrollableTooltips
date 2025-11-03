package cn.flowerinsnow.greatscrollabletooltips;

import cn.flowerinsnow.greatscrollabletooltips.util.ConfigScreenUtil;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
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

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Consumer;

@Mod(GreatScrollableTooltips.MODID)
public class GreatScrollableTooltips {
    public static final String MODID = "great_scrollable_tooltips";

    private static GreatScrollableTooltips instance;

    private GreatScrollableTooltipsConfig config;

    private ScrollSession<ItemStack> scrollSession;

    public GreatScrollableTooltips() {
        //noinspection removal : for old forge versions
        this(FMLJavaModLoadingContext.get());
    }

    public GreatScrollableTooltips(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> GreatScrollableTooltips.this.onClientSetup(context));
    }

    public void onClientSetup(FMLJavaModLoadingContext context) {
        GreatScrollableTooltips.instance = this;
        this.scrollSession = new ScrollSession<>();

        this.initConfig(context);
        this.initListeners();
        context.getModEventBus().addListener(this::initKeyBindings);
    }

    private void initConfig(FMLJavaModLoadingContext context) {
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
        ConfigScreenUtil.registerConfigScreen(context, this.config);
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
