package com.ownwn;

import com.ownwn.config.Config;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickDisplay implements ClientModInitializer {
	public static final String nickMessage = "You are now nicked. Hover over this message for details.";
	public static final String unNickMessage = "You are no longer nicked.";

	public static final Pattern namePattern = Pattern.compile("You are now nicked as: ([a-zA-Z0-9_]{3,16})");
	public static String currentNick = "Unknown!";
	public static Config config;


	@Override
	public void onInitializeClient() {
		Config.HANDLER.load();
		config = Config.HANDLER.instance();

		Keybinds.register();

		ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {

			String string = message.getString().trim();
			if (string.endsWith(unNickMessage)) {
				currentNick = MinecraftClient.getInstance().player.getName().getString();
				return true;
			}

			if (!string.endsWith(nickMessage)) return true;

			HoverEvent hover = message.getStyle().getHoverEvent();
			if (hover == null) return true;

			Text tooltip = hover.getValue(HoverEvent.Action.SHOW_TEXT);
			if (tooltip == null) return true;

			Matcher matcher = namePattern.matcher(tooltip.getString());
			if (!matcher.find()) return true;

			String name = matcher.group(1);
			if (name == null) return true;

			currentNick = name;
			Keybinds.canSendCommand = true;

			return true;
		});

		HudRenderCallback.EVENT.register((context, delta) -> {
			if (!config.enabled) return;

			int x = config.textX;
			int y = config.textY;

			context.getMatrices().push();
			context.getMatrices().translate(x, y, 0);
			context.getMatrices().scale(config.scale, config.scale, 1f);
			context.drawText(MinecraftClient.getInstance().textRenderer, currentNick, 0, 0, config.color.getRGB(), true);
			context.getMatrices().pop();
		});
	}
}

