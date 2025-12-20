package cn.flowerinsnow.greatscrollabletooltips.screen;

import cn.flowerinsnow.greatscrollabletooltips.config.GreatScrollableTooltipsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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
                        translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.enable", this.config.enable()),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean invert = config.invertEnable();
                            config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.enable", invert));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 60)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.auto-reset", this.config.autoReset()),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean invert = config.invertAutoReset();
                            config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.auto-reset", invert));
                        }
                )
                        .tooltip(Tooltip.create(Component.translatable("great-scrollable-tooltips.ui.config.auto-reset.tooltip")))
                        .pos(this.width / 2 - 100, this.height / 2 - 35)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                new AbstractSliderButton(
                        this.width / 2 - 100, this.height / 2 - 10,
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
                                .intValue())
                                .save();
                    }
                }
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.invert-scrolling.mouse.x", this.config.invertScrollingMouseX()),
                                button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean invert = config.invertInvertScrollingMouseX();
                            config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.invert-scrolling.mouse.x", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 + 15)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.invert-scrolling.mouse.y", this.config.invertScrollingMouseY()),
                                button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            boolean invert = config.invertInvertScrollingMouseY();
                            config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("great-scrollable-tooltips.ui.config.invert-scrolling.mouse.y", invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2 + 15)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("great-scrollable-tooltips.ui.config.done"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            Minecraft.getInstance().setScreen(instance.parent);
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 + 40)
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

    public static MutableComponent translateComponentWithParamTrueFalse(String key, boolean value) {
        return Component.translatable(key, translateComponentOfTrueFalse(value));
    }

    public static MutableComponent translateComponentOfTrueFalse(boolean value) {
        return value ? Component.translatable("great-scrollable-tooltips.ui.title.constant.true").withStyle(ChatFormatting.GREEN) : Component.translatable("great-scrollable-tooltips.ui.title.constant.false").withStyle(ChatFormatting.RED);
    }
}