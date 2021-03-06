package org.runestar.client.plugins.dev

import org.runestar.client.game.api.live.Menu
import org.runestar.client.plugins.spi.PluginSettings
import org.runestar.client.api.util.DisposablePlugin

class MenuActionLogger : DisposablePlugin<PluginSettings>() {

    override val defaultSettings = PluginSettings()

    override fun start() {
        add(Menu.actions.subscribe { a ->
            logger.info(a.toString())
        })
    }
}