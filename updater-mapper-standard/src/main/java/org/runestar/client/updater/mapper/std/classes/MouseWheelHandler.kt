package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.annotations.SinceVersion
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2
import org.runestar.client.updater.mapper.tree.Method2
import org.objectweb.asm.Type.INT_TYPE
import org.objectweb.asm.Type.VOID_TYPE
import java.awt.event.MouseWheelListener

class MouseWheelHandler : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.interfaces.contains(MouseWheelListener::class.type) }

    class rotation : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == INT_TYPE }
    }

    @MethodParameters()
    @SinceVersion(141)
    @DependsOn(MouseWheel.useRotation::class)
    class useRotation : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.mark == method<MouseWheel.useRotation>().mark }
    }

    @MethodParameters("component")
    class addTo : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.instructions.any { it.isMethod && it.methodName == "addMouseWheelListener" } }
    }

    @MethodParameters("component")
    class removeFrom : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.instructions.any { it.isMethod && it.methodName == "removeMouseWheelListener" } }
    }
}