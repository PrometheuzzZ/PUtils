package promej.putils.pro.putils.config;

import com.mojang.datafixers.util.Pair;

import java.nio.charset.StandardCharsets;


public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static String BUTTONS;

    public static String defaultButtons = "{\n" +
            "  \"buttons\":[\n" +
            "    {\n" +
            "      \"name\":\"Эндер-сундук\",\n" +
            "      \"command\":\"ender\",\n" +
            "      \"item_id\":359\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Верстак\",\n" +
            "      \"command\":\"workbench\",\n" +
            "      \"item_id\":278\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Наковальня\",\n" +
            "      \"command\":\"anvil\",\n" +
            "      \"item_id\":397\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Точило\",\n" +
            "      \"command\":\"grindstone\",\n" +
            "      \"item_id\":1159\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Мусорка\",\n" +
            "      \"command\":\"dispose\",\n" +
            "      \"item_id\":870\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Ежедневный бонус\",\n" +
            "      \"command\":\"dayly1\",\n" +
            "      \"item_id\":955\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Хранилище клана\",\n" +
            "      \"command\":\"clan chest open\",\n" +
            "      \"item_id\":277\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Магазин\",\n" +
            "      \"command\":\"shop\",\n" +
            "      \"item_id\":1154\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";


    public static void registerConfigs() {
        configs = new ModConfigProvider();

        createConfigs();

        CONFIG = SimpleConfig.of("putils").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {



        configs.addKeyValuePair(new Pair<>("BUTTONS", defaultButtons), "");

    }

    private static void assignConfigs() {
        BUTTONS = CONFIG.getOrDefault("BUTTONS", "Nothing");


        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}