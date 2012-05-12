package ar.edu.unq.tpi.traits

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

trait EventGameComponent[T <: EventGameComponent[T]] {

  private var listeners = Map[String, Buffer[Function[T,_]]]()

  protected def dispatchEvent[A <: Any](event: Event[T, A]) {
    listeners.keys.foreach(key => {
      if (key.equals(event.name)) {
        listeners(key).foreach(listener => listener.aply(event))
      }
    })
  }

  def addEventListener[A <: Any](eventName: String, listener: (Event[T, A]) => Unit) {
    if (!listeners.contains(eventName)) {
      listeners(eventName) = Buffer()
    }
    listeners(eventName).append(new Function(listener))
  }

  def removeEventListener[A <: Any](eventName: String, listener: (Event[T, A]) => Unit) {
    if (listeners.contains(eventName)) {
      listeners(eventName).-=(new Function(listener))
    }
  }
  
}


private class Function[A, B]( val listener:(Event[A,B]) =>Unit){
  def aply(event:Event[A, _]) = event match{
    case e:Event[A, B] => listener(e)
  }
  
  override def equals(any:Any):Boolean={
    any match{
      case function:Function[A, B] => listener == function.listener
      case _ => false
    }
  }
}