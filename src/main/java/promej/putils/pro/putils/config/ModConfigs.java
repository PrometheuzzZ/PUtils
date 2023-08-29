package promej.putils.pro.putils.config;

import com.mojang.datafixers.util.Pair;


public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static String BUTTONS;

    public static final String defaultButtons = "{\n" +
            "  \"buttons\":[\n" +
            "    {\n" +
            "      \"name\":\"test\",\n" +
            "      \"command\":\"asd\",\n" +
            "      \"item_id\":123\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"test\",\n" +
            "      \"command\":\"asd\",\n" +
            "      \"item_id\":123\n" +
            "    }\n" +
            "  ]\n" +
            "}";


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