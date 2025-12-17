package cn.flowerinsnow.greatscrollabletooltips.config;

import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.dataformat.toml.TomlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class GreatScrollableTooltipsConfig {
    private ObjectNode root;

    private static final String FIELD_NAME_ENABLE = "enable";
    private static final String FIELD_NAME_SENSITIVITY = "sensitivity";
    private static final String FIELD_NAME_AUTO_RESET = "auto_reset";

    @Contract(value = "-> new", pure = true)
    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(GreatScrollableTooltips.MODID + ".toml");
    }

    @Contract(mutates = "io,this")
    public void init() {
        final Path configPath = getConfigPath();
        if (!Files.exists(configPath)) {
            this.root = TomlMapper.shared().createObjectNode();
            this.root.put(FIELD_NAME_ENABLE, true);
            this.root.put(FIELD_NAME_SENSITIVITY, 10);
            this.root.put(FIELD_NAME_AUTO_RESET, true);
            this.save();
        } else if (!Files.isRegularFile(configPath)) {
            IOException ex = new IOException("config file " + getConfigPath() + " is not a regular file.");
            Minecraft.getInstance().emergencySaveAndCrash(CrashReport.forThrowable(ex, ex.getMessage()));
        } else {
            this.root = ((ObjectNode) TomlMapper.shared().readTree(configPath));
        }
    }

    @Contract(mutates = "io")
    public void save() {
        TomlMapper.shared().writeValue(getConfigPath(), this.root);
    }

    @Contract(pure = true)
    public boolean enable() {
        return this.root.get(FIELD_NAME_ENABLE).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public GreatScrollableTooltipsConfig enable(boolean enable) {
        this.root.put(FIELD_NAME_ENABLE, enable);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertEnable() {
        boolean invert = !this.enable();
        this.enable(invert);
        return invert;
    }

    @Contract(pure = true)
    public int sensitivity() {
        return this.root.get(FIELD_NAME_SENSITIVITY).asInt();
    }

    @Contract("_ -> this")
    public GreatScrollableTooltipsConfig sensitivity(int sensitivity) {
        this.root.put(FIELD_NAME_SENSITIVITY, sensitivity);
        return this;
    }

    @Contract(pure = true)
    public boolean autoReset() {
        return this.root.get(FIELD_NAME_AUTO_RESET).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract("_ -> this")
    public GreatScrollableTooltipsConfig autoReset(boolean autoReset) {
        this.root.put(FIELD_NAME_AUTO_RESET, autoReset);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertAutoReset() {
        boolean invert = !this.autoReset();
        this.autoReset(invert);
        return invert;
    }
}
