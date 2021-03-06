package org.runestar.client.game.api.live

import hu.akarnokd.rxjava2.swing.SwingObservable
import io.reactivex.Observable
import org.runestar.client.game.api.MouseCross
import org.runestar.client.game.api.MouseCrossColor
import org.runestar.client.game.api.SceneElementTag
import org.runestar.client.game.raw.CLIENT
import java.awt.Point
import java.awt.event.MouseEvent

object Mouse {

    val x get() = CLIENT.mouseHandler_x

    val y get() = CLIENT.mouseHandler_y

    val location get() = Point(x, y)

    val viewportX get() = CLIENT.viewportMouse_x

    val viewportY get() = CLIENT.viewportMouse_y

    val viewportLocation get() = Point(viewportX, viewportY)

    val isInViewport get() = CLIENT.viewportMouse_isInViewport

    val entityCount get() = CLIENT.viewportMouse_entityCount

    val tags get() = List(entityCount) { SceneElementTag(CLIENT.viewportMouse_entityTags[it], Game.plane) }

    val cross get() = MouseCrossColor.of(CLIENT.mouseCrossColor)?.let { MouseCross(it, CLIENT.mouseCrossState) }

    /**
     * @see[java.awt.event.MouseListener]
     * @see[java.awt.event.MouseMotionListener]
     * @see[java.awt.event.MouseWheelListener]
     */
    val events: Observable<MouseEvent> = LiveCanvas.canvasReplacements.flatMap { SwingObservable.mouse(it) }

    override fun toString(): String {
        return "Mouse(location=$location, cross=$cross)"
    }
}