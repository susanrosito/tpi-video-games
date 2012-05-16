package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Animation
import scala.collection.mutable.HashMap
import com.uqbar.vainilla.appearances.Sprite
import ar.edu.unq.tpi.resource.TraitResources

class Character(var envelope:CharacterAppearance){

  var death = false

  private var _life = 100D

  def life() = this._life
  
  def getMove(move: Movement) = envelope.getMove(move)

  def receiveAttack(force: Double) {
    _life  -= force
    if(life <= 0){
      this.death = true
    }
  }

}
