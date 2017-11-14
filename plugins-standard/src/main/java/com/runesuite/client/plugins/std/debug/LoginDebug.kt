package com.runesuite.client.plugins.std.debug

import com.runesuite.client.game.api.live.LiveCanvas
import com.runesuite.client.game.raw.Client.accessor
import com.runesuite.client.plugins.DisposablePlugin
import com.runesuite.client.plugins.PluginSettings
import com.runesuite.client.plugins.util.ColorForm
import com.runesuite.client.plugins.util.FontForm
import java.awt.Font

class LoginDebug : DisposablePlugin<LoginDebug.Settings>() {

    override val defaultSettings = Settings()

    override fun start() {
        super.start()
        add(LiveCanvas.repaints.subscribe { g ->
            g.font = settings.font.get()
            g.color = settings.color.get()

            val strings = listOf(
                    "username: ${accessor.login_username}",
                    "password: ${accessor.login_password.isNotEmpty()}",
                    "isUsernameRemembered: ${accessor.login_isUsernameRemembered}",
                    "response0: ${accessor.login_response0}",
                    "response1: ${accessor.login_response1}",
                    "response2: ${accessor.login_response2}",
                    "response3: ${accessor.login_response3}"
            )
            val x = 20
            var y = 40
            strings.forEach { s ->
                g.drawString(s, x, y)
                y += g.font.size + 5
            }
        })
    }

    class Settings : PluginSettings() {
        val font = FontForm(Font.SANS_SERIF, FontForm.BOLD, 15f)
        val color = ColorForm()
    }
}