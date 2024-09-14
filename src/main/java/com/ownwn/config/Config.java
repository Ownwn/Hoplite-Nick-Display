package com.ownwn.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.ColorField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import java.awt.*;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("nick-display", "nick-display"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve("nick-display.json5"))
                            .setJson5(true)
                            .build())
                    .build();

    public static Screen screen(Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

    @SerialEntry
    @AutoGen(category = "Nick")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean enabled = true;

    @SerialEntry
    @AutoGen(category = "Nick")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean moveWithClick = true;

    @SerialEntry
    @AutoGen(category = "Nick")
    @ColorField
    public Color color = Color.RED;

    @SerialEntry
    public float scale = 1f;

    @SerialEntry
    public int textY = 100;

    @SerialEntry
    public int textX = 100;




}
