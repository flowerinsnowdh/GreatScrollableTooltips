package cn.flowerinsnow.greatscrollabletooltips.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import cn.flowerinsnow.greatscrollabletooltips.mixin.AccessorAbstractSliderButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final GreatScrollableTooltipsConfig config;
    private AbstractSliderButton sensitivitySlider;
    public ConfigScreen(Screen parent, GreatScrollableTooltipsConfig config) {
        super(Component.translatable("great-scrollable-tooltips.ui.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                new Button.Builder(
                        Component.translatable(
                                this.config.enable ?
                                        "great-scrollable-tooltips.ui.config.enable.true" :
                                        "great-scrollable-tooltips.ui.config.enable.false"
                        ),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            if (config.enable) {
                                button.setMessage(Component.translatable("great-scrollable-tooltips.ui.config.enable.false"));
                                config.enable = false;
                            } else {
                                button.setMessage(Component.translatable("great-scrollable-tooltips.ui.config.enable.true"));
                                config.enable = true;
                            }
                        })
                        .pos(this.width / 2 - 100, this.height / 2 - 70)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                new Button.Builder(
                        Component.translatable(
                                this.config.autoReset ?
                                        "great-scrollable-tooltips.ui.config.auto-reset.true" :
                                        "great-scrollable-tooltips.ui.config.auto-reset.false"
                        ),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            if (config.autoReset) {
                                button.setMessage(Component.translatable("great-scrollable-tooltips.ui.config.auto-reset.false"));
                                config.autoReset = false;
                            } else {
                                button.setMessage(Component.translatable("great-scrollable-tooltips.ui.config.auto-reset.true"));
                                config.autoReset = true;
                            }
                        })
                        .pos(this.width / 2 - 100, this.height / 2 - 45)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                this.sensitivitySlider = new AbstractSliderButton(
                        this.width / 2 - 100, this.height / 2 - 20,
                        200, 20,
                        Component.translatable(
                                "great-scrollable-tooltips.ui.config.sensitivity",
                                this.config.sensitivity
                        ),
                        new BigDecimal(this.config.sensitivity)
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
                        ));
                    }

                    @Override
                    protected void applyValue() {
                    }
                }
        );
        this.addRenderableWidget(
                new Button.Builder(
                        Component.translatable("great-scrollable-tooltips.ui.config.reload"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            instance.config.load();
                            Minecraft.getInstance().setScreen(new ConfigScreen(instance.parent, instance.config));
                        })
                        .pos(this.width / 2 - 100, this.height / 2 + 5)
                        .size(200, 20)
                        .build()
        );
        this.addRenderableWidget(
                new Button.Builder(
                        Component.translatable("great-scrollable-tooltips.ui.config.save-and-exit"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            GreatScrollableTooltipsConfig config = instance.config;
                            // config.sensitivity = BigDecimal.valueOf(instance.sensitivitySlider.value)
                            AccessorAbstractSliderButton accessor = (AccessorAbstractSliderButton) (instance.sensitivitySlider);
                            config.sensitivity = BigDecimal.valueOf(accessor.getValue())
                                    .multiply(new BigDecimal(99))
                                    .add(BigDecimal.ONE)
                                    .setScale(0, RoundingMode.DOWN)
                                    .intValue();
                            config.save();
                            Minecraft.getInstance().setScreen(instance.parent);
                        })
                        .pos(this.width / 2 - 100, this.height / 2 + 30)
                        .size(200, 20)
                        .build()
        );
        this.addRenderableWidget(
                new Button.Builder(
                        Component.translatable("great-scrollable-tooltips.ui.config.discard-and-exit"),
                        button -> Minecraft.getInstance().setScreen(ConfigScreen.this.parent)
                )
                        .pos(this.width / 2 - 100, this.height / 2 + 55)
                        .size(200, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(
                this.font,
                Component.translatable("great-scrollable-tooltips.ui.title"),
                this.width / 2, 20, 0xFFFFFF
        );
    }
}
