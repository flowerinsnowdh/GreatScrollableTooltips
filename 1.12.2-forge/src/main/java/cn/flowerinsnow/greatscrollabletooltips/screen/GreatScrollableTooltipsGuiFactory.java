package cn.flowerinsnow.greatscrollabletooltips.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@SideOnly(Side.CLIENT)
public class GreatScrollableTooltipsGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigScreen(parentScreen, GreatScrollableTooltips.getInstance().getConfig());
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
