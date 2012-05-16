package ar.edu.unq.tpi
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.components.Selectable
import ar.edu.unq.tpi.resource.SpriteUtil



class SelectableCharacter(var character:CharacterAppearance) extends Selectable("", character.selectedImage) 

object Arena1 extends Selectable("Fondo1", SpriteUtil.sprite("fondo1.png")) 
object Arena2 extends Selectable("Fondo2", SpriteUtil.sprite("fondo2.jpg")) 
object Arena3 extends Selectable("Fondo3", SpriteUtil.sprite("fondo3.jpg")) 
object Arena4 extends Selectable("Fondo4", SpriteUtil.sprite("fondo4.png"))

object AllArena{
  def allArena = List(Arena1,Arena2, Arena3,Arena4)
}
