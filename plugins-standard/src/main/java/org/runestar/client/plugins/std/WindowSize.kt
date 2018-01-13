package org.runestar.client.plugins.std

import org.runestar.client.api.Application
import org.runestar.client.plugins.Plugin
import org.runestar.client.plugins.PluginSettings
import java.awt.Dimension

class WindowSize : Plugin<WindowSize.Settings>() {

    override val defaultSettings = Settings()

    override fun start() {
        super.start()
        Application.frame.size = settings.size
    }

    class Settings : PluginSettings() {
        val size = Application.frame.preferredSize
    }
}