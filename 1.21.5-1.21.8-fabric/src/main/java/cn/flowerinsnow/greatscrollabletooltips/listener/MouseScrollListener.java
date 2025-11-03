package cn.flowerinsnow.greatscrollabletooltips.listener;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import cn.flowerinsnow.greatscrollabletooltips.common.object.ScrollSession;
import cn.flowerinsnow.greatscrollabletooltips.event.PreScreenMouseScrollEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public record MouseScrollListener(GreatScrollableTooltips main) implements PreScreenMouseScrollEvent {
    @Override
    public ActionResult preScreenMouseScrolled(HandledScreen<?> screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!this.main.getConfig().enable) {
            return ActionResult.PASS;
        }

        ScrollSession<ItemStack> session = this.main.getScrollSession();
        if (!session.isRendering()) {
            return ActionResult.PASS;
        }

        if (session.getLastItemStackRendered().isIn(ItemTags.BUNDLES)) {
            return ActionResult.PASS;
        }

        int i = Double.compare(horizontalAmount, 0.0);
        if (!Screen.hasShiftDown()) {
            session.addHorizontal(i);
        } else {
            session.addVertical(i);
        }

        i = Double.compare(verticalAmount, 0.0);
        if (!Screen.hasShiftDown()) {
            session.addVertical(i);
        } else {
            session.addHorizontal(i);
        }
        return ActionResult.PASS;
    }
}
