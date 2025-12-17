package cn.flowerinsnow.greatscrollabletooltips.screen;

import cn.flowerinsnow.greatscrollabletooltips.config.GreatScrollableTooltipsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final GreatScrollableTooltipsConfig config;
    public ConfigScreen(Screen parent, GreatScrollableTooltipsConfig config) {
        super(Component.translatable("great-scrollable-tooltips.ui.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.enable() ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean enable = config.invertEnable();
                            config.save();
                            button.setMessage(Component.translatable(enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 48)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.autoReset() ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean autoReset = config.invertAutoReset();
                            config.save();
                            button.setMessage(Component.translatable(autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"));
                        }
                )
                        .tooltip(Tooltip.create(Component.translatable("great-scrollable-tooltips.ui.config.auto-reset.tooltip")))
                        .pos(this.width / 2 - 100, this.height / 2 - 23)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                new AbstractSliderButton(
                        this.width / 2 - 100, this.height / 2 + 2,
                        200, 20,
                        Component.translatable("great-scrollable-tooltips.ui.config.sensitivity", this.config.sensitivity()),
                        new BigDecimal(this.config.sensitivity())
                                .add(new BigDecimal(-1))
                                .divide(new BigDecimal(99), 2, RoundingMode.UP)
                                .doubleValue()
                ) {
                    @Override
                    protected void updateMessage() {
                        this.setMessage(
                                Component.translatable(
                                        "great-scrollable-tooltips.ui.config.sensitivity",
                                        BigDecimal.valueOf(this.value)
                                                .multiply(new BigDecimal(99))
                                                .add(BigDecimal.ONE)
                                                .setScale(0, RoundingMode.DOWN)
                                                .intValue()
                                )
                        );
                    }

                    @Override
                    protected void applyValue() {
                        GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                        config.sensitivity(BigDecimal.valueOf(this.value)
                                .multiply(new BigDecimal(99))
                                .add(BigDecimal.ONE)
                                .setScale(0, RoundingMode.DOWN)
                                .intValue());
                        config.save();
                    }
                }
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("great-scrollable-tooltips.ui.config.done"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            Minecraft.getInstance().setScreen(instance.parent);
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 + 27)
                        .size(200, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.render(graphics, mouseX, mouseY, a);
        graphics.drawCenteredString(
                this.font, Component.translatable("great-scrollable-tooltips.ui.title"),
                this.width / 2, 20, 0xFFFFFF
        );
    }
}