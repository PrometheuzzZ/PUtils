package promej.putils.pro.putils.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

import static promej.putils.pro.putils.Putils.mc;

public class FakeAllayEntity extends AllayEntity {

    private Vec3d cord;
    public FakeAllayEntity(String name, ItemStack itemStack, double x, double y, double z) {
        super(EntityType.ALLAY, mc.player.networkHandler.getWorld());
        cord = new Vec3d(0, 0,0);
        setNoGravity(true);
        setGlowing(true);
        setCustomName(Text.literal(name));
        setFlag(6, true);
        setPosition(x, y, z);
        equipStack(EquipmentSlot.MAINHAND, itemStack);
    }

    public void spawn(){
        unsetRemoved();
        mc.player.networkHandler.getWorld().addEntity(getId(),this);
    }

    public void despawn() {
        mc.world.removeEntity(getId(), RemovalReason.DISCARDED);
        setRemoved(RemovalReason.DISCARDED);
    }
    @Override
    public void travel(Vec3d movementInput) {



                this.updateVelocity(this.getMovementSpeed(), cord);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.9100000262260437));



        this.updateLimbs(false);
    }
}
