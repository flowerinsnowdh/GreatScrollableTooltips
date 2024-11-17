package cn.flowerinsnow.greatscrollabletooltips.screen;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
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

    public ConfigScreen(GuiScreen parent) {
        this(parent, GreatScrollableTooltips.getInstance().getConfig());
    }

    public ConfigScreen(GuiScreen parent, GreatScrollableTooltipsConfig config) {
        this.parent = parent;
        this.config = config;
    }

    @Override
    public void initGui() {
        this.buttonList.add(
                new GuiButton(
                        400053423,
                        this.width / 2 - 100, this.height / 2 - 70,
                        200, 20,
                        I18n.format(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false")
                )
        );

        this.buttonList.add(
                new GuiButton(
                        400053424,
                        this.width / 2 - 100, this.height / 2 - 45,
                        200, 20,
                        I18n.format(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false")
                )
        );

        this.buttonList.add(
                this.sensitivitySlider = new GuiSlider(
                        400053425,
                        this.width / 2 - 100, this.height / 2 - 20,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.sensitivity"), "",
                        1, 100, config.sensitivity, false, true
                )
        );

        this.buttonList.add(
                new GuiButton(
                        400053426,
                        this.width / 2 - 100, this.height / 2 + 5,
                        200, 20,
                        I18n.format("great-scrollable-tooltips.ui.config.reload")
                )
        );
        this.buttonList.add(
                new GuiButton(
                        400053427,
                        this.width / 2 - 100, this.height / 2 + 30,
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
                button.displayString = I18n.format(this.config.enable ? "great-scrollable-tooltips.ui.config.enable.true" : "great-scrollable-tooltips.ui.config.enable.false");
                break;
            case 400053424:
                this.config.autoReset = !this.config.autoReset;
                button.displayString = I18n.format(this.config.autoReset ? "great-scrollable-tooltips.ui.config.auto-reset.true" : "great-scrollable-tooltips.ui.config.auto-reset.false");
                break;
            case 400053426:
                this.config.load();
                Minecraft.getMinecraft().displayGuiScreen(new ConfigScreen(this.parent, this.config));
                break;
            case 400053427:
                this.config.sensitivity = sensitivitySlider.getValueInt();
                this.config.save();
                Minecraft.getMinecraft().currentScreen = parent;
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("great-scrollable-tooltips.ui.title"), this.width / 2, 20, 0xFFFFFF);
    }
}
