package org.runestar.client.game.api.live

import org.runestar.client.game.api.GlobalTile
import org.runestar.client.game.api.Region
import org.runestar.client.game.api.Scene
import org.runestar.client.game.raw.CLIENT

object LiveScene : Scene {

    override fun getHeight(x: Int, y: Int, plane: Int): Int {
        return CLIENT.tiles_heights[plane][x][y]
    }

    override fun getRenderFlags(x: Int, y: Int, plane: Int): Byte {
        return CLIENT.tiles_renderFlags[plane][x][y]
    }

    override fun getCollisionFlags(x: Int, y: Int, plane: Int): Int {
        return CLIENT.collisionMaps[plane].flags[x][y]
    }

    override val base get() = GlobalTile(CLIENT.baseX, CLIENT.baseY, 0)

    val regionIds: IntArray get() = CLIENT.regions ?: IntArray(0)

    val regions: List<Region> get() = regionIds.map { Region(it) }

    override fun toString(): String {
        return "LiveScene(base=$base)"
    }
}