package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameScene
import ar.unq.tpi.components.AnimateSprite
import ar.unq.tpi.components.CenterComponent
import ar.unq.tpi.components.ScrollingBackroundComponent
import ar.unq.tpi.components.ScrollingSprite
import ar.unq.tpi.components.Selectable
import ar.unq.tpi.components.Stats
import traits.EventGameComponent

class GamePlayScene(game: Fight, character: CharacterAppearance) extends GameScene with BoundsScene with EventGameScene with EventGameComponent[GamePlayScene] {

  var finish = false

  var character1 = new CharacterFight(new Player1(), new Character(character), this, 200, 800)
  var character2 = new CharacterFight(new Player2(), new Character(Litchi), this, 1200, 700)

  val winAnimate = new AnimateSprite(GameImage.WIN_IMAGE)
  val loseAnimate = new AnimateSprite(GameImage.LOSE_IMAGE)
  
  var finishAnimation = new CenterComponent(winAnimate, game.getDisplayWidth(), game.getDisplayHeight())

  var backGround: ScrollingBackroundComponent[GameScene] = null

  def this(game: Fight, character: CharacterAppearance, arena: Selectable) {
    this(game, character)

    character1.oponent = character2
    character2.oponent = character1

    configureListeners()

    var backGroundSprite = arena.image.scale((2 * game.getDisplayWidth()) / arena.image.getWidth(), game.getDisplayHeight() / arena.image.getHeight())
    backGround = new ScrollingBackroundComponent[GameScene](new ScrollingSprite(backGroundSprite.getImage(), game.getDisplayWidth()), 0, 0)

    this.addComponent(new Stats(0, 0))
    
    this.addComponents(backGround)
    this.addComponents(character1, character2, new LifeBar(GameImage.LIFE_BAR, 100, 100, character1.character.life), new LifeBar(GameImage.LIFE_BAR, 1000, 100, character2.character.life))
  }

  def configureListeners() {
    character1.addEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, onLeftMapMove)
    character1.addEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, onRigthMapMove)
    character2.addEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, onLeftMapMove)
    character2.addEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, onRigthMapMove)

    character1.addEventListener(GameEvents.DEATH, onDeath1)
    character2.addEventListener(GameEvents.DEATH, onDeath2)
  }

  def onDeath1(event: Event[CharacterFight, Nothing]) {
    onDeath()
    finishAnimation.setAppearance(loseAnimate)
    this.addComponent(finishAnimation)
  }

  def onDeath2(event: Event[CharacterFight, Nothing]) {
    onDeath()
    finishAnimation.setAppearance(winAnimate)
    this.addComponent(finishAnimation)
  }

  def onDeath() {
    if (!finish) {
      finish = true
      new Thread() {
        override def run() {
          Thread.sleep(5 * 1000)
          dispatchEvent(new Event(GameEvents.FINISH_FIGTH, GamePlayScene.this))
        }
      }.start()
    }
  }

  def onRigthMapMove(event: Event[CharacterFight, Orientation.Orientation]) {
    backGround.sprite.avance()
  }

  def onLeftMapMove(event: Event[CharacterFight, Orientation.Orientation]) {
    backGround.sprite.retroceder()
  }

  def centerX = game.getDisplayWidth() / 2
  def centerY = game.getDisplayHeight() / 2

}