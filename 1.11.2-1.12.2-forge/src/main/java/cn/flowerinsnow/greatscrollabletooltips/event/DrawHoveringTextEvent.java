package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class DrawHoveringTextEvent extends Event {
    public final ItemStack stack;
    public final List<String> textLines;
    public final int mouseX;
    public final int mouseY;
    public final int screenWidth;
    public final int screenHeight;
    public final int maxTextWidth;
    public final FontRenderer font;

    protected DrawHoveringTextEvent(ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font) {
        this.stack = stack;
        this.textLines = textLines;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.maxTextWidth = maxTextWidth;
        this.font = font;
    }

    @Cancelable
    public static class Pre extends DrawHoveringTextEvent {
        public Pre(ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font) {
            super(stack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends DrawHoveringTextEvent {
        public Post(ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font) {
            super(stack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
        }
    }
}
