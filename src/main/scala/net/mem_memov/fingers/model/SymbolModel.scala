package net.mem_memov.fingers.model

case class SymbolModel(
  bits: Array[Boolean]
):

  require(bits.length == SymbolModel.bitLength)

  override def toString: String =

    bits.map(if _ then "x" else ".").mkString("").sliding(6, 6).mkString("\n")

  def toSymbolRows: Array[Array[Boolean]] =

    bits.sliding(6, 6).toArray

object SymbolModel:

  private val bitLength = 24