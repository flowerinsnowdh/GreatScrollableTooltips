package cn.flowerinsnow.greatscrollabletooltips.util;

import net.minecraft.client.gui.tooltip.TooltipPositioner;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class AbsoluteHoveredTooltipPositioner implements TooltipPositioner {
    @Override
    public Vector2ic getPosition(int screenWidth, int screenHeight, int x, int y, int width, int height) {
        return new Vector2i(x, y).add(12, -12);
    }
}
