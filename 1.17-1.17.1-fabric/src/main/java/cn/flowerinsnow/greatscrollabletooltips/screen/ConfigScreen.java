package cn.flowerinsnow.greatscrollabletooltips.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.mixin.AccessorSliderWidget;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final GreatScrollableTooltipsConfig config;
    private SliderWidget sensitivitySlider;
    public ConfigScreen(Screen parent, GreatScrollableTooltipsConfig config) {
        super(new TranslatableText("great-scrollable-tooltips.ui.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100, this.height / 2 - 70,
                        200, 20,
                        new TranslatableText(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"),
                        button -> {
                            ConfigScreen screen = ConfigScreen.this;
                            if (screen.config.enable) {
                                button.setMessage(new TranslatableText("great-scrollable-tooltips.ui.config.enable.false"));
                                screen.config.enable = false;
                            } else {
                                button.setMessage(new TranslatableText("great-scrollable-tooltips.ui.config.enable.true"));
                                screen.config.enable = true;
                            }
                        }
                )
        );

        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100, this.height / 2 - 45,
                        200, 20,
                        new TranslatableText(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"),
                        button -> {
                            ConfigScreen screen = ConfigScreen.this;
                            if (screen.config.autoReset) {
                                button.setMessage(new TranslatableText("great-scrollable-tooltips.ui.config.auto-reset.false"));
                                screen.config.autoReset = false;
                            } else {
                                button.setMessage(new TranslatableText("great-scrollable-tooltips.ui.config.auto-reset.true"));
                                screen.config.autoReset = true;
                            }
                        }
                )
        );

        this.addDrawableChild(
                this.sensitivitySlider = new SliderWidget(
                        this.width / 2 - 100, this.height / 2 - 20,
                        200, 20,
                        new TranslatableText("great-scrollable-tooltips.ui.config.sensitivity", this.config.sensitivity),
                        new BigDecimal(this.config.sensitivity)
                                .add(new BigDecimal(-1))
                                .divide(new BigDecimal(99), 2, RoundingMode.DOWN)
                                .doubleValue()
                ) {
                    @Override
                    protected void updateMessage() {
                        this.setMessage(
                                new TranslatableText(
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
                    }
                }
        );

        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100, this.height / 2 + 5,
                        200, 20,
                        new TranslatableText("great-scrollable-tooltips.ui.config.reload"),
                        button -> {
                            ConfigScreen screen = ConfigScreen.this;
                            screen.config.load();
                            MinecraftClient.getInstance().currentScreen = new ConfigScreen(screen.parent, screen.config);
                        }
                )
        );

        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100, this.height / 2 + 30,
                        200, 20,
                        new TranslatableText("great-scrollable-tooltips.ui.config.save-and-exit"),
                        button -> {
                            ConfigScreen.this.config.sensitivity = BigDecimal.valueOf(((AccessorSliderWidget) sensitivitySlider).getValue())
                                    .multiply(new BigDecimal(99))
                                    .add(BigDecimal.ONE)
                                    .setScale(0, RoundingMode.DOWN)
                                    .intValue();
                            ConfigScreen.this.config.save();
                            MinecraftClient.getInstance().currentScreen = ConfigScreen.this.parent;
                        }
                )
        );

        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100, this.height / 2 + 55,
                        200, 20,
                        new TranslatableText("great-scrollable-tooltips.ui.config.discard-and-exit"),
                        button -> MinecraftClient.getInstance().currentScreen = ConfigScreen.this.parent
                )
        );
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        DrawableHelper.drawCenteredTextWithShadow(
                matrices, this.textRenderer,
                new TranslatableText("great-scrollable-tooltips.ui.title").asOrderedText(),
                this.width / 2, 20, 0xFFFFFF
        );
    }
}
