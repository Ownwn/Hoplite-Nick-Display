package com.ownwn;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {

    public static KeyBinding toggleKey;
    public static KeyBinding nickKey;
    public static boolean canSendCommand = true;

    public static void register() {

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nick-display.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.nick-display.nick-display"
        ));

        nickKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nick-display.renick",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.nick-display.nick-display"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                NickDisplay.config.enabled = !NickDisplay.config.enabled;
            }
            while (nickKey.wasPressed() && canSendCommand) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("renick");
                canSendCommand = false;
            }
        });
    }

}
