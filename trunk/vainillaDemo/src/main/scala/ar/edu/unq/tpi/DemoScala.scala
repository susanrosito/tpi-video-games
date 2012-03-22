package ar.edu.unq.tpi

import java.awt.image.BufferedImage

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.util.Random
import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.FilledArc
import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.colissions.CollisionDetector
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import javax.imageio.ImageIO
import com.uqbar.vainilla.appearances.Appearance

class DemoScala extends Game {

  protected def initializeResources(): Unit = {}

  protected def setUpScenes(): Unit = {
    val random = new Random(100);
    var components: Buffer[GameComponent[GameScene, FilledArc]] = Buffer[GameComponent[GameScene, FilledArc]]()
    var i = 0
    while (i < 50) {
      components.append(new GameComponent[GameScene, FilledArc](new FilledArc(Color.BLACK, 20, 10, 10), random.nextInt(200), random.nextInt(200)))
      i += 1;
    }

    val backGround = new GameComponent[GameScene, Rectangle](new Rectangle(Color.LIGHT_GRAY, 1200, 800), 0, 0)
    val piso = new GameComponent[GameScene, Appearance](new Rectangle(Color.BLACK, 1200, 200), 0, 600)

    val top = new GameComponent[GameScene, Appearance](new Rectangle(Color.BLACK, 1200, 100), 0, 0)

    val header = new GameComponent[GameScene, Label](new Label(new Font("Verdana", Font.BOLD, 50), Color.RED, ""), 350, 20)

    backGround.setZ(-100)
    

    this.getCurrentScene().addComponents(backGround)
    this.getCurrentScene().addComponents(top)
    this.getCurrentScene().addComponents(piso)
    this.getCurrentScene().addComponents(header)

    this.getCurrentScene().addComponents(components.toList)
    var bufferN = Buffer[GameComponent[GameScene, Appearance]]()
    bufferN.append(top)

    var bufferS = Buffer[GameComponent[GameScene, Appearance]]()
    bufferS.append(piso)
    val avatar = new Avatar(this.getCurrentScene(), 200, 200, bufferN, bufferS){
      
      override def move(dx:Double, dy:Double) {
        super.move(dx, dy)
        	header.getAppearance().setText("X: " + this.getX() + " Y: " + this.getY())
      }
    }

    this.getCurrentScene().addComponent(avatar)

    new Thread() {
      override def run() = {
        val random = new Random(2);
        while (true) {
          components.foreach(p => {
        	  p.move(random.nextInt(2), random.nextInt(2))
        	  if(CollisionDetector.INSTANCE.collidesRectAgainstRect(p, avatar)){
        	    p.getAppearance().setColor(Color.RED)
        	  }else{
        	    p.getAppearance().setColor(Color.BLACK)
        	  }
          })
          Thread.sleep(100)
        }
      }

    }.start()

  }
  //32x48


  def getDisplaySize(): Dimension = new Dimension(1200, 800)

  def getTitle(): String = "Demo"

}

object MainDemo {

  def main(args: Array[String]) {
    new DesktopGameLauncher(new DemoScala()).launch();
  }
}


