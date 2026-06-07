package me.noahvdaa.nochatlag.nochatlag.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mixin(net.minecraft.client.network.message.MessageHandler.class)
public abstract class MessageHandlerMixin {

    private final ExecutorService service = Executors.newFixedThreadPool(1);

    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(
            method = "onChatMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void handleChat(SignedMessage message, GameProfile sender, MessageType.Parameters params, CallbackInfo ci) {
        service.submit(() -> {
            if (this.client.shouldBlockMessages(sender.id())) {
                return;
            }
            this.client.execute(() ->
                this.client.inGameHud.getChatHud().addMessage(
                    params.applyChatDecoration(message.getContent()),
                    message.signature(),
                    null
                )
            );
        });
        ci.cancel();
    }
}
