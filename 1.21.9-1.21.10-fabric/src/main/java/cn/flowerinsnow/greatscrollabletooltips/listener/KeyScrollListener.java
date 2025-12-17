package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.object.ScrollSession;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.InteractionResult;

@Environment(EnvType.CLIENT)
public record KeyScrollListener(GreatScrollableTooltips main) implements PreScreenKeyPressedEvent {
    @Override
    public InteractionResult preScreenKeyPressed(AbstractContainerScreen<?> screen, KeyEvent keyEvent) {
        ScrollSession session = this.main.scrollSession();
        if (session.lastSlotRendered() != null && session.lastSlotRendered().hasItem()) {
            if (KeyBindingManager.KEY_BINDING_SCROLL_UP.get().matches(keyEvent)) {
                session.increaseVerticalAmount();
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get().matches(keyEvent)) {
                session.increaseHorizontalAmount();
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get().matches(keyEvent)) {
                session.decreaseVerticalAmount();
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get().matches(keyEvent)) {
                session.decreaseHorizontalAmount();
            }
        }
        return InteractionResult.PASS;
    }
}
