package ar.unq.tpi.components
import java.awt.Color
import java.awt.Font

import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

class LabelComponent[SceneType <:GameScene](text:String, font:Font, color:Color, x:Double, y:Double) extends GameComponent[SceneType, Label] (
					new Label(font, color, text), x, y) with ComponentUtils[SceneType, Label]{
  
}

