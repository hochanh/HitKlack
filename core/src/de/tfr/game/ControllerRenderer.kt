package de.tfr.game

import de.tfr.game.Controller.Control

/**
 * @author Tobse4Git@gmail.com
 */
class ControllerRenderer(val camera: Camera) {

    typealias TexturePair = Pair<TextureRegion, TextureRegion>

    private val buttons: SpriteSheet
    private val red: TexturePair
    private val blue: TexturePair
    private val yellow: TexturePair
    private val green: TexturePair
    private val batch: SpriteBatch
    private val width = 120

    init {
        val texture = Texture(Gdx.files.internal("buttons.png"))
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        buttons = SpriteSheet(texture, width, width, 2, 4)
        green = Pair(buttons[0], buttons[1])
        blue = Pair(buttons[2], buttons[3])
        yellow = Pair(buttons[4], buttons[5])
        red = Pair(buttons[6], buttons[7])
        batch = SpriteBatch()
    }

    fun render(controller: Controller) {
        batch.projectionMatrix = camera.combined
        batch.begin()

        val radius = width / 2F
        fun draw(textureRegion: TextureRegion, touchArea: Controller.TouchArea) {
            val pos = touchArea.rect.getCenter(Vector2()).sub(radius, radius)
            batch.draw(textureRegion, pos.x, pos.y)
        }

        fun button(button: TexturePair, control: Control): TextureRegion {
            return if (controller.isPressed(control)) button.second else button.first
        }


        draw(button(red, Control.Left), controller.touchAreas[0])
        draw(button(blue, Control.Right), controller.touchAreas[1])
        draw(button(yellow, Control.Bottom), controller.touchAreas[3])
        draw(button(green, Control.Top), controller.touchAreas[2])

        batch.end()
    }

    class SpriteSheet(val texture: Texture, val width: Int, val height: Int, val horizontalCount: Int, val verticalCount: Int) {
        private val regions: Array<TextureRegion> = Array(numTiles(), this::getTile)

        fun numTiles() = horizontalCount * verticalCount

        operator fun get(index: Int) = regions[index]

        fun getTile(index: Int): TextureRegion {
            val y = if (index > 0) index / horizontalCount else 0
            val x = index - (y * horizontalCount)
            return getTile(x, y)
        }

        private fun getTile(x: Int, y: Int) = TextureRegion(texture, x * width, y * height, width, height)
    }
}