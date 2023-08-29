package promej.putils.pro.putils.config;

import java.util.ArrayList;
import java.util.List;
import com.mojang.datafixers.util.Pair;
public class ModConfigProvider implements SimpleConfig.DefaultConfig {

    private String configContents = "";

    public List<Pair> getConfigsList() {
        return configsList;
    }

    private final List<Pair> configsList = new ArrayList<>();

    public void addKeyValuePair(Pair<String, ?> keyValuePair, String comment) {
        configsList.add(keyValuePair);
        configContents += keyValuePair.getSecond();
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}