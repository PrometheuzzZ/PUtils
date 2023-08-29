package promej.putils.pro.putils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import promej.putils.pro.putils.GUI.buttons.PUtilsButtonWidget;
import promej.putils.pro.putils.GUI.screen.PUtilsScreen;
import promej.putils.pro.putils.config.ButtonsConfig;
import promej.putils.pro.putils.config.ModConfigs;
import promej.putils.pro.putils.config.buttons.PUButtonsList;
import promej.putils.pro.putils.config.buttons.PUButton;
import promej.putils.pro.putils.handlers.JoinServerHandler;
import promej.putils.pro.putils.sounds.ModSounds;

public class Putils implements ModInitializer {




    @Override
    public void onInitialize() {
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
                    PUtilsButtonWidget buttonWidget = new PUtilsButtonWidget((HandledScreen)screen, index, row, pubutton.getName(), Item.byRawId(pubutton.getItemId()), button ->  MinecraftClient.getInstance().player.networkHandler.sendChatCommand(pubutton.getCommand()));
                    Screens.getButtons(screen).add(buttonWidget);
                    index++;
                }

            /*    PUtilsButtonWidget enderOpenButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 0, 0, "Эндер-сундук", Item.byRawId(359), button ->  MinecraftClient.getInstance().player.networkHandler.sendChatCommand("ender") );
                PUtilsButtonWidget workbenchButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 1, 0, "Верстак", Item.byRawId(278), pressActionCommand("workbench"));
                PUtilsButtonWidget anvilButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 2, 0, "Наковальня", Item.byRawId(397), pressActionCommand("anvil"));
                PUtilsButtonWidget tochiloButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 3, 0, "Точило", Item.byRawId(1159), pressActionCommand("grindstone"));
                PUtilsButtonWidget trashButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 4, 0, "Мусорка", Item.byRawId(870), pressActionCommand("dispose"));
                PUtilsButtonWidget daylyButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 5, 0, "Ежедневный бонус", Item.byRawId(955), pressActionCommand("dayly1"));
                //PUtilsButtonWidget settingButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 6, 0,"Быстрое меню", Items.BARRIER, pressActionScreen(new PUtilsScreen(new FastMenu())));


                PUtilsButtonWidget vaultButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 0, 1, "Хранилище клана", Item.byRawId(277), pressActionCommand("clan chest"));
                PUtilsButtonWidget shopButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 1, 1, "Магазин", Item.byRawId(1154), pressActionCommand("shop"));

                Screens.getButtons(screen).add(enderOpenButtonWidget);
                Screens.getButtons(screen).add(workbenchButtonWidget);
                Screens.getButtons(screen).add(vaultButtonWidget);
                Screens.getButtons(screen).add(anvilButtonWidget);
                Screens.getButtons(screen).add(trashButtonWidget);
                Screens.getButtons(screen).add(daylyButtonWidget);
                Screens.getButtons(screen).add(tochiloButtonWidget);
                Screens.getButtons(screen).add(shopButtonWidget);
                //Screens.getButtons(screen).add(settingButtonWidget); */
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