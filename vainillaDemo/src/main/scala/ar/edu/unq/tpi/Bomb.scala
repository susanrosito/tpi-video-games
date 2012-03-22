package ar.edu.unq.tpi

import java.awt.image.BufferedImage
import scala.collection.mutable.Buffer
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.sound.StereoSound
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import javax.sound.sampled.AudioSystem
import javax.imageio.ImageIO
import com.uqbar.vainilla.sound.SoundBuilder

class Bomb(animation:Animation, x:Double, y:Double) extends GameComponent[GameScene, Animation](animation, x, y) {
  
  def this(x:Double, y:Double) = {
    this(null, x, y )

    val audio = new SoundBuilder().buildSound(this.getClass().getResourceAsStream("sounds/bomb.wav"))
    val images = ImageIO.read(this.getClass().getResource("img/bob_omb.png"))

    var a = new Animation(0.2, SpriteUtil.spriteS(13.2, 22.5, 8, 0, images)){
      
    	override def advance() ={
    		if(this.getCurrentIndex()+1 >= this.getSprites().length) {
    		  super.advance()
    		  audio.play()
    		  destroy()
    		}else{
    			super.advance()
    		}
    	}
      
    }
    
    this.setAppearance(a)
  }
  

}

object SpriteUtil{
    def spriteS(sWidth: Double, sHeight: Double, row: Int, cols: Int, image: BufferedImage): Array[Sprite] = {
    
    val buffer = Buffer[Sprite]()

    for (i <- 0 to row-1 by 1) {
      buffer.append(new Sprite(image.getSubimage((sWidth*i).toInt, (cols * sHeight).toInt, sWidth.toInt, sHeight.toInt)))
    }
    
    return buffer.toArray

  }
}