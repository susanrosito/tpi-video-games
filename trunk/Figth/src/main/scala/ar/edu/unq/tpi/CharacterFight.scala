package ar.edu.unq.tpi
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import javax.imageio.ImageIO
import com.uqbar.vainilla.appearances.Animation
import ar.edu.unq.tpi.traits.Event

class CharacterFight(player: Player, var character: Character, var scene: GamePlayScene, x: Int, staticY: Int) extends Actor[GamePlayScene, CharacterFight](null, x, staticY) {

  player.character = this

  var oponent: CharacterFight = null

  var orientation = Orientation.LEFT
  var delta = 500
  var currentMove: Movement = IDLE
  var pego = true

  changeMove(IDLE)

  def changeMove(move: Movement, orientation: Orientation.Orientation) = {
    this.currentMove = move
    this.setAppearance(character.getMove(move).getAnimation(orientation))
  }

  override def changeMove(move: Movement) = {
    changeMove(move, orientation)
  }

  def walk() = {
    var move: Movement = null
    if (orientation.equals(Orientation.LEFT)) {
      move = WALK
    } else {
      move = WALK_BACK
    }
    changeMove(move)
  }

  def receiveAttack(force: Double) {
    character.receiveAttack(force)
    if (this.orientation.equals(Orientation.LEFT)) {
      this.move(force, 0)
    } else {
      this.move(-force, 0)
    }
  }

  def attack(attack: Movement)(delta: DeltaState) {
    if (!isMoving) {
      this.changeMove(attack)
      isMoving = true;
      pego = false
      damageOponent()

    }
  }

  def damageOponent() {
    if (!pego && collidesWith(oponent)) {
      pego = true
      //    if(CollisionDetector.INSTANCE.collidesWith(this, oponent, true)){
      oponent.receiveAttack(character.getMove(currentMove).force)
    }
  }

  override def update(deltaState: DeltaState) = {

    if (isMoving) {
      isMoving = !this.getAppearance().finish()
      damageOponent()
      super.update(deltaState)
      if (!isMoving) {
        this.getAppearance().reset()
        changeMove(IDLE)
      }
    } else {
      if (!player.update(deltaState)) {
        super.update(deltaState)
      }

      if (oponent.getX() > this.getX()) {
        orientation = Orientation.RIGHT
      } else {
        orientation = Orientation.LEFT
      }
    }
    if (currentMove == JUMP) {
      currentMove.update(this, deltaState)
    } else {
      this.setY(baseY)
    }

  }

  def baseY = staticY - this.getAppearance().getHeight() + 120

  def walkLeft(deltaState: DeltaState) {
    val moveX = -delta * deltaState.getDelta()
    this.move(moveX, 0)
    if (orientation.equals(Orientation.LEFT)) {
      this.changeMove(WALK)

    } else {
      this.changeMove(WALK_BACK)
    }
    this.getAppearance().advance()

    isMoving = false;
    if (collidesWith(scene.boundLeft) || collidesWith(oponent)) {
      dispatchEvent(new Event("aa", this, this.orientation))
      this.move(-moveX, 0)
    }
  }

  def walkRight(deltaState: DeltaState) {
    val moveX = delta * deltaState.getDelta()
    this.move(moveX, 0)
    if (orientation.equals(Orientation.LEFT)) {
      this.changeMove(WALK_BACK)
    } else {
      this.changeMove(WALK)
    }
    this.getAppearance().advance()
    isMoving = false;

    if (collidesWith(scene.boundRigth) || collidesWith(oponent)) {
      this.move(-moveX, 0)
      dispatchEvent(new Event("aa", this, this.orientation))
    }

  }

  def walkToOponent(deltaState: DeltaState) {
    if (this.getX() > oponent.getX()) {
      walkLeft(deltaState)
    } else {
      walkRight(deltaState)
    }

  }

  var deltaWidth = 300
  var deltaHeight = 150

  //  override def getX() = super.getX() + 100

}