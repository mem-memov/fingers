package net.mem_memov.fingers

case class Symbol(
  bits: Array[Boolean]
):

  require(bits.length == Symbol.bitLength)

  override def toString: String =

    bits.map(if _ then "x" else ".").mkString("").sliding(6, 6).mkString("\n")

object Symbol:

  private val bitLength = 24