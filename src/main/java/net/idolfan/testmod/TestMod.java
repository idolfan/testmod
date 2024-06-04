package net.idolfan.testmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.idolfan.testmod.command.GetSacrificeCommand;
import net.idolfan.testmod.event.*;
import net.idolfan.testmod.util.ModLootTableModifiers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TestMod implements ModInitializer {
	public static final String MOD_ID = "testmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ArrayList<World> server_world = new ArrayList<>();

	@Override
	public void onInitialize() {
		ServerEntityEvents.ENTITY_LOAD.register(new LoadEntityHandler());
		EntitySleepEvents.ALLOW_SLEEPING.register(new DisableSleepHandler());
		ServerWorldEvents.LOAD.register(new GetWorldHandler());
		ServerEntityEvents.ENTITY_UNLOAD.register(new ExtendWorldBorderHandler());

		CommandRegistrationCallback.EVENT.register(GetSacrificeCommand::register);
		Registry.register(Registries.SOUND_EVENT, LoadEntityHandler.CREEPER_ID, LoadEntityHandler.CREEPER_ALARM);

	}
}