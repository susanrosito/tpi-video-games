package ar.edu.unq.tpi
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.unq.tpi.collide.Bounds
import ar.unq.tpi.components.SpriteComponent
import resource.TraitResources
import ar.unq.tpi.components.ScaleSpriteComponent

trait BoundsScene extends GameScene with TraitResources{

  var boundLeft:SpriteComponent[GameScene] = null
  var boundRigth:SpriteComponent[GameScene] = null

  override def setGame(game: Game) = {
    boundLeft = new SpriteComponent(ScaleSpriteComponent.scale(sprite("bounds.png"), 20, game.getDisplayHeight()), 0, 0)
    boundRigth = new SpriteComponent(boundLeft.getAppearance(), game.getDisplayWidth()-20, 0)
    this.addComponents(boundLeft, boundRigth)
  }

}