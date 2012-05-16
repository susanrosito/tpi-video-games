package ar.edu.unq.tpi
import java.awt.image.BufferedImage
import scala.xml.Node
import com.uqbar.vainilla.appearances.Animation
import ar.edu.unq.tpi.resource.TextureXMLReader
import com.uqbar.vainilla.appearances.Sprite

class FightMoves(var key: String, var sprite: Sprite, var sourceXML: Node, var force: Double, meantime: Double) extends TextureXMLReader {

  protected var animation: Animation = null
  protected var flipAnimation: Animation = null

  this.createAnimation()

  def this(key: String, sprite: Sprite, sourceXML: Node, force: Double) {
    this(key, sprite, sourceXML, force, 0.001)
  }

  protected def createAnimation() {
    this.animation = new Animation(meantime, buildSprites(sourceXML, key, sprite, false, 1).toArray)
    this.flipAnimation = new Animation(meantime, buildSprites(sourceXML, key, sprite, true, 1).toArray)
    sprite = null
    sourceXML = null
  }

  def getAnimation(orientation: Orientation.Orientation): Animation = {
    if (orientation.equals(Orientation.RIGHT)) {
      return flipAnimation
    } else {
      return animation
    }
  }

}

class IdleMoves(key: String, sprite: Sprite, sourceXML: Node, force: Double, meantime: Double) extends FightMoves(key, sprite, sourceXML, force, meantime) {

  override def createAnimation() {
    val flipAnimationSprites = buildSprites(sourceXML, key, sprite, true, 1)
    flipAnimationSprites.appendAll(flipAnimationSprites.reverse)
    this.flipAnimation = new Animation(meantime, flipAnimationSprites.toArray)
    val animationSprites = buildSprites(sourceXML, key, sprite, false, 1)
    animationSprites.appendAll(animationSprites.reverse)
    this.animation = new Animation(meantime, animationSprites.toArray)
  }

}