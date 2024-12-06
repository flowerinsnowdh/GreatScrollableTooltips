package cn.flowerinsnow.greatscrollabletooltips.listener;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.manager.KeyBindingManager;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenKeyPressedEvent;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public record KeyScrollListener(GreatScrollableTooltips main) {
    public KeyScrollListener(GreatScrollableTooltips main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void onScreenKeyPressed(PreScreenKeyPressedEvent event) {
        ScrollSession<ItemStack> session = this.main.getScrollSession();
        int keyCode = event.getKeyCode();
        int scanCode = event.getScanCode();

        if (session.isRendering()) {
            if (KeyBindingManager.KEY_BINDING_SCROLL_UP.get().matches(keyCode, scanCode)) {
                session.addVertical(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_LEFT.get().matches(keyCode, scanCode)) {
                session.addHorizontal(1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_DOWN.get().matches(keyCode, scanCode)) {
                session.addVertical(-1);
            }
            if (KeyBindingManager.KEY_BINDING_SCROLL_RIGHT.get().matches(keyCode, scanCode)) {
                session.addHorizontal(-1);
            }
        }
    }
}
