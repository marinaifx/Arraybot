package de.arraying.arraybot.command

import de.arraying.arraybot.command.commands.customization.custom.CustomCommand
import de.arraying.arraybot.command.commands.developer.eval.EvalCommand
import de.arraying.arraybot.command.commands.developer.reload.ReloadCommand
import de.arraying.arraybot.command.commands.developer.reload.subcommands.ReloadLanguagesSubCommand
import de.arraying.arraybot.command.commands.developer.reload.subcommands.ReloadShardsSubCommand
import de.arraying.arraybot.command.commands.developer.script.ScriptCommand
import de.arraying.arraybot.command.commands.developer.shards.ShardsCommand
import de.arraying.arraybot.command.commands.utils.commands.CommandsCommand
import de.arraying.arraybot.command.commands.utils.commands.subcommands.CommandsDisableSubCommand
import de.arraying.arraybot.command.commands.utils.commands.subcommands.CommandsEnableSubCommand
import de.arraying.arraybot.command.commands.utils.commands.subcommands.CommandsInfoSubCommand
import de.arraying.arraybot.command.commands.utils.commands.subcommands.CommandsListSubCommand
import de.arraying.arraybot.command.commands.utils.help.HelpCommand
import de.arraying.arraybot.command.commands.utils.ping.PingCommand
import de.arraying.arraybot.command.templates.DefaultCommand

/**
 * Copyright 2017 Arraying
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
enum class CommandCollection(val command: DefaultCommand) {

    /**
     * The command that shows information concerning commands.
     */
    COMMANDS(CommandsCommand(arrayOf(
            CommandsDisableSubCommand(),
            CommandsEnableSubCommand(),
            CommandsInfoSubCommand(),
            CommandsListSubCommand()
    ))),

    /**
     * The custom command management command.
     */
    CUSTOM(CustomCommand()),

    /**
     * The help command giving a basic overview of the bot, but not listing commands.
     */
    HELP(HelpCommand()),

    /**
     * The command to evaluate code.
     */
    EVAL(EvalCommand()),

    /**
     * The command to check the WebSocket ping.
     */
    PING(PingCommand()),

    /**
     * The command that is responsible for manually reloading certain modules.
     */
    RELOAD(ReloadCommand(arrayOf(
            ReloadLanguagesSubCommand(),
            ReloadShardsSubCommand()
    ))),

    /**
     * The command to use to quickly evaluate a long Zeus script.
     */
    SCRIPT(ScriptCommand()),

    /**
     * The command to display statistics about a shard.
     */
    SHARDS(ShardsCommand())

}