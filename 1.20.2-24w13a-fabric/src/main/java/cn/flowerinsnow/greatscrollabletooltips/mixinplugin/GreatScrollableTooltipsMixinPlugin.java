package cn.flowerinsnow.greatscrollabletooltips.mixinplugin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class GreatScrollableTooltipsMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (
                "cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips.MixinLegendaryTooltips".equals(mixinClassName) ||
                        "cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips.MixinItemModelComponent".equals(mixinClassName)
        ) {
            return FabricLoader.getInstance().isModLoaded("legendarytooltips");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
