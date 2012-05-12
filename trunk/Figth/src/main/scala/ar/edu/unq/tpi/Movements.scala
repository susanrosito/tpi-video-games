package ar.edu.unq.tpi
import com.uqbar.vainilla.DeltaState

//object Movements extends Enumeration {
// type Movements = Value
//  var IDLE, WALK, WALK_BACK, HIGH_KICK1, LOW_KICK1, HIGH_KICK2, LOW_KICK2, HIGH_PUCH1, LOW_PUNCH1, HIGH_PUCH2, LOW_PUNCH2, JUMP = Value //COMBO1, COMBO2, COMBO3, COMBO4 = Value
//
//}

abstract class Movement {
  def update(character:CharacterFight, deltaState:DeltaState)
}

object SELECTED extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object IDLE extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object WALK extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object WALK_BACK extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object HIGH_KICK1 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object LOW_KICK1 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object HIGH_KICK2 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}
object HIGH_PUCH1 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object HIGH_PUCH2 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object LOW_PUNCH2 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object LOW_PUNCH1 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object LOW_KICK2 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object JUMP extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    if(character.getY() > character.baseY - (character.getAppearance().getHeight())){
      character.isMoving = true
      character.move(0, -30)
    }else{
      if(character.getY() < character.baseY ){
        character.isMoving = true
        character.move(0, 30)
      }else{
        character.getAppearance().advance()
        character.isMoving = false
      }
    }
  }
}

object COMBO1 extends Movement{
  def update(character:CharacterFight, deltaState:DeltaState){
    
  }
}

object Orientation extends Enumeration {
 type Orientation = Value
  var LEFT, RIGHT = Value

}