package ar.unq.tpi.components
import com.uqbar.vainilla.GameScene

abstract class SelectScene() extends GameScene() {
  def selectItem(arena: Selectable) {}
}