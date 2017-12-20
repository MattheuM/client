package com.runesuite.client.game.api

import com.runesuite.client.game.api.live.Widgets
import com.runesuite.client.game.raw.Client
import com.runesuite.client.game.raw.Wrapper
import com.runesuite.client.game.raw.access.XWidget
import com.runesuite.client.game.raw.access.XWidgetNode

class WidgetGroup(
        val id: Int,
        override val accessor: Array<XWidget>
) : Wrapper() {

    val parent: Widget.Parent? get() {
        val table = Client.accessor.widgetNodes
        var node = table.first() as XWidgetNode?
        while (node != null) {
            if (node.id == id) {
                return Widgets[WidgetId(node.key.toInt())]
            }
            node = table.next() as XWidgetNode?
        }
        return null
    }

    val roots: List<Widget.Parent> get() = all.filter { it.predecessor == null }

    val size get() = accessor.size

    val flat: List<Widget> get() = all.flatMap { it.flat }

    val all: List<Widget.Parent> get() = accessor.map { Widget.Parent(it) }

    operator fun get(id: Int): Widget.Parent? = accessor.getOrNull(id)?.let { Widget.Parent(it) }
}