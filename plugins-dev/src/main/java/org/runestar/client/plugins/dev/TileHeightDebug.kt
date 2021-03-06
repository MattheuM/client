package org.runestar.client.plugins.dev

import org.kxtra.swing.graphics.drawString
import org.kxtra.swing.point2d.minus
import org.runestar.client.api.util.DisposablePlugin
import org.runestar.client.api.Fonts
import org.runestar.client.game.api.Scene
import org.runestar.client.game.api.SceneTile
import org.runestar.client.game.api.live.Game
import org.runestar.client.game.api.live.LiveCanvas
import org.runestar.client.game.api.live.LiveScene
import org.runestar.client.plugins.spi.PluginSettings
import java.awt.Color
import java.awt.Point

class TileHeightDebug : DisposablePlugin<PluginSettings>() {

    override val defaultSettings = PluginSettings()

    override fun start() {
        add(LiveCanvas.repaints.subscribe { g ->
            g.font = Fonts.PLAIN_12
            g.color = Color.WHITE

            for (x in 0 until Scene.SIZE) {
                for (y in 0 until Scene.SIZE) {
                    val tile = SceneTile(x, y, Game.plane)
                    val height = LiveScene.getHeight(tile)
                    val center = tile.center.toScreen()
                    if (center != null) {
                        val pt = center - Point(g.font.size, g.font.size)
                        g.drawString(height.toString(), pt)
                    }
                }
            }
        })
    }
}