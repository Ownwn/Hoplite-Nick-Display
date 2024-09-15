package com.ownwn.mixin;

import com.ownwn.config.Config;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
	@Inject(at = @At("HEAD"), method = "mouseClicked", cancellable = true)
	private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
		Config config = Config.HANDLER.instance();
		if (!config.moveWithClick) return;

		config.textX = (int) mouseX;
		config.textY = (int) mouseY;
		cir.setReturnValue(false);
	}

	@Inject(at = @At("HEAD"), method = "mouseScrolled", cancellable = true)
	private void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
		Config config = Config.HANDLER.instance();
		if (!config.moveWithClick) return;

		double sign = Math.signum(verticalAmount);
		// prevent shrinking too small
		if (config.scale <= 0.2f && sign > 0) return;

		config.scale+= (float) (-0.1*Math.signum(verticalAmount));
		cir.setReturnValue(false);
	}
}