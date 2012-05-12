package ar.edu.unq.tpi
import java.awt.Color
import java.awt.Dimension
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.collide.Colicionable
import ar.unq.tpi.components.SelectComponent
import ar.unq.tpi.components.SelectComponent
import ar.unq.tpi.components.SelectScene
import ar.unq.tpi.components.Selectable
import ar.unq.tpi.components.MouseComponent
import ar.unq.tpi.components.SpriteComponent

class InitialScene(game: Fight) extends GameScene() with TraitResources {

  this.addComponent(new GameComponent[GameScene, Appearance](sprite("fondo1.png").scale(1, 1.3), 0, 0) {
    override def update(delta: DeltaState) {
      if (delta.isKeyBeingHold(Key.ENTER)) {
        //        game.playGame()
      }
    }
  })
}

class LoadingScene(game: Fight) extends GameScene() with TraitResources {

  var loading = sprite("loading.png")
  loading = loading.scale(game.getDisplayWidth() / loading.getWidth(), game.getDisplayHeight() / loading.getHeight())
  this.addComponent(new GameComponent[GameScene, Rectangle](new Rectangle(Color.WHITE, game.getDisplayWidth(), game.getDisplayHeight()), 0, 0))
  this.addComponent(new GameComponent[GameScene, Appearance](loading, 0, 0))
}

class SelectArenaScene(game: Fight, character: CharacterAppearance) extends SelectScene() with TraitResources {

  val sizeBigArena = new Dimension(900, 500)
  val width = 300
  val height = 250
  val initialX = 20
  val initialY = 20
  val padding = 50
  var arenaSelected: Selectable = Arena1

  this.addComponent(new SelectComponent(this, Arena1, initialX, initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena2, initialX + (width) + padding, initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena3, initialX + (2 * width) + (2 * padding), initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena4, initialX + (3 * width) + (3 * padding), initialY, width, height))

  var bigArenaComponent = new GameComponent[GameScene, Appearance](scaleBigArena(arenaSelected.image), 400, 300)
  this.addComponent(new GameComponent[GameScene, Animation](character.selectedAnimation, 10, 300))

  this.addComponent(bigArenaComponent)

  var start = sprite("start.png")
  this.addComponent(new GameComponent[GameScene, Sprite](start, game.getDisplayWidth() - (start.getWidth() + 100), game.getDisplayHeight() - (start.getHeight() + 100)) with Colicionable[GameScene, Sprite] {
    override def update(delta: DeltaState) {
      if (delta.isMouseButtonPressed(MouseButton.LEFT)) {
        if (isClickMe(delta.getCurrentMousePosition())) {
          game.playGame(character, arenaSelected)
        }
      }
    }
    def getImage() = this.getAppearance().getImage()
    def width = this.getAppearance().getWidth()
    def height = this.getAppearance().getHeight()
  })

  this.addComponent(new GameComponent[GameScene, Sprite](sprite("sword.png"), 0, 0) {
    override def update(delta: DeltaState) {
      this.setX(delta.getCurrentMousePosition().getX())
      this.setY(delta.getCurrentMousePosition().getY())
    }
  })

  override def selectItem(arena: Selectable) {
    arenaSelected = arena
    bigArenaComponent.setAppearance(scaleBigArena(arena.image))

  }

  def scaleBigArena(sprite: Sprite) = sprite.scale(sizeBigArena.width / sprite.getWidth(), sizeBigArena.height / sprite.getHeight())
}

class SelectCharacterScene(game: Fight) extends SelectScene() with TraitResources {

  val sizeBigArena = new Dimension(900, 500)
  val width = 300
  val height = 250
  val initialX = 20
  val initialY = 20
  val padding = 50
  var characterSelected: SelectableCharacter = new SelectableCharacter(Ragna)
  
  var backgrond = sprite("00101.png")
  backgrond = backgrond.scale(game.getDisplayWidth()/backgrond.getWidth(), game.getDisplayHeight()/backgrond.getHeight())
  
  this.addComponent(new SpriteComponent(backgrond, 0,0))
  this.addComponent(new SelectComponent(this, characterSelected, initialX, initialY, width, height))
  this.addComponent(new SelectComponent(this, new SelectableCharacter(Litchi), initialX + (3 * width) + (3 * padding), initialY, width, height))

  var bigArenaComponent = new GameComponent[GameScene, Animation](characterSelected.character.selectedAnimation, 200, 300)

  this.addComponent(bigArenaComponent)

  var start = sprite("start.png")
  this.addComponent(new GameComponent[GameScene, Sprite](start, game.getDisplayWidth() - (start.getWidth() + 100), game.getDisplayHeight() - (start.getHeight() + 100)) with Colicionable[GameScene, Sprite] {
    override def update(delta: DeltaState) {
      if (delta.isMouseButtonPressed(MouseButton.LEFT)) {
        if (isClickMe(delta.getCurrentMousePosition())) {
          game.selectArena(characterSelected)
        }
      }
    }
    def getImage() = this.getAppearance().getImage()
    def width = this.getAppearance().getWidth()
    def height = this.getAppearance().getHeight()
  })

  this.addComponent(new MouseComponent(sprite("sword.png"), 0, 0))

  override def selectItem(selectable: Selectable) {
    selectable match {
      case selectedCharacter: SelectableCharacter => {
        characterSelected = selectedCharacter
        bigArenaComponent.setAppearance(characterSelected.character.selectedAnimation)

      }
    }

  }

}