package promej.putils.pro.putils.mixin;

import net.minecraft.client.gui.hud.MessageIndicator;

import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import promej.putils.pro.putils.handlers.ChatHandler;


@Mixin(value={net.minecraft.client.gui.hud.ChatHud.class}, priority=0)
public class ChatHook {


    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="HEAD")})
    private void addMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci){
        ChatHandler.newMessage(message);
    }


}