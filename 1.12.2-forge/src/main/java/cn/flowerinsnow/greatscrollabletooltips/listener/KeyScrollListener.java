package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class KeyScrollListener {
    private final GreatScrollableTooltips main;

    public KeyScrollListener(GreatScrollableTooltips main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void preScreenKeyPressed(PreScreenKeyPressedEvent event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        int keyCode = event.getKeyCode();

        if (session.isRendering()) {
            if (KeyBindingManager.KEY_BINDING_SCROLL_UP.get().getKeyCode() == keyCode) {
                session.addVertical(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get().getKeyCode() == keyCode) {
                session.addHorizontal(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get().getKeyCode() == keyCode) {
                session.addVertical(-1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get().getKeyCode() == keyCode) {
                session.addHorizontal(-1);
            }
        }
    }
}
