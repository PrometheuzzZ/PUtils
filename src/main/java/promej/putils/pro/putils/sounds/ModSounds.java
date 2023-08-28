package promej.putils.pro.putils.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent NEW_GLOBAL_PM;
    public static SoundEvent NEW_MESSAGE_PM;
    public static SoundEvent NEW_MONEY_PM;
    public static SoundEvent PJ_WELCOME;

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("putils", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        NEW_MESSAGE_PM = ModSounds.registerSoundEvent("new_message_pm");
        NEW_MONEY_PM = ModSounds.registerSoundEvent("new_money_pm");
        NEW_GLOBAL_PM = ModSounds.registerSoundEvent("new_global_pm");
        PJ_WELCOME = ModSounds.registerSoundEvent("welcome");
    }
}
