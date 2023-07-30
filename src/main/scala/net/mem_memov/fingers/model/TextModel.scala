package net.mem_memov.fingers.model

case class TextModel(
  symbols: Vector[SymbolModel]
):

  def addSymbol(symbolModel: SymbolModel): TextModel =

    TextModel(
      symbols.appended(symbolModel)
    )

object TextModel:

  lazy val empty: TextModel =

    TextModel(Vector.empty[SymbolModel])

