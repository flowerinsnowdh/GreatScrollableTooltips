package cn.flowerinsnow.greatscrollabletooltips;

import cn.flowerinsnow.greatscrollabletooltips.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.event.*;
import cn.flowerinsnow.greatscrollabletooltips.object.ScrollSession;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import cn.flowerinsnow.greatscrollabletooltips.listener.EventTriggerListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.KeyScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.MouseScrollListener;
import cn.flowerinsnow.greatscrollabletooltips.listener.ScrollingStatusListener;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public class GreatScrollableTooltips implements ClientModInitializer {
	public static final String MODID = "great-scrollable-tooltips";

	private static GreatScrollableTooltips instance;

	private GreatScrollableTooltipsConfig config;

	private ScrollSession scrollSession;

	@Override
	public void onInitializeClient() {
		GreatScrollableTooltips.instance = this;
		this.scrollSession = new ScrollSession();

		this.initConfig();
		this.initListeners();
		this.initKeyBindings();
	}

	private void initConfig() {
		this.config = new GreatScrollableTooltipsConfig();
		this.config.init();
	}

	private void initListeners() {
		ClientTickEvents.END_CLIENT_TICK.register(new EventTriggerListener());
		PreScreenKeyPressedEvent.EVENT.register(new KeyScrollListener(this));
		PreScreenMouseScrollEvent.EVENT.register(new MouseScrollListener(this));
		ScrollingStatusListener scrollingStatusListener = new ScrollingStatusListener(this);
		SlotHoverEvent.EVENT.register(scrollingStatusListener);
		ScreenCloseEvent.EVENT.register(scrollingStatusListener);
	}

	private void initKeyBindings() {
		KeyBindingManager.registerAll();
	}

	public static GreatScrollableTooltips getInstance() {
		return GreatScrollableTooltips.instance;
	}

	public GreatScrollableTooltipsConfig config() {
		return this.config;
	}

	@Contract(pure = true)
	public ScrollSession scrollSession() {
		return this.scrollSession;
	}
}
