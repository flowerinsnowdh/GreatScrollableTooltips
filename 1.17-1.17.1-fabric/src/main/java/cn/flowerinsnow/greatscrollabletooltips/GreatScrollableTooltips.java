package cn.flowerinsnow.greatscrollabletooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.crash.CrashReport;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.common.provider.ModEnvironmentProvider;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.RenderTooltipEvent;
import cn.flowerinsnow.greatscrollabletooltips.event.ScreenCloseEvent;
import cn.flowerinsnow.greatscrollabletooltips.listener.EventTriggerListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.KeyScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.MouseScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.ScrollingStatusListener;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;

import java.io.InputStream;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class GreatScrollableTooltips implements ClientModInitializer {
	public static final String MODID = "great-scrollable-tooltips";

	private static GreatScrollableTooltips instance;

	private GreatScrollableTooltipsConfig config;

	private ScrollSession<ItemStack> scrollSession;

	@Override
	public void onInitializeClient() {
		GreatScrollableTooltips.instance = this;
		this.scrollSession = new ScrollSession<>();

		this.initConfig();
		this.initListeners();
		this.initKeyBindings();
	}

	private void initConfig() {
		this.config = new GreatScrollableTooltipsConfig(new ModEnvironmentProvider() {
			@Override
			public InputStream getDefaultConfigAsStream() {
				return GreatScrollableTooltips.class.getResourceAsStream("/config.toml");
			}

			@Override
			public Path getConfigFile() {
				return FabricLoader.getInstance().getConfigDir().resolve(GreatScrollableTooltips.MODID + ".toml");
			}

			@Override
			public void crash(Throwable throwable, String msg) {
				MinecraftClient.printCrashReport(CrashReport.create(throwable, msg));
			}
		});
		this.config.saveDefaultConfig();
		this.config.load();
	}

	private void initListeners() {
		ClientTickEvents.END_CLIENT_TICK.register(new EventTriggerListener());
		PreScreenKeyPressedEvent.EVENT.register(new KeyScrollListener(this));
		PreScreenMouseScrollEvent.EVENT.register(new MouseScrollListener(this));
		ScrollingStatusListener scrollingStatusListener = new ScrollingStatusListener(this);
		RenderTooltipEvent.Pre.EVENT.register(scrollingStatusListener);
		RenderTooltipEvent.Miss.EVENT.register(scrollingStatusListener);
		ScreenCloseEvent.EVENT.register(scrollingStatusListener);
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
