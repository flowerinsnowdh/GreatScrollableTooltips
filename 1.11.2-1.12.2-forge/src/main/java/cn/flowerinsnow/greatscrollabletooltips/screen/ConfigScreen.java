package cn.flowerinsnow.greatscrollabletooltips.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.greatscrollabletooltips.config.Config;

@SideOnly(Side.CLIENT)
public class ConfigScreen extends GuiScreen {
    private final GuiScreen parent;
    private final Config config;
    private GuiSlider sensitivitySlider;

    public ConfigScreen(GuiScreen parent, Config config) {
        this.parent = parent;
        this.config = config;
    }

    @Override
    public void initGui() {
        this.buttonList.add(
                new GuiButton(
                        400053423,
                        this.width / 2 - 100, this.height / 2 - 60,
                        200, 20,
                        config.enable ? I18n.format("great-scrollable-tooltips.ui.config.enable.true") : I18n.format("great-scrollable-tooltips.ui.config.enable.false")
                )
        );
        this.buttonList.add(
                sensitivitySlider = new GuiSlider(
                        400053424,
                        this.width / 2 - 100, this.height / 2 - 35,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.sensitivity"), "",
                        1, 100, config.sensitivity, false, true
                )
        );
        this.buttonList.add(
                new GuiButton(
                        400053425,
                        this.width / 2 - 100, this.height / 2 - 10,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.reload")
                )
        );
        this.buttonList.add(
                new GuiButton(
                        400053426,
                        this.width / 2 - 100, this.height / 2 + 15,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.save-and-exit")
                )
        );
        this.buttonList.add(
                new GuiButton(
                        400053427,
                        this.width / 2 - 100, this.height / 2 + 40,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.discard-and-exit")
                )
        );
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 400053423:
                if (config.enable) {
                    button.displayString = I18n.format("great-scrollable-tooltips.ui.config.enable.false");
                    config.enable = false;
                } else {
                    button.displayString = I18n.format("great-scrollable-tooltips.ui.config.enable.true");
                    config.enable = true;
                }
                break;
            case 400053425:
                config.load();
                sensitivitySlider.setValue(config.sensitivity);
                break;
            case 400053426:
                config.sensitivity = sensitivitySlider.getValueInt();
                config.save();
                Minecraft.getMinecraft().currentScreen = parent;
                break;
            case 400053427:
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
