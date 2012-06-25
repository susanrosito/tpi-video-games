package ar.edu.unq.tpi
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.edu.unq.tpi.resource.TraitResources
import scala.xml.NodeSeq
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

abstract class Player(tag:String) extends TraitResources{
  
  val controls = xmlFromFile("controls/controllers.xml")
  var thisControls = controls\ tag
  
  var LEFT = new PlayerKey("LEFT", thisControls)
  var RIGHT = new PlayerKey("RIGHT", thisControls)
  var UP = new PlayerKey("UP", thisControls)
  var DOWN = new PlayerKey("DOWN", thisControls)
  var ENTER = new PlayerKey("ENTER", thisControls)
  
  var K_HIGH_KICK1 = new PlayerKey("K_HIGH_KICK1", thisControls)
  var K_LOW_KICK1 = new PlayerKey("K_LOW_KICK1", thisControls) 
  var K_HIGH_KICK2 = new PlayerKey("K_HIGH_KICK2", thisControls)
  var K_LOW_KICK2 = new PlayerKey("K_LOW_KICK2", thisControls)
  var K_HIGH_PUCH1 = new PlayerKey("K_HIGH_PUNCH1", thisControls)
  var K_LOW_PUNCH1 = new PlayerKey("K_LOW_PUNCH1", thisControls)
  var K_HIGH_PUCH2 = new PlayerKey("K_HIGH_PUNCH2", thisControls)
  var K_LOW_PUNCH2 = new PlayerKey("K_LOW_PUNCH2", thisControls)
  
  val keys = List(LEFT, RIGHT, UP, DOWN, ENTER, K_HIGH_KICK1, K_HIGH_KICK2, K_HIGH_PUCH1, K_HIGH_PUCH2, K_LOW_KICK1, K_LOW_KICK2, K_LOW_PUNCH1, K_LOW_PUNCH2)

  //  var COMBO1 = Array(Key.T)
  //  var COMBO2 = Array(Key.T)
  //  var COMBO3 = Array(Key.T)
  //  var COMBO4 = Array(Key.T)

  var _character: CharacterFight = null

  def character = this._character
  def character_=(anCharacter: CharacterFight) = {
    this._character = anCharacter
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_KICK1.key, new FunctionSceneListener((d)=>character.changeMove(HIGH_KICK1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_KICK2.key, new FunctionSceneListener((d)=>character.changeMove(HIGH_KICK2)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_KICK1.key, new FunctionSceneListener((d)=>character.changeMove(LOW_KICK1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_KICK2.key, new FunctionSceneListener((d)=>character.changeMove(LOW_KICK2)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_PUNCH1.key, new FunctionSceneListener((d)=>character.changeMove(LOW_PUNCH1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_PUCH1.key, new FunctionSceneListener((d)=>character.changeMove(HIGH_PUCH1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_PUNCH2.key, new FunctionSceneListener((d)=>character.changeMove(LOW_PUNCH2)))
  }

  def update(deltaState: DeltaState): Boolean = {
    if (deltaState.isKeyPressed(this.UP.key)) {
      //dispatchEvent(new Event(GameEvents.MOVE_CABEZAL_UP, this, this.orientation))
      character.changeMove(JUMP)
      return false;
    }
    if (deltaState.isKeyBeingHold(this.LEFT.key) && deltaState.isKeyBeingHold(this.RIGHT.key) && deltaState.isKeyBeingHold(this.K_HIGH_PUCH1.key)) {
      character.attack(COMBO1)(deltaState)
    } else if (deltaState.isKeyBeingHold(this.LEFT.key)) {
      character.walkLeft(deltaState)
    } else if (deltaState.isKeyBeingHold(this.RIGHT.key)) {
      character.walkRight(deltaState)
    } else {
      character.changeMove(IDLE)
      return false;
    }

    return true

  }
}

object Player1 extends Player("Player1") {}
object Player2 extends Player("Player2"){}

object PlayerCPU extends Player("Player2") {

  override def update(deltaState: DeltaState): Boolean = {
    if ((Math.random * 100) >= 50) {
      character.changeMove(IDLE)
      return false

    } else {

      if (!character.collidesWith(character.oponent)) {
        //        character.walkToOponent(deltaState)
      } else {
        var buffer = Buffer[Movement]()
        //        buffer.appendAll(Array[Movement](HIGH_KICK1, HIGH_KICK2, HIGH_PUCH1, HIGH_PUCH2, LOW_KICK1, LOW_KICK2, LOW_PUNCH1, LOW_PUNCH2, WALK, WALK_BACK))
        //        character.attack(buffer((Math.random * (buffer.length - 1)).toInt))
      }
      return true;

    }
  }

}


class PlayerKey(var name:String, node:NodeSeq) extends EventGameComponent[PlayerKey]{
  var key = Key.fromCode((node\name).text)
  
  def setKey(k: Key) = {
    key = k
    dispatchEvent(new Event("key", this, k))
  }
  
  def save(node:NodeSeq, futureKey:Key){
    key = futureKey
//    node.foreach(a )
//    node.dropWhile(a => a.length>0) 
  }
}