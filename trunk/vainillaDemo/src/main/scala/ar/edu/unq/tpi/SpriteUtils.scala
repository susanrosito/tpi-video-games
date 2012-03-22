package ar.edu.unq.tpi
import java.awt.image.BufferedImage
import com.uqbar.vainilla.appearances.Sprite
import scala.collection.mutable.Buffer
import javax.imageio.ImageIO

object SpriteUtils {
  
  def spriteS(sWidth: Int, sHeight: Int, row: Int, cols: Int, image: BufferedImage): Array[Sprite] = {

    val buffer = Buffer[Sprite]()

    for (i <- 0 to row - 1 by 1) {
      buffer.append(new Sprite(image.getSubimage(sWidth * i, cols * sHeight, sWidth, sHeight)))
    }

    return buffer.toArray

  }
  
  def sprite(name: String): Sprite = new Sprite(ImageIO.read(this.getClass().getResource("img/" + name)))

}