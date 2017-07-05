package com.runesuite.client.game.live

import com.runesuite.client.base.Client.accessor
import com.runesuite.client.base.access.XProjectile
import com.runesuite.client.game.Projectile
import java.util.*

object Projectiles {

    val all: List<Projectile> get() {
        val nodes = accessor.projectiles
        val list = ArrayList<Projectile>()
        var node = nodes.sentinel.previous
        while (node != nodes.sentinel) {
            list.add(Projectile(node as XProjectile))
            node = node.previous
        }
        return list
    }
}