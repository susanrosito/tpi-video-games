package ar.edu.unq.tpi
import java.io.PrintWriter
import scala.io.Source
import java.io.File

class RichFile( file: File ) {

  def text = Source.fromFile( file ).mkString

  def text_=( s: String ) {
    val out = new PrintWriter( file )
    try{ out.print( s ) }
    finally{ out.close }
  }
}

object RichFile {

  implicit def enrichFile( file: File ) = new RichFile( file )

}