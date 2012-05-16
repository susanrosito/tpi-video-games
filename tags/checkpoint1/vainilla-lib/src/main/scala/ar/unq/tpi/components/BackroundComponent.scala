package ar.unq.tpi.components
import java.awt.image.BufferedImage
import java.awt.Graphics2D

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameScene

import ar.edu.unq.tpi.traits.EventGameScene

class BackroundComponent[SceneType <: GameScene](sprite: Sprite, width: Double, heigth: Double, x: Double, y: Double) extends SpriteComponent[SceneType](sprite.scale(width / sprite.getWidth(), heigth / sprite.getHeight()), x, y) {
  this.setZ(-10)
}

class ScrollingBackroundComponent[SceneType <: GameScene](var sprite: ScrollingSprite, x: Double, y: Double) extends SpriteComponent[SceneType](sprite, x, y) {
}

class ScrollingSprite(image: BufferedImage, width: Int, a: Any) extends Sprite(image) {

  var currentPointer: Int = 0
  var step = 10

  def this(image: BufferedImage, width: Int) {
    this(image, width, null)
    currentPointer = (getImage().getWidth() - width) / 2
  }

  override def doRenderAt(x: Int, y: Int, graphics: Graphics2D) {
    graphics.drawImage(this.getImage(), x - currentPointer, y, null);
  }

  def avance() = {
    currentPointer += step
    if (currentPointer + width > getImage().getWidth()) {
      currentPointer = getImage().getWidth() - width
    }
  }

  def retroceder() = {
    currentPointer -= step
    if (currentPointer < 0) {
      currentPointer = 0
    }
  }
}
