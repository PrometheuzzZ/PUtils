/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package promej.putils.pro.putils.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static net.minecraft.entity.effect.StatusEffects.GLOWING;
import static promej.putils.pro.putils.Putils.mc;

public class FakePlayerEntity extends OtherClientPlayerEntity {
    public boolean doNotPush, hideWhenInsideCamera;

    public FakePlayerEntity(PlayerEntity player, String name, float health, boolean copyInv) {
        super(mc.world, new GameProfile(UUID.randomUUID(), name));

        copyPositionAndRotation(player);


        //prevYaw = getYaw();
        //prevPitch = getPitch();
        //headYaw = player.headYaw;
        //prevHeadYaw = headYaw;
        //bodyYaw = player.bodyYaw;
        //prevBodyYaw = bodyYaw;

        Byte playerModel = player.getDataTracker().get(PlayerEntity.PLAYER_MODEL_PARTS);
        dataTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);
        //setInvisible(true);
        //setCustomName(Text.literal(name));
        //setCustomNameVisible(true);

        //getAttributes().setFrom(player.getAttributes());
        //setPose(player.getPose());

        capeX = getX();
        capeY = getY();
        capeZ = getZ();

        if (health <= 20) {
            setHealth(health);
        } else {
            setHealth(health);
            setAbsorptionAmount(health - 20);
        }

        if (copyInv) getInventory().clone(player.getInventory());
    }

    public void spawn() {
        unsetRemoved();
        this.setStatusEffect(new StatusEffectInstance(GLOWING, -1, 5), this);
        this.setGlowing(true);
        this.setNoGravity(true);
        mc.world.addEntity(getId(), this);
    }

    public void despawn() {
        mc.world.removeEntity(getId(), RemovalReason.DISCARDED);
        setRemoved(RemovalReason.DISCARDED);
    }

    @Nullable
    @Override
    protected PlayerListEntry getPlayerListEntry() {
        return mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
    }
}
