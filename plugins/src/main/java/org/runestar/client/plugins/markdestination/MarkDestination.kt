package org.runestar.client.plugins.markdestination

import org.runestar.client.api.util.DisposablePlugin
import org.runestar.client.api.forms.RgbaForm
import org.runestar.client.game.api.live.Game
import org.runestar.client.game.api.live.LiveCanvas
import org.runestar.client.plugins.spi.PluginSettings
import java.awt.BasicStroke
import java.awt.Color
import java.awt.RenderingHints

class MarkDestination : DisposablePlugin<MarkDestination.Settings>() {

    override val defaultSettings = Settings()

    override val name = "Mark Destination"

    override fun start() {
        val stroke = BasicStroke(settings.stroke)
        val color = settings.color.get()
        add(LiveCanvas.repaints.subscribe { g ->
            val destination = Game.destination ?: return@subscribe
            if (!destination.isLoaded) return@subscribe
            val outline = destination.outline()
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.color = color
            g.stroke = stroke
            g.draw(outline)
        })
    }

    class Settings(
            val stroke: Float = 2.0f,
            val color: RgbaForm = RgbaForm(Color.WHITE)
    ) : PluginSettings()
}