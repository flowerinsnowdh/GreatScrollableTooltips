package cn.flowerinsnow.greatscrollabletooltips.config;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cn.flowerinsnow.fnml4j.api.node.ObjectNode;
import cn.flowerinsnow.fnml4j.api.node.StringNode;
import cn.flowerinsnow.fnml4j.api.parser.present.FNML4JPresentParser;
import cn.flowerinsnow.fnml4j.core.FNML4J;
import cn.flowerinsnow.greatscrollabletooltips.GreatScrollableTooltips;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

@SideOnly(Side.CLIENT)
public class Config {
    private ObjectNode rootNode;

    public boolean enable;
    public int sensitivity;

    private File configFile;

    public Config(File configFile) {
        this.configFile = configFile;
    }

    public void saveDefaultConfig() {
        BiConsumer<Throwable, String> crashFunction = crashFunction();
        if (!configFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                configFile.createNewFile();
                int len;
                byte[] bytes = new byte[1024];
                try (InputStream in = GreatScrollableTooltips.class.getResourceAsStream("/config.conf")) {
                    try (FileOutputStream fos = new FileOutputStream(configFile)) {
                        //noinspection DataFlowIssue
                        while ((len = in.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                        }
                    }
                }
            } catch (IOException e) {
                crashFunction.accept(e, "Failed to save default config.");
            }
        }
    }

    public void load() {
        BiConsumer<Throwable, String> crashFunction = crashFunction();
        try {
            rootNode = FNML4J.parse(getConfigFile(), StandardCharsets.UTF_8);
            enable = rootNode.getChildNodeNotNull("enable", FNML4JPresentParser.BOOLEAN, StringNode.class);
            sensitivity = rootNode.getChildNodeNotNull("sensitivity", FNML4JPresentParser.INT, StringNode.class);
        } catch (IOException e) {
            crashFunction.accept(e, "Failed to load config.");
        }
    }

    public void save() {
        BiConsumer<Throwable, String> crashFunction = crashFunction();
        try (FileOutputStream fos = new FileOutputStream(getConfigFile())) {
            try (OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                rootNode.writeRoot(0, osw);
            }
        } catch (IOException e) {
            crashFunction.accept(e, "Failed to save config.");
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public Config setConfigFile(File configFile) {
        this.configFile = configFile;
        return this;
    }

    private BiConsumer<Throwable, String> crashFunction() {
        return (e, msg) -> Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(e, msg));
    }
}
