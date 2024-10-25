package online.flowerinsnow.greatscrollabletooltips.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface RenderTooltipEvent {
    interface Post {
        Event<Post> EVENT = EventFactory.createArrayBacked(Post.class, listeners -> (screen, matrices, stack, x, y) -> {
            for (Post listener : listeners) {
                ActionResult actionResult = listener.startDrawMouseoverTooltip(screen, matrices, stack, x, y);
                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult startDrawMouseoverTooltip(HandledScreen<?> screen, MatrixStack matrices, ItemStack stack, int x, int y);
    }

    interface Miss {
        Event<Miss> EVENT = EventFactory.createArrayBacked(Miss.class, listeners -> screen -> {
            for (Miss listener : listeners) {
                ActionResult actionResult = listener.onMiss(screen);
                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult onMiss(HandledScreen<?> screen);
    }
}
