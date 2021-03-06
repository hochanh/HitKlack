package de.tfr.game.model

/**
 * @author Tobse4Git@gmail.com
 */
class GameField(var size: Int) : Iterable<Ring> {

    override fun iterator() = rings.iterator()

    private var rings: Array<Ring>

    init {
        rings = Array(size, ::Ring)
    }

    operator fun get(index: Int) = rings[index]

    fun reset() = rings.forEach { it.reset() }

}