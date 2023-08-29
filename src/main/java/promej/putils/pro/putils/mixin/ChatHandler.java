package promej.putils.pro.putils.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.datafixer.fix.EntityShulkerColorFix;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import promej.putils.pro.putils.sounds.ModSounds;

import javax.swing.text.html.parser.Entity;

@Mixin(value={ChatHud.class})
public class ChatHandler {
    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V"}, at={@At(value="HEAD")})
    private void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci){
        String nickName = MinecraftClient.getInstance().player.getName().getString();

        String string = message.toString();

        try {

        if (string.contains("➥") && (string.split("➥")[1]).contains(nickName)) {
            this.playSound(ModSounds.NEW_MESSAGE_PM);
        }
        if (string.contains("› Вам зачислено")) {
            this.playSound(ModSounds.NEW_MONEY_PM);
        }

        if (string.contains("Ⓖ") && (string.split("››")[1]).contains(nickName)) {
            this.playSound(ModSounds.NEW_GLOBAL_PM);
        }
        if (string.contains("Ⓛ") && (string.split("››")[1]).contains(nickName)) {
            this.playSound(ModSounds.NEW_GLOBAL_PM);
        }
        if (string.contains("Ⓓ") && (string.split("››")[1]).contains(nickName)) {
            this.playSound(ModSounds.NEW_GLOBAL_PM);
        }
        if (string.contains("_PrometheuZ_ зашел на сервер")) {
            this.playSound(ModSounds.PJ_WELCOME);
        }

        } catch (Exception e){
            e.printStackTrace();
        }

        if (string.contains("devmes")) {
            World world = MinecraftClient.getInstance().world;
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            ShulkerEntity shulkerEntity = new ShulkerEntity(EntityType.SHULKER, world);
            shulkerEntity.setPosition(player.getX(), player.getY(), player.getZ());
            //shulkerEntity.addStatusEffect()
            world.spawnEntity(shulkerEntity);
        }

    }

    private void playSound(SoundEvent soundEvent) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.ambient(soundEvent, 1.0f, 6.0f));
    }
}
