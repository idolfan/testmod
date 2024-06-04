package net.idolfan.testmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;

public class LoadEntityHandler implements ServerEntityEvents.Load {

    public static Identifier CREEPER_ID = new Identifier("testmod:creeper_alarm");
    public static SoundEvent CREEPER_ALARM = SoundEvent.of(CREEPER_ID);

    @Override
    public void onLoad(Entity entity, ServerWorld world) {
        if (entity.age != 0) return;

        int infinite = 1000000;

        if (entity instanceof CreeperEntity creeper) {
            NbtCompound nbt = new NbtCompound();
            boolean isNuke = Math.random() < 0.03;
            boolean isFast = Math.random() < 0.1;
            boolean isInvisible = Math.random() < 0.01;
            if (isInvisible) {
                creeper.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, infinite, 0));
                nbt.putByte("ExplosionRadius", (byte) 2);
                nbt.putShort("Fuse", (short) 55);
                creeper.readCustomDataFromNbt(nbt);
                return;
            }

            if (isNuke) {
                double strength = 1 + Math.random() * 0.6;
                nbt.putBoolean("powered", true);
                nbt.putByte("ExplosionRadius", (byte) (15 * strength));
                nbt.putShort("Fuse", (short) (38 * strength));
                creeper.playSound(SoundEvent.of(CREEPER_ID), (int)(1.5 * strength * strength), 1);
            }
            if (isFast) {
                nbt.putShort("Fuse", (short) 20);
                creeper.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, infinite, 2));
            }
            creeper.readCustomDataFromNbt(nbt);

        } else if (entity instanceof ZombieEntity || entity instanceof SpiderEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            boolean hasSlowness = Math.random() < 0.1;
            boolean hasStrength = Math.random() < 0.1;
            boolean hasSpeed = hasSlowness ? false : Math.random() < 0.06;
            boolean hasInvisibility = Math.random() < 0.01;

            boolean hasAbsorption = Math.random() < 0.20;
            boolean hasHealthBoost = Math.random() < 0.15;
            boolean hasFireResistance = Math.random() < 0.05;
            boolean hasResistance = Math.random() < 0.07;
            boolean hasRegeneration = Math.random() < 0.10;

            int slownessLevel = 0;

            if (hasSlowness) {
                slownessLevel = 1 + (int) (Math.random() * 2);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, infinite, slownessLevel - 1));
            }
            if (hasStrength) {
                int amplifier = (int) (Math.random() * 1.5) + slownessLevel;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, infinite, amplifier));
            }
            if (hasSpeed) {
                int amplifier = (int) (Math.random() * 1.3);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, infinite, amplifier));
            }
            if(hasInvisibility) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, infinite, 0));
            }
            if(hasAbsorption) {
                int amplifier = (int) (Math.random() * 5) + slownessLevel * 2;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, infinite, amplifier));
            }
            if(hasHealthBoost) {
                int amplifier = (int) (Math.random() * 4) + slownessLevel * 2;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, infinite, amplifier));
            }
            if(hasFireResistance) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, infinite, 0));
            }
            if(hasResistance) {
                int amplifier = (int) (Math.random() * 1.8) + slownessLevel;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, infinite, amplifier));
            }
            if(hasRegeneration) {
                int amplifier = (int) (Math.random() * 1.6) + slownessLevel;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, infinite, amplifier));
            }
        }
    }
}
