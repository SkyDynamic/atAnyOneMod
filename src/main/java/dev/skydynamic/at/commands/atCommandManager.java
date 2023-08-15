package dev.skydynamic.at.commands;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Objects;

public class atCommandManager{

    public void atCommand(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = commandDispatcher.register(
            literal("at")
                .then(argument("targets", EntityArgumentType.players()).executes(context ->
                    execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), Text.of(""))
                )
                    .then(argument("message", MessageArgumentType.message()).executes(context ->
                            execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), MessageArgumentType.getMessage(context, "message")))))
        );

        commandDispatcher.register(literal("@").redirect(literalCommandNode));
    }

    public static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Text message) {
        String msg = message.getString();
        String messages;
        if (!Objects.equals(msg, "")) {
            messages = ": " + msg;
        } else {
            messages = "";
        }
        String sourceName = source.getName();
        targets.forEach(it ->
            {
                String targetPlayerName = it.getName().getString();
                it.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.VOICE, 1.0F, 1.0F);
                //#if MC>=12000
                //$$ source.sendMessage(Text.of("§7" + "你@了" + targetPlayerName + messages));
                //#else
                source.sendFeedback(Text.of("§7" + "你@了" + targetPlayerName + messages), false);
                //#endif
                it.sendMessage(Text.of("§7" + sourceName + "@了你" + messages), false);
            }
        );
        return 1;
    }

}
