package cn.flowerinsnow.greatscrollabletooltips.util;

import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.screen.ConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class ConfigScreenUtil {
    private ConfigScreenUtil() {
    }

    public static void registerConfigScreen(FMLJavaModLoadingContext context, GreatScrollableTooltipsConfig config) {
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class, () ->
                        new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ConfigScreen(parent, config))
        );
    }
}
