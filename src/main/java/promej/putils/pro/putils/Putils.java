package promej.putils.pro.putils;

import io.github.cottonmc.cotton.gui.GuiDescription;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import promej.putils.pro.putils.GUI.FastMenu;
import promej.putils.pro.putils.GUI.buttons.PUtilsButtonWidget;
import promej.putils.pro.putils.GUI.screen.PUtilsScreen;
import promej.putils.pro.putils.handlers.JoinServerHandler;

public class Putils implements ModInitializer {
    @Override
    public void onInitialize() {

        ClientPlayConnectionEvents.JOIN.register(new JoinServerHandler());

        ScreenEvents.AFTER_INIT.register((minecraftClient, screen, width, height) -> {

            if (screen instanceof HandledScreen) {



                PUtilsButtonWidget enderOpenButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 0, 0, "Эндер-сундук", Items.ENDER_CHEST, button ->  MinecraftClient.getInstance().player.networkHandler.sendChatCommand("ender") );
                PUtilsButtonWidget workbenchButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 1, 0, "Верстак", Items.CRAFTING_TABLE, pressActionCommand("workbench"));
                PUtilsButtonWidget anvilButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 2, 0, "Наковальня", Items.ANVIL, pressActionCommand("anvil"));
                PUtilsButtonWidget trashButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 4, 0, "Мусорка", Items.LAVA_BUCKET, pressActionCommand("dispose"));
                PUtilsButtonWidget daylyButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 5, 0, "Ежедневный бонус", Items.GOLD_NUGGET, pressActionCommand("dayly1"));
                PUtilsButtonWidget tochiloButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 3, 0, "Точило", Items.GRINDSTONE, pressActionCommand("grindstone"));
                PUtilsButtonWidget settingButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 6, 0,"Быстрое меню", Items.BARRIER, pressActionScreen(new PUtilsScreen(new FastMenu())));


                PUtilsButtonWidget vaultButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 0, 1, "Хранилище клана", Items.CHEST, pressActionCommand("clan chest"));
                PUtilsButtonWidget shopButtonWidget = new PUtilsButtonWidget((HandledScreen)screen, 1, 1, "Магазин", Items.BARREL, pressActionCommand("shop"));

                Screens.getButtons(screen).add(enderOpenButtonWidget);
                Screens.getButtons(screen).add(workbenchButtonWidget);
                Screens.getButtons(screen).add(vaultButtonWidget);
                Screens.getButtons(screen).add(anvilButtonWidget);
                Screens.getButtons(screen).add(trashButtonWidget);
                Screens.getButtons(screen).add(daylyButtonWidget);
                Screens.getButtons(screen).add(tochiloButtonWidget);
                Screens.getButtons(screen).add(shopButtonWidget);
                Screens.getButtons(screen).add(settingButtonWidget);
            }

        });
    }

    private ButtonWidget.PressAction pressActionCommand(String command){

        ButtonWidget.PressAction pressAction = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                MinecraftClient.getInstance().player.networkHandler.sendCommand(command);
                MinecraftClient.getInstance().player.sendMessage(Text.literal(command));
            }
        };

        return pressAction;
    }

    private ButtonWidget.PressAction pressActionScreen(PUtilsScreen pUtilsScreen){

        ButtonWidget.PressAction pressAction = button -> MinecraftClient.getInstance().setScreen(pUtilsScreen);;

        return pressAction;
    }



}