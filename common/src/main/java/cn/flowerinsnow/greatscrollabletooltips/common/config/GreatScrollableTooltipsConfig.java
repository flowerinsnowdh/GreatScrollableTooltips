package cn.flowerinsnow.greatscrollabletooltips.common.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.WritingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import cn.flowerinsnow.greatscrollabletooltips.common.provider.ModEnvironmentProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class GreatScrollableTooltipsConfig {
    private final ModEnvironmentProvider environmentProvider;

    public GreatScrollableTooltipsConfig(ModEnvironmentProvider environmentProvider) {
        this.environmentProvider = environmentProvider;
    }

    public boolean enable;
    public int sensitivity;
    public boolean autoReset;

    public void saveDefaultConfig() {
        Path configFile = this.environmentProvider.getConfigFile();
        File file = configFile.toFile();
        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                int len;
                byte[] bytes = new byte[1024];
                try (InputStream in = this.environmentProvider.getDefaultConfigAsStream()) {
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        while ((len = in.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                        }
                    }
                }
            } catch (IOException e) {
                this.environmentProvider.crash(e, "failed to save default config");
            }
        }
    }

    public void load() {
        TomlFormat format = TomlFormat.instance();
        TomlParser parser = format.createParser();
        CommentedConfig config;
        try {
            config = parser.parse(this.environmentProvider.getConfigFile(), (path, ignored0) -> {
                this.environmentProvider.crash(new FileNotFoundException(path.toString()), "failed to load config");
                return false;
            }, StandardCharsets.UTF_8);
        } catch (WritingException e) {
            this.environmentProvider.crash(e, "failed to load config file");
            return;
        }
        this.enable = config.get("enable");
        this.sensitivity = config.getInt("sensitivity");
        this.autoReset = config.get("auto_reset");
    }

    public void save() {
        TomlFormat format = TomlFormat.instance();
        CommentedConfig config = format.createConfig();
        config.set("enable", this.enable);
        config.set("sensitivity", this.sensitivity);
        config.set("auto_reset", this.autoReset);
        TomlWriter writer = format.createWriter();
        try {
            writer.write(config, this.environmentProvider.getConfigFile(), WritingMode.REPLACE, StandardCharsets.UTF_8);
        } catch (WritingException e) {
            this.environmentProvider.crash(e, "failed to save config");
        }
    }
}
