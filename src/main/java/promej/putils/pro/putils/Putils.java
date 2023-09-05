package promej.putils.pro.putils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import promej.putils.pro.putils.gui.buttons.PUtilsButtonWidget;
import promej.putils.pro.putils.gui.screen.PUtilsScreen;
import promej.putils.pro.putils.config.ButtonsConfig;
import promej.putils.pro.putils.config.ModConfigs;
import promej.putils.pro.putils.config.buttons.PUButtonsList;
import promej.putils.pro.putils.config.buttons.PUButton;
import promej.putils.pro.putils.handlers.ChatHandler;
import promej.putils.pro.putils.handlers.JoinServerHandler;
import promej.putils.pro.putils.sounds.ModSounds;

import java.nio.charset.StandardCharsets;

public class Putils implements ModInitializer {
    public static String itemName = "air";
    public  static int rawId = 0;
    public static MinecraftClient mc;


    @Override
    public void onInitialize() {

        mc = MinecraftClient.getInstance();

        ModConfigs.registerConfigs();

        ButtonsConfig.loadButtonsFromConfig();

        PUButtonsList buttonsList = ButtonsConfig.getButtonsList();

        ModSounds.init();

        ClientPlayConnectionEvents.JOIN.register(new JoinServerHandler());

        ScreenEvents.AFTER_INIT.register((minecraftClient, screen, width, height) -> {

            if (screen instanceof HandledScreen) {


                int index = 0;
                int row = 0;

                for (PUButton pubutton : buttonsList.getButtons()){
                    if(index==6){
                        index = 0;
                        row++;
                    }

                    byte[] contents = pubutton.getName().getBytes(StandardCharsets.UTF_8);
                    String pubuttonname = new String(contents, StandardCharsets.UTF_16);

                    System.out.println(pubuttonname);

                    PUtilsButtonWidget buttonWidget = new PUtilsButtonWidget((HandledScreen)screen, index, row, pubutton.getName(), Item.byRawId(pubutton.getItemId()), button ->  MinecraftClient.getInstance().player.networkHandler.sendChatCommand(pubutton.getCommand()));
                    Screens.getButtons(screen).add(buttonWidget);
                    index++;
                }

            }

        });
    }

    private ButtonWidget.PressAction pressActionCommand(String command){

        ButtonWidget.PressAction pressAction = button -> {
            MinecraftClient.getInstance().player.networkHandler.sendCommand(command);
            //MinecraftClient.getInstance().player.sendMessage(Text.literal(command));
        };

        return pressAction;
    }

    private ButtonWidget.PressAction pressActionScreen(PUtilsScreen pUtilsScreen){

        ButtonWidget.PressAction pressAction = button -> MinecraftClient.getInstance().setScreen(pUtilsScreen);;

        return pressAction;
    }



}