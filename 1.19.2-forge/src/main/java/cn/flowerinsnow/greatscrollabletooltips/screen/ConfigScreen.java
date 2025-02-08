package cn.flowerinsnow.greatscrollabletooltips.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

@OnlyIn(Dist.CLIENT)
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
                new Button(
                        this.width / 2 - 100, this.height / 2 - 48,
                        200, 20,
                        Component.translatable(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            config.enable = !config.enable;
                            button.setMessage(Component.translatable(config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false"));
                            config.save();
                        }
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 100, this.height / 2 - 23,
                        200, 20,
                        Component.translatable(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"),
                        button -> {
                            GreatScrollableTooltipsConfig config = ConfigScreen.this.config;
                            config.autoReset = !config.autoReset;
                            button.setMessage(Component.translatable(config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false"));
                            config.save();
                        }
                )
        );

        this.addRenderableWidget(
                new AbstractSliderButton(
                        this.width / 2 - 100, this.height / 2 + 2,
                        200, 20,
                        Component.translatable("great-scrollable-tooltips.ui.config.sensitivity", this.config.sensitivity),
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
                        ConfigScreen.this.config.sensitivity = BigDecimal.valueOf(this.value)
                                .multiply(new BigDecimal(99))
                                .add(BigDecimal.ONE)
                                .setScale(0, RoundingMode.DOWN)
                                .intValue();
                    }
                }
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 100, this.height / 2 + 27,
                        200, 20,
                        Component.translatable("great-scrollable-tooltips.ui.config.done"),
                        button -> {
                            ConfigScreen instance = ConfigScreen.this;
                            Minecraft.getInstance().setScreen(instance.parent);
                        }
                )
        );
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        MutableComponent title = Component.translatable("great-scrollable-tooltips.ui.title");
        this.font.drawShadow(
                poseStack, title,
                (float) (this.width - this.font.width(title)) / 2.0F, 20.0F,
                0xFFFFFF
        );
    }
}
