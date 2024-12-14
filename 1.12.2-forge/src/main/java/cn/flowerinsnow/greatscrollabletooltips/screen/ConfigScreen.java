package cn.flowerinsnow.greatscrollabletooltips.screen;

import cn.flowerinsnow.greatscrollabletooltips.common.config.GreatScrollableTooltipsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigScreen extends GuiScreen {
    private final GuiScreen parent;
    private final GreatScrollableTooltipsConfig config;
    private GuiSlider sensitivitySlider;

    public ConfigScreen(GuiScreen parent, GreatScrollableTooltipsConfig config) {
        this.parent = parent;
        this.config = config;
    }

    @Override
    public void initGui() {
        this.buttonList.add(
                new GuiButton(
                        400053423,
                        this.width / 2 - 100, this.height / 2 - 48,
                        200, 20,
                        I18n.format(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false")
                )
        );

        this.buttonList.add(
                new GuiButton(
                        400053424,
                        this.width / 2 - 100, this.height / 2 - 23,
                        200, 20,
                        I18n.format(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false")
                )
        );

        this.buttonList.add(
                this.sensitivitySlider = new GuiSlider(
                        400053425,
                        this.width / 2 - 100, this.height / 2 + 2,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.sensitivity"), "",
                        1, 100, this.config.sensitivity, false, true
                ) {
                    @Override
                    public void updateSlider() {
                        super.updateSlider();
                        ConfigScreen.this.config.sensitivity = ConfigScreen.this.sensitivitySlider.getValueInt();
                        ConfigScreen.this.config.save();
                    }
                }
        );
        this.buttonList.add(
                new GuiButton(
                        400053426,
                        this.width / 2 - 100, this.height / 2 + 27,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.done")
                )
        );
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 400053423:
                this.config.enable = !this.config.enable;
                this.config.save();
                button.displayString = I18n.format(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false");
                break;
            case 400053424:
                this.config.autoReset = !this.config.autoReset;
                this.config.save();
                button.displayString = I18n.format(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false");
                break;
            case 400053426:
                Minecraft.getMinecraft().currentScreen = parent;
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRenderer, I18n.format("great-scrollable-tooltips.ui.title"), this.width / 2, 20, 0xFFFFFF);
    }
}
