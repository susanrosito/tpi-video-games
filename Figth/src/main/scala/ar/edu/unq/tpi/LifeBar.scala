package ar.edu.unq.tpi
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameScene

class LifeBar(barSprite:Sprite, x:Double, y:Double, lifeFunction:()=>Double) extends GameComponent[GameScene, Sprite](barSprite.copy(), x, y) {
  
  override def update(deltaState: DeltaState) = {
    var life = lifeFunction()
    if(life >0){
    	this.setAppearance(barSprite.scale(life/100, 1))
    }else{
      this.setAppearance(barSprite.scale(0.01, 1))
    }
  }
}
