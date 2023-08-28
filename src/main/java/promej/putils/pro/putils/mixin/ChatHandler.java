package promej.putils.pro.putils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import promej.putils.pro.putils.sounds.ModSounds;

@Mixin(value={ChatHud.class}, priority=1)
public abstract class ChatHandler {
    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="HEAD")})
    private void injected(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        String nickName = message.getContent().toString();

        String string = message.getString();

            String content;
            if (string.contains("➥") && (string.split("➥")[1]).contains(nickName)) {
                this.playSound(ModSounds.NEW_MESSAGE_PM);
            }
            if (string.contains("› Вы получили")) {
                this.playSound(ModSounds.NEW_MONEY_PM);
            }
            if (string.contains("DragonCoin »")) {
                this.playSound(ModSounds.NEW_MONEY_PM);
            }
            if (string.contains("Ⓖ") && (string.split("››")[1]).contains(nickName)) {
                this.playSound(ModSounds.NEW_GLOBAL_PM);
            }
            if (string.contains("_PrometheuZ_ зашел на сервер")) {
                this.playSound(ModSounds.PJ_WELCOME);
            }

    }

    private void playSound(SoundEvent soundEvent) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.ambient(soundEvent, 1.0f, 6.0f));
    }
}
