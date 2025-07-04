package cn.flowerinsnow.greatscrollabletooltips.mixinplugin;

import net.minecraftforge.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

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
        // LegendaryTooltips
        if ("cn.flowerinsnow.greatscrollabletooltips.mixin.legendarytooltips.MixinLegendaryTooltips".equals(mixinClassName)) {
            return FMLLoader.getLoadingModList().getModFileById("legendarytooltips") != null;
        }
        // Obscure Tooltips
        if ("cn.flowerinsnow.greatscrollabletooltips.mixin.obscuretooltips.MixinTooltipRenderer".equals(mixinClassName)) {
            return FMLLoader.getLoadingModList().getModFileById("obscure_tooltips") != null;
        }
        // Tooltips Reforged
        if ("cn.flowerinsnow.greatscrollabletooltips.mixin.tooltipsreforged.MixinTooltipsRenderHelper".equals(mixinClassName)) {
            return FMLLoader.getLoadingModList().getModFileById("tooltips_reforged") != null;
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