package cn.flowerinsnow.greatscrollabletooltips.screen;

import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final GreatScrollableTooltipsConfig config;
    public ConfigScreen(Screen parent, GreatScrollableTooltipsConfig config) {
        super(Text.translatable("great-scrollable-tooltips.ui.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.translatable(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            config.enable = !config.enable;
                            config.save();
                            button.setMessage(Text.translatable(config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"));
                        }
                )
                        .position(this.width / 2 - 100, this.height / 2 - 48)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.translatable(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            config.autoReset = !config.autoReset;
                            config.save();
                            button.setMessage(Text.translatable(config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"));
                        }
                )
                        .tooltip(Tooltip.of(Text.translatable("great-scrollable-tooltips.ui.config.auto-reset.tooltip")))
                        .position(this.width / 2 - 100, this.height / 2 - 23)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                new SliderWidget(
                        this.width / 2 - 100, this.height / 2 + 2,
                        200, 20,
                        Text.translatable("great-scrollable-tooltips.ui.config.sensitivity", this.config.sensitivity),
                        new BigDecimal(this.config.sensitivity)
                                .add(new BigDecimal(-1))
                                .divide(new BigDecimal(99), 2, RoundingMode.UP)
                                .doubleValue()
                ) {
                    @Override
                    protected void updateMessage() {
                        this.setMessage(
                                Text.translatable(
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
                        config.sensitivity = BigDecimal.valueOf(this.value)
                                .multiply(new BigDecimal(99))
                                .add(BigDecimal.ONE)
                                .setScale(0, RoundingMode.DOWN)
                                .intValue();
                        config.save();
                    }
                }
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.translatable("great-scrollable-tooltips.ui.config.done"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            MinecraftClient.getInstance().setScreen(instance.parent);
                        }
                )
                        .position(this.width / 2 - 100, this.height / 2 + 27)
                        .size(200, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable("great-scrollable-tooltips.ui.title"),
                this.width / 2, 20, 0xFFFFFF
        );
    }
}