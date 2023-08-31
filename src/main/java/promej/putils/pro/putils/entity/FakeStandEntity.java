package promej.putils.pro.putils.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static promej.putils.pro.putils.Putils.mc;

public class FakeStandEntity extends ArmorStandEntity {

    private Text customName;
    private ItemStack headItem;
    public FakeStandEntity(double x, double y, double z, String name, ItemStack item) {
        super(mc.player.networkHandler.getWorld(), x, y, z);
        this.customName = Text.literal(name);
        this.headItem = item;
    }

    public void spawn(){
        this.unsetRemoved();
        this.setCustomName(customName);
        this.setCustomNameVisible(true);
        this.equipStack(EquipmentSlot.HEAD, headItem);

        this.setNoGravity(true);
        this.setGlowing(true);
        this.setStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10000), null);

       // mc.world.addEntity(getId(), this);
       // mc.player.getWorld().spawnEntity(this);
        mc.player.networkHandler.getWorld().addEntity(getId(),this);



    }

    public void despawn() {
        mc.world.removeEntity(getId(), RemovalReason.DISCARDED);
        setRemoved(RemovalReason.DISCARDED);
    }
}
