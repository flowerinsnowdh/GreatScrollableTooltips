package cn.flowerinsnow.greatscrollabletooltips.config;

import cn.flowerinsnow.flowerinsnowlib.jackson.databind.java11.prettyprinter.CustomIndentDefaultPrettyPrinterImpl;
import cn.flowerinsnow.flowerinsnowlib.jackson.databind.json5.Json5Mapper;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class GreatScrollableTooltipsConfig {
    private ObjectNode root;

    private static final JsonMapper JSON_MAPPER = Json5Mapper.shared();

    private static final String FIELD_VERSION = "version";
    private static final int VERSION = 1;
    private static final String FIELD_NAME_ENABLE = "enable";
    private static final String FIELD_NAME_SENSITIVITY = "sensitivity";
    private static final String FIELD_NAME_AUTO_RESET = "auto_reset";
    private static final String FIELD_INVERT_SCROLLING = "invert_scrolling";
    private static final String FIELD_INVERT_SCROLLING_MOUSE = "mouse";
    private static final String FIELD_INVERT_SCROLLING_MOUSE_X = "x";
    private static final String FIELD_INVERT_SCROLLING_MOUSE_Y = "y";

    @Contract(value = "-> new", pure = true)
    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(GreatScrollableTooltips.MODID + ".json5");
    }

    @Contract(mutates = "io,this")
    public void init() {
        final Path configPath = getConfigPath();
        if (!Files.exists(configPath)) {
            this.root = JSON_MAPPER.createObjectNode();
            this.root.put(FIELD_VERSION, VERSION);
            this.root.put(FIELD_NAME_ENABLE, true);
            this.root.put(FIELD_NAME_SENSITIVITY, 10);
            this.root.put(FIELD_NAME_AUTO_RESET, true);

            ObjectNode invertScrollingNode = JSON_MAPPER.createObjectNode();
            ObjectNode mouseNode = JSON_MAPPER.createObjectNode();
            mouseNode.put(FIELD_INVERT_SCROLLING_MOUSE_X, false);
            mouseNode.put(FIELD_INVERT_SCROLLING_MOUSE_Y, false);
            invertScrollingNode.set(FIELD_INVERT_SCROLLING_MOUSE, mouseNode);
            this.root.set(FIELD_INVERT_SCROLLING, invertScrollingNode);
            this.save();
        } else if (!Files.isRegularFile(configPath)) {
            IOException ex = new IOException("config file " + getConfigPath() + " is not a regular file.");
            Minecraft.getInstance().emergencySaveAndCrash(CrashReport.forThrowable(ex, ex.getMessage()));
        } else {
            this.root = ((ObjectNode) JSON_MAPPER.readTree(configPath));
            if (!this.root.has(FIELD_VERSION) || this.root.get(FIELD_VERSION).asInt() != VERSION) {
                try {
                    Files.delete(configPath);
                } catch (IOException e) {
                    Minecraft.getInstance().emergencySaveAndCrash(CrashReport.forThrowable(e, "unable to delete old config file."));
                    return;
                }
                this.init();
            }
        }
    }

    @Contract(mutates = "io")
    public void save() {
        JSON_MAPPER.writer().with(CustomIndentDefaultPrettyPrinterImpl.instance().createSpacesLF(4)).writeValue(getConfigPath(), this.root);
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

    @Contract(pure = true)
    public boolean invertScrollingMouseX() {
        return this.root.get(FIELD_INVERT_SCROLLING).get(FIELD_INVERT_SCROLLING_MOUSE).get(FIELD_INVERT_SCROLLING_MOUSE_X).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public GreatScrollableTooltipsConfig invertScrollingMouseX(boolean invertScrollingMouseX) {
        ((ObjectNode) this.root.get(FIELD_INVERT_SCROLLING).get(FIELD_INVERT_SCROLLING_MOUSE)).put(FIELD_INVERT_SCROLLING_MOUSE_X, invertScrollingMouseX);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertInvertScrollingMouseX() {
        boolean invert = !this.invertScrollingMouseX();
        this.invertScrollingMouseX(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean invertScrollingMouseY() {
        return this.root.get(FIELD_INVERT_SCROLLING).get(FIELD_INVERT_SCROLLING_MOUSE).get(FIELD_INVERT_SCROLLING_MOUSE_Y).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public GreatScrollableTooltipsConfig invertScrollingMouseY(boolean invertScrollingMouseY) {
        ((ObjectNode) this.root.get(FIELD_INVERT_SCROLLING).get(FIELD_INVERT_SCROLLING_MOUSE)).put(FIELD_INVERT_SCROLLING_MOUSE_Y, invertScrollingMouseY);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertInvertScrollingMouseY() {
        boolean invert = !this.invertScrollingMouseY();
        this.invertScrollingMouseY(invert);
        return invert;
    }
}
