package promej.putils.pro.putils.config;

import com.google.gson.Gson;
import promej.putils.pro.putils.config.buttons.PUButtonsList;

import java.nio.charset.StandardCharsets;

import static promej.putils.pro.putils.config.ModConfigs.BUTTONS;
import static promej.putils.pro.putils.config.ModConfigs.defaultButtons;

public class ButtonsConfig {

    private static PUButtonsList buttonsList;

    public static void loadButtonsFromConfig(){

        Gson gson  = new Gson();
        PUButtonsList buttonsConfig1;

        buttonsConfig1 = gson.fromJson(BUTTONS, PUButtonsList.class);


        buttonsList = buttonsConfig1;
    }

    public static PUButtonsList getButtonsList(){
        return buttonsList;
    }

}
