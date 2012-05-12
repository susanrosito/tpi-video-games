package ar.edu.unq.tpi

import java.awt.Dimension
import scala.collection.mutable.Buffer
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import javax.imageio.ImageIO
import java.awt.Toolkit
import ar.unq.tpi.components.Stats
import ar.unq.tpi.components.Selectable
import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.components.AnimateSprite
import ar.edu.unq.tpi.traits.Event
import ar.unq.tpi.components.ScrollingBackroundComponent
import ar.unq.tpi.components.ScrollingSprite

class Fight extends Game with TraitResources {

  var winAnimation = new GameComponent[GameScene, AnimateSprite](new AnimateSprite(getImage("win.png")), centerX, centerY) {
    override def update(deltaState: DeltaState) = {
      super.update(deltaState)
      this.setX(centerX - (this.getAppearance().getWidth() / 2))
      this.setY(centerY - (this.getAppearance().getHeight() / 2))
    }
  }
  var loseAnimation = new GameComponent[GameScene, AnimateSprite](new AnimateSprite(getImage("lose.png")), centerY, centerY)

  var backGround: ScrollingBackroundComponent[GameScene] = null

  protected def initializeResources(): Unit = {
  }

  protected def setUpScenes(): Unit = {
    setCurrentScene(new SelectCharacterScene(this))
    //    playGame(Ragna, Arena1)
  }

  def selectArena(character: SelectableCharacter) {
    setCurrentScene(new SelectArenaScene(this, character.character))
  }

  def playGame(character: CharacterAppearance, arena: Selectable) {
    var gameScene: GamePlayScene = new GamePlayScene()
    var loading = new LoadingScene(this)
    setCurrentScene(loading)

    new Thread() {
      override def run() {

        var originalLifeSprite = sprite("barra.png")

        var lifeCharacter1 = new GameComponent[GameScene, Sprite](originalLifeSprite.copy(), 100, 100)
        var lifeCharacter2 = new GameComponent[GameScene, Sprite](originalLifeSprite.copy(), 1000, 100)

        var finish = false

        var character1 = new CharacterFight(new Player1(), new Character(character), gameScene, 200, 800) {
          override def update(deltaState: DeltaState) = {
            if (!finish) {
              super.update(deltaState)
            }

            if (this.character.death) {
              finish = true
              getCurrentScene().addComponent(loseAnimation)
              lifeCharacter1.setAppearance(originalLifeSprite.scale(0.01, 1))
              new Thread() {
                override def run() {
                  Thread.sleep(5 * 1000)
                  setUpScenes()
                }
              }.start()
            } else {
              lifeCharacter1.setAppearance(originalLifeSprite.scale(this.character.life / 100, 1))
            }
          }
        }

        var character2 = new CharacterFight(new Player2(), new Character(Litchi), gameScene, 1200, 800) {
          override def update(deltaState: DeltaState) = {
            if (!finish) {
              super.update(deltaState)
            }
            if (this.character.death) {
              finish = true
              getCurrentScene().addComponent(winAnimation)
              lifeCharacter2.setAppearance(originalLifeSprite.scale(0.01, 1))
              new Thread() {
                override def run() {
                  Thread.sleep(5 * 1000)
                  setUpScenes()
                }
              }.start()
            } else {
              lifeCharacter2.setAppearance(originalLifeSprite.scale(this.character.life / 100D, 1))
            }

          }

        }

        character1.oponent = character2
        character2.oponent = character1
        character1.addEventListener("aa", onMapMove)
        character2.addEventListener("aa", onMapMove)

        setCurrentScene(gameScene)

        backGround = new ScrollingBackroundComponent[GameScene](
            new ScrollingSprite(arena.image.scale((2*getDisplayWidth())/arena.image.getWidth(), getDisplayHeight()/arena.image.getHeight()).getImage(), getDisplayWidth()), 0, 0)

        backGround.setZ(-100)

        gameScene.addComponent(new Stats(0, 0) {
          override def text(deltaState: DeltaState): String = {
            "FPS: " + (1 / deltaState.getDelta()).toInt 
          }
        })
        gameScene.addComponents(backGround)
        gameScene.addComponents(character1, character2, lifeCharacter1, lifeCharacter2)
        //    this.getCurrentScene().addComponent(avatar2)
      }
    }.start()

  }

  def onMapMove(event: Event[CharacterFight, Orientation.Orientation]) {
    if (event.data.equals(Orientation.LEFT)) {
      backGround.sprite.avance()
    } else {
      backGround.sprite.retroceder()
    }
  }

  def getDisplaySize(): Dimension = Toolkit.getDefaultToolkit().getScreenSize()

  def centerX = this.getDisplayWidth() / 2
  def centerY = this.getDisplayHeight() / 2

  def getTitle(): String = "Demo"

}

object MainFight {

  def main(args: Array[String]) {
    new DesktopGameLauncher(new Fight()).launch();
  }
}


