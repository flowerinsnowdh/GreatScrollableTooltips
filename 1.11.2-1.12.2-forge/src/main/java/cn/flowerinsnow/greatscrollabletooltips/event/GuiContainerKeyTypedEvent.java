package cn.flowerinsnow.greatscrollabletooltips.event;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GuiContainerKeyTypedEvent extends Event {
    public final GuiContainer gui;
    public final char typedChar;
    public final int keyCode;

    protected GuiContainerKeyTypedEvent(GuiContainer gui, char typedChar, int keyCode) {
        this.gui = gui;
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }

    @Cancelable
    public static class Pre extends GuiContainerKeyTypedEvent {
        public Pre(GuiContainer gui, char typedChar, int keyCode) {
            super(gui, typedChar, keyCode);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends GuiContainerKeyTypedEvent {
        public Post(GuiContainer gui, char typedChar, int keyCode) {
            super(gui, typedChar, keyCode);
        }
    }
}
