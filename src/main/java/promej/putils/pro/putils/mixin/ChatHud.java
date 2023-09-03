package promej.putils.pro.putils.mixin;


import net.minecraft.client.gui.hud.MessageIndicator;

import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import promej.putils.pro.putils.handlers.ChatHandler;


@Mixin(value={net.minecraft.client.gui.hud.ChatHud.class})
public class ChatHud {


    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V"}, at={@At(value="HEAD")})
    private void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci){
        ChatHandler.newMessage(message, signature, ticks, indicator, refresh, ci);
    }


}
