package promej.putils.pro.putils.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import promej.putils.pro.putils.Putils;
import promej.putils.pro.putils.entity.FakeAllayEntity;
import promej.putils.pro.putils.entity.FakeAllayManager;
import promej.putils.pro.putils.sounds.ModSounds;

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


        if(string.contains("Ближайший магазин, соответствующий")){
          FakeAllayManager.clear();
          String[] list = message.getString().split("\n");
          for(String item : list){
              if(item.contains("x:")){



                  String x = item.split("x:")[1].split(" ")[0];
                  String y = item.split("y:")[1].split(" ")[0];
                  String z = item.split("z:")[1].split(" ")[0];

                  String name = "Фиксик";
                  Item itemIco = Item.byRawId(Putils.rawId);

                  ItemStack itemStack = new ItemStack(itemIco);

                  FakeAllayManager.add(name, itemStack,  Double.parseDouble(x)+0.5, Double.parseDouble(y)+1, Double.parseDouble(z)+0.5);


              }
          }

        }

        if (string.contains("devmes")) {



        }

    }

   private void spawnFakeAllay(String name, ItemStack item, double x, double y, double z){
       FakeAllayEntity fakeAllayEntity = new FakeAllayEntity(name, item, x, y, z);
       fakeAllayEntity.spawn();
    }

    private void playSound(SoundEvent soundEvent) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.ambient(soundEvent, 1.0f, 6.0f));
    }
}
