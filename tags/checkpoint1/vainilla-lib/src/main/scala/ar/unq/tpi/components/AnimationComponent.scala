package ar.unq.tpi.components
import java.awt.image.BufferedImage

import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

import ar.edu.unq.tpi.resource.TextureXMLReader
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.unq.tpi.collide.CollitionBounds
import ar.unq.tpi.collide.PixelPerfectCollition

class AnimationComponent[SceneType <:GameScene, T <:EventGameComponent[T]](var animation: Animation, x: Double, y: Double) extends GameComponent[SceneType, Animation](animation, x, y) with CollitionBounds[SceneType,Animation] 
				with TraitResources with PixelPerfectCollition[SceneType, Animation] with TextureXMLReader with EventGameComponent[T]{

  def getImage(): BufferedImage = this.getAppearance().getCurrentSprite().getImage()
  def width: Double = this.getAppearance().getWidth()
  def height: Double = this.getAppearance().getHeight()

}