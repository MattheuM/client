package org.runestar.client.plugins.std

import org.runestar.client.api.Application
import org.runestar.client.game.api.live.Keyboard
import org.runestar.client.game.raw.Client
import org.runestar.client.game.raw.access.XRasterProvider
import org.runestar.client.plugins.PluginSettings
import org.runestar.client.utils.DisposablePlugin
import java.awt.TrayIcon
import java.awt.event.KeyEvent
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

class Screenshot : DisposablePlugin<Screenshot.Settings>() {

    companion object {
        const val IMAGE_FILE_EXTENSION = "png"
        const val SCREENSHOTS_DIRECTORY_NAME = "screenshots"
    }

    override val defaultSettings = Settings()

    lateinit var timeFormatter: DateTimeFormatter
    lateinit var screenshotDirectory: Path

    override fun start() {
        super.start()

        timeFormatter = createTimeFormatter()
        screenshotDirectory = directory.resolve(SCREENSHOTS_DIRECTORY_NAME)

        add(Keyboard.events
                .filter { it.extendedKeyCode == KeyEvent.VK_PRINTSCREEN && it.id == KeyEvent.KEY_RELEASED }
                .flatMapSingle { XRasterProvider.drawFull0.exit.firstOrError() }
                .map { it.instance.image as BufferedImage }
                .subscribe { takeScreenshot(it) }
        )
    }

    fun createTimeFormatter(): DateTimeFormatter {
        val zoneId = if (settings.localizeTimeZone) ZoneId.systemDefault() else ZoneId.from(ZoneOffset.UTC)
        return DateTimeFormatter.ofPattern(settings.dateTimeFormatterPattern)
                .withZone(zoneId)
    }

    fun takeScreenshot(img: RenderedImage) {
        val rsn = Client.accessor.localPlayerName
        val timeString = timeFormatter.format(Instant.now())
        val fileName = "$rsn.$timeString.$IMAGE_FILE_EXTENSION"
        val path = screenshotDirectory.resolve(fileName)
        try {
            Files.createDirectories(path)
            ImageIO.write(img, IMAGE_FILE_EXTENSION, path.toFile())
            if (settings.trayNotify) {
                Application.trayIcon.displayMessage(
                        "Screenshot Taken",
                        fileName,
                        TrayIcon.MessageType.NONE
                )
            }
        } catch (e: IOException) {
            logger.error("Failed to take screenshot", e)
        }
    }

    class Settings : PluginSettings() {
        val dateTimeFormatterPattern = "yyyy-MM-dd'T'kk-mm-ss,SSS"
        val localizeTimeZone = true
        val trayNotify = true
    }
}