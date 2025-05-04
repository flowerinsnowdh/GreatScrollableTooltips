package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;

@Environment(EnvType.CLIENT)
public record KeyScrollListener(GreatScrollableTooltips main) implements PreScreenKeyPressedEvent {
    @Override
    public ActionResult preScreenKeyPressed(HandledScreen<?> screen, int keyCode, int scanCode, int modifiers) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        if (session.isRendering()) {
            if (KeyBindingManager.KEY_BINDING_SCROLL_UP.get().matchesKey(keyCode, scanCode)) {
                session.addHorizontal(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get().matchesKey(keyCode, scanCode)) {
                session.addVertical(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get().matchesKey(keyCode, scanCode)) {
                session.addHorizontal(-1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get().matchesKey(keyCode, scanCode)) {
                session.addVertical(-1);
            }
        }
        return ActionResult.PASS;
    }
}
