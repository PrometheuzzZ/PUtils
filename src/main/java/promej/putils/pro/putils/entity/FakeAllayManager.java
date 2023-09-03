/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package promej.putils.pro.putils.entity;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FakeAllayManager {
    private static final List<FakeAllayEntity> ENTITIES = new ArrayList<>();

    public static List<FakeAllayEntity> getFakeAllays() {
        return ENTITIES;
    }

    public static FakeAllayEntity get(String name) {
        for (FakeAllayEntity fp : ENTITIES) {
            if (fp.getEntityName().equals(name)) return fp;
        }

        return null;
    }

    public static void add(String name, ItemStack itemStack, double x, double y, double z) {
        FakeAllayEntity fakeAllayEntity = new FakeAllayEntity(name, itemStack, x, y, z);
        fakeAllayEntity.spawn();
        ENTITIES.add(fakeAllayEntity);
    }

    public static void remove(FakeAllayEntity fp) {
        ENTITIES.removeIf(fp1 -> {
            if (fp1.getEntityName().equals(fp.getEntityName())) {
                fp1.despawn();
                return true;
            }

            return false;
        });
    }

    public static void clear() {
        if (ENTITIES.isEmpty()) return;
        ENTITIES.forEach(FakeAllayEntity::despawn);
        ENTITIES.clear();
    }

    public static void forEach(Consumer<FakeAllayEntity> action) {
        for (FakeAllayEntity fakeAllayEntity : ENTITIES) {
            action.accept(fakeAllayEntity);
        }
    }

    public static int count() {
        return ENTITIES.size();
    }

    public static Stream<FakeAllayEntity> stream() {
        return ENTITIES.stream();
    }

    public static boolean contains(FakePlayerEntity fp) {
        return ENTITIES.contains(fp);
    }
}
