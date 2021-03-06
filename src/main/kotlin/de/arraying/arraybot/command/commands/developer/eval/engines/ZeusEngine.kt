package de.arraying.arraybot.command.commands.developer.eval.engines

import de.arraying.arraybot.Arraybot
import de.arraying.arraybot.command.CommandEnvironment
import de.arraying.arraybot.command.commands.developer.eval.EvalEngine
import de.arraying.arraybot.language.Message

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
class ZeusEngine: EvalEngine {

    /**
     * Evaluates Zeus code.
     */
    override fun evaluate(environment: CommandEnvironment, code: String): String {
        Arraybot.getInstance().scriptManager.executeStringRaw(code.split("\n").toTypedArray(), environment)
        return Message.COMMANDS_EVAL_EVALUATED.getContent(environment.channel)
    }

}