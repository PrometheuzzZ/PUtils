package promej.putils.pro.putils.handlers;

        import com.google.common.collect.ComparisonChain;
        import com.google.common.collect.Ordering;
        import net.fabricmc.api.EnvType;
        import net.fabricmc.api.Environment;
        import net.fabricmc.loader.api.FabricLoader;
        import net.minecraft.client.MinecraftClient;
        import net.minecraft.client.gui.hud.MessageIndicator;
        import net.minecraft.client.network.ClientPlayNetworkHandler;
        import net.minecraft.client.network.PlayerListEntry;
        import net.minecraft.client.sound.PositionedSoundInstance;
        import net.minecraft.item.Item;
        import net.minecraft.item.ItemStack;
        import net.minecraft.network.message.MessageSignatureData;
        import net.minecraft.scoreboard.Team;
        import net.minecraft.sound.SoundEvent;
        import net.minecraft.sound.SoundEvents;
        import net.minecraft.text.Text;
        import net.minecraft.world.GameMode;
        import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
        import promej.putils.pro.putils.Putils;
        import promej.putils.pro.putils.entity.FakeAllayManager;
        import promej.putils.pro.putils.sounds.ModSounds;

        import java.io.*;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.util.Comparator;
        import java.util.List;
        import java.util.logging.Logger;

public class ChatHandler {



    public static void newMessage(Text message){
        String nickName = MinecraftClient.getInstance().player.getName().getString();

        String string = message.getString();

        try {

            if (string.contains("➥") && (string.split("➥")[1]).contains(nickName)) {
                playSound(ModSounds.NEW_MESSAGE_PM);
            }
            if (string.contains("› Вам зачислено")) {
                playSound(ModSounds.NEW_MONEY_PM);
            }

            if (string.contains("Ⓖ") && (string.split("››")[1]).contains(nickName)) {
                playSound(ModSounds.NEW_GLOBAL_PM);
            }
            if (string.contains("Ⓛ") && (string.split("››")[1]).contains(nickName)) {
                playSound(ModSounds.NEW_GLOBAL_PM);
            }
            if (string.contains("Ⓓ") && (string.split("››")[1]).contains(nickName)) {
                playSound(ModSounds.NEW_GLOBAL_PM);
            }
            if (string.contains("+ [LEGEND] _PrometheuZ_")) {
                playSound(ModSounds.PJ_WELCOME);
            }
            if (string.contains("послал запрос на телепортацию, чтобы переместиться к вам.")) {
                playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT);
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

        if (string.contains("saveskin")) {



            try {
                String nick = message.getString().split("saveskin ")[1];
                saveSkin(nick);
            } catch (Exception e){
                MinecraftClient.getInstance().player.sendMessage(Text.literal("[PUtils] Введите ник."));
            }



        }
    }
    private static void playSound(SoundEvent soundEvent) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.ambient(soundEvent, 1.0f, 6.0f));
    }
    @Environment(EnvType.CLIENT)
    static class EntryOrderComparator implements Comparator<PlayerListEntry> {
        EntryOrderComparator() {
        }

        public int compare(PlayerListEntry playerListEntry, PlayerListEntry playerListEntry2) {
            Team team = playerListEntry.getScoreboardTeam();
            Team team2 = playerListEntry2.getScoreboardTeam();
            return ComparisonChain.start().compareTrueFirst(playerListEntry.getGameMode() != GameMode.SPECTATOR, playerListEntry2.getGameMode() != GameMode.SPECTATOR).compare(team != null ? team.getName() : "", team2 != null ? team2.getName() : "").compare(playerListEntry.getProfile().getName(), playerListEntry2.getProfile().getName(), String::compareToIgnoreCase).result();
        }
    }
    private static final Ordering<PlayerListEntry> ENTRY_ORDERING = Ordering.from(new EntryOrderComparator());

    private static List<PlayerListEntry> getPlayerList(){
        ClientPlayNetworkHandler clientPlayNetworkHandler = MinecraftClient.getInstance().player.networkHandler;
        List<PlayerListEntry> list = ENTRY_ORDERING.sortedCopy(clientPlayNetworkHandler.getPlayerList());
        //   MinecraftClient.getInstance().player.sendMessage(Text.of("listSize"+list.size()));

        return list;
    }
    private static void saveSkin(String nicksave) {
        List<PlayerListEntry> list = getPlayerList();

        for (final PlayerListEntry listEntry : list) {

            String nick = listEntry.getProfile().getName();

            if(nick.contains(nicksave)){
                try {

                    String skinPath = FabricLoader.getInstance().getGameDir()+"\\assets\\skins\\";
                    String skinId = listEntry.getSkinTexture().toString().split("/")[1];
                    String skinDir = firstNChars(skinId, 2);
                    String fullPath = skinPath + skinDir + "\\" + skinId;
                    String skinSavedFullPath = FabricLoader.getInstance().getGameDir()+"\\skins_saved\\"+nicksave+".png";

                    MinecraftClient.getInstance().player.sendMessage(Text.literal("[PUtils] NICK: "+nicksave));
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("[PUtils] SOURCE: "+fullPath));
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("[PUtils] SAVED: "+skinSavedFullPath));

                    File source = new File(fullPath);
                    File dest = new File(skinSavedFullPath);
                    copyFileUsingStream(source, dest);


                } catch (Exception e) {

                }
            }
        }
    }



    private static void copyFileUsingStream(File source, File dest) throws IOException {

        File folder = new File(FabricLoader.getInstance().getGameDir()+"\\skins_saved\\");
        if (!folder.exists()) {
            folder.mkdir();
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    private static String firstNChars(String str, int n) {
        if (str == null) {
            return null;
        }
        return str.substring(0, Math.min(str.length(), n));
    }



}