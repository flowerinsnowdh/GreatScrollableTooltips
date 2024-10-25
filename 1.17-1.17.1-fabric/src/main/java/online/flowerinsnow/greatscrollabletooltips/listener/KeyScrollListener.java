package online.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import online.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import online.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import online.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import online.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;

public class KeyScrollingListener implements PreScreenKeyPressedEvent {
    @Override
    public ActionResult preScreenKeyPressed(HandledScreen<?> screen, int keyCode, int scanCode, int modifiers) {
        ScrollSession<ItemStack> session = GreatScrollableTooltips.getInstance().getScrollSession();
        if (session.isRendering()) {
            if (KeyBindingManager.KEY_BINDING_SCROLL_UP.get().matchesKey(keyCode, scanCode)) {
                session.addVertical(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get().matchesKey(keyCode, scanCode)) {
                session.addHorizontal(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get().matchesKey(keyCode, scanCode)) {
                session.addVertical(-1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get().matchesKey(keyCode, scanCode)) {
                session.addHorizontal(-1);
            }
        }
        return ActionResult.PASS;
    }
}
