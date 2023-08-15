package dev.skydynamic.at;

import net.fabricmc.api.ModInitializer;
//#if MC>=11904
//$$ import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//#else
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#endif
import dev.skydynamic.at.commands.atCommandManager;

public class atServer implements ModInitializer {

	@Override
	public void onInitialize() {
		//#if MC>=11904
		//$$ CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> new atCommandManager().atCommand(dispatcher));
		//#else
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> new atCommandManager().atCommand(dispatcher));
		//#endifs
	}

}
