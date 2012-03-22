package ar.edu.unq.tpi
import java.awt.Color

import scala.collection.JavaConversions.seqAsJavaList

import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.colissions.CollisionDetector
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

import javax.imageio.ImageIO

class Avatar(scene: GameScene, animation: Animation, x: Int, y: Int, collitionNorthComponent: Seq[GameComponent[GameScene, Appearance]], collitionSouthComponent: Seq[GameComponent[GameScene, Appearance]]) extends GameComponent[GameScene, Animation](animation, x, y) {

  val images = ImageIO.read(this.getClass().getResource("img/avatar.png"))
  val animationH = 40
  val animationW = 32
  val animationCant = 5

  val delta = 5;
  var isMoving = false;
  var previousAnimation: Animation = null

  val animationN = new Animation(0.1, SpriteUtils.spriteS(animationW, animationH, animationCant, 2, images))
  val animationS = new Animation(0.1, SpriteUtils.spriteS(animationW, animationH, animationCant, 0, images))
  val animationL = new Animation(0.1, SpriteUtils.spriteS(animationW, animationH, animationCant, 1, images))
  val animationR = new Animation(0.1, SpriteUtils.spriteS(animationW, animationH, animationCant, 3, images))

  val peleaRyu = ImageIO.read(this.getClass().getResource("img/GotenkspritesheetHD.png"))
  
  val animationPH = 80
  val animationPW = 60
  val animationPCant = 5

  val animationOYUKE = new Animation(0.2, SpriteUtils.spriteS(animationPW, animationPH, 8, 1, peleaRyu))

  val animationPina = new Animation(0.2, SpriteUtils.spriteS(animationPW, animationPH, 7, 2, peleaRyu))

  val caja = new GameComponent[GameScene, Rectangle](new Rectangle(Color.RED, 25, 25), 300, 400)

  def this(scene: GameScene, x: Int, y: Int, collitionNorthComponent: Seq[GameComponent[GameScene, Appearance]], collitionSouthComponent: Seq[GameComponent[GameScene, Appearance]]) = {
    this(scene, null, x, y, collitionNorthComponent, collitionSouthComponent)
    this.setScene(scene)
    this.setAppearance(animationS)
    this.getScene().addComponent(caja)
  }

  override def update(deltaState: DeltaState) = {

    if (isMoving) {
      isMoving = !this.getAppearance().finish()
      if (!isMoving) {
        this.getAppearance().reset()
        this.setAppearance(previousAnimation)
      }
      super.update(deltaState)
      if (CollisionDetector.INSTANCE.collidesRectAgainstRect(this, caja)) {
          caja.move(delta, 0)
        }
    } else {

      if (deltaState.isKeyPressed(Key.UP)) {
        this.setAppearance(animationN)
        this.getAppearance().advance()
        if (!CollisionDetector.INSTANCE.collidesRectAgainstRect(this, collitionNorthComponent)) {
          this.move(0, -delta)
        }
        if (CollisionDetector.INSTANCE.collidesRectAgainstRect(this, caja)) {
          caja.move(0, -delta)
        }
      }

      if (deltaState.isKeyPressed(Key.DOWN)) {
        this.setAppearance(animationS)
        this.getAppearance().back()
        if (!CollisionDetector.INSTANCE.collidesRectAgainstRect(this, collitionSouthComponent)) {
          this.move(0, delta)
        }
        if (CollisionDetector.INSTANCE.collidesRectAgainstRect(this, caja)) {
          caja.move(0, delta)
        }
      }

      if (deltaState.isKeyPressed(Key.LEFT)) {
        this.setAppearance(animationL)
        this.getAppearance().back()
        if (this.getX() > 0) {
          this.move(-delta, 0)
          if (CollisionDetector.INSTANCE.collidesRectAgainstRect(this, caja)) {
            caja.move(-delta, 0)
          }
        }
      }

      if (deltaState.isKeyPressed(Key.RIGHT)) {
        this.setAppearance(animationR)
        this.getAppearance().advance()
        if (this.getX() + this.getAppearance().getWidth() < this.getGame().getDisplayWidth()) {
          this.move(delta, 0)
          if (CollisionDetector.INSTANCE.collidesRectAgainstRect(this, caja)) {
            caja.move(delta, 0)
          }
        }
      }

      if (deltaState.isKeyPressed(Key.A)) {
        previousAnimation = this.getAppearance()
        this.setAppearance(animationOYUKE)
        isMoving = true;
      }

      if (deltaState.isKeyPressed(Key.S)) {
        previousAnimation = this.getAppearance()
        this.setAppearance(animationPina)
        isMoving = true;
      }

      if (deltaState.isKeyPressed(Key.ENTER) || deltaState.isKeyPressed(Key.SPACE)) {
        getScene().addComponent(new Bomb(this.getX() + 2, this.getY()))
      }
    }
  }
}
