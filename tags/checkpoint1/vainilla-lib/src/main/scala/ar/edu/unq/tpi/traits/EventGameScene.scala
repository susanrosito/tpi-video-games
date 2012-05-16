package ar.edu.unq.tpi.traits
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import com.uqbar.vainilla.GameComponent

trait EventGameScene extends GameScene {

  val keyPressetListeners = Map[Key, Map[Any, Buffer[(DeltaState) => Unit]]]()
  val keyReleasedListeners = Map[Key, Map[Any, Buffer[(DeltaState) => Unit]]]()
  val keyBeingHoldListeners = Map[Key, Map[Any, Buffer[(DeltaState) => Unit]]]()

  val mousePressetListeners = Map[MouseButton, Map[Any, Buffer[(DeltaState) => Unit]]]()
  val mouseReleasedListeners = Map[MouseButton, Map[Any, Buffer[(DeltaState) => Unit]]]()
  val mouseBeingHoldListeners = Map[MouseButton, Map[Any, Buffer[(DeltaState) => Unit]]]()

  override def updateComponent(component: GameComponent[_, _], deltaState: DeltaState) {
    processListener(component, keyPressetListeners, deltaState, deltaState.isKeyPressed)
    processListener(component, keyReleasedListeners, deltaState, deltaState.isKeyReleased)
    processListener(component, keyBeingHoldListeners, deltaState, deltaState.isKeyBeingHold)

    processListener(component, mousePressetListeners, deltaState, deltaState.isMouseButtonPressed)
    processListener(component, mouseReleasedListeners, deltaState, deltaState.isMouseButtonReleased)
    processListener(component, mouseBeingHoldListeners, deltaState, deltaState.isMouseButtonBeingHold)
    super.updateComponent(component, deltaState)
  }

  def addKeyPressetListener(component: Any, key: Key, listener: (DeltaState) => Unit) = addListener(component, keyPressetListeners, key, listener)
  def addKeyReleasedListener(component: Any, key: Key, listener: (DeltaState) => Unit) = addListener(component, keyReleasedListeners, key, listener)
  def addKeyBeingHoldListener(component: Any, key: Key, listener: (DeltaState) => Unit) = addListener(component, keyBeingHoldListeners, key, listener)

  def addMousePressetListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = addListener(component, mousePressetListeners, button, listener)
  def addMouseReleasedListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = addListener(component, mouseReleasedListeners, button, listener)
  def addMouseBeingHoldListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = addListener(component, mouseBeingHoldListeners, button, listener)

  def removeKeyPressetListener(component: Any, key: Key, listener: (DeltaState) => Unit) = removeListener(component, keyPressetListeners, key, listener)
  def removeKeyReleasedListener(component: Any, key: Key, listener: (DeltaState) => Unit) = removeListener(component, keyReleasedListeners, key, listener)
  def removeKeyBeingHoldListener(component: Any, key: Key, listener: (DeltaState) => Unit) = removeListener(component, keyBeingHoldListeners, key, listener)

  def removeMousePressetListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = removeListener(component, mousePressetListeners, button, listener)
  def removeMouseReleasedListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = removeListener(component, mouseReleasedListeners, button, listener)
  def removeMouseBeingHoldListener(component: Any, button: MouseButton, listener: (DeltaState) => Unit) = removeListener(component, mouseBeingHoldListeners, button, listener)

  protected def processListener[A](component: Any, listeners: Map[A, Map[Any, Buffer[(DeltaState) => Unit]]], deltaState: DeltaState, method: (A) => Boolean) {
    listeners.keys.foreach(key => {
      if (method(key)) {
        if (listeners(key).contains(component)) {
          listeners(key)(component).foreach(
              listener => listener(deltaState))
        }
      }
    })
  }

  protected def addListener[E](component: Any, map: Map[E, Map[Any, Buffer[(DeltaState) => Unit]]], e: E, listener: (DeltaState) => Unit) {
    if (!map.contains(e)) {
      map(e) = Map()
    }

    if (!map(e).contains(component)) {
      map(e)(component) = Buffer[(DeltaState) => Unit]()
    }
    map(e)(component).append(listener)
  }

  protected def removeListener[E](component: Any, map: Map[E, Map[Any, Buffer[(DeltaState) => Unit]]], e: E, listener: (DeltaState) => Unit) {
    if (map.contains(e) && map(e).contains(component)) {
		map(e)(component).clear()
    }

  }

}