package net.mem_memov.fingers.viewModel

import net.mem_memov.fingers.model.{SymbolModel, TextModel}
import net.mem_memov.fingers.view.{SymbolView, TextView}

case class TextViewModel(
  textModel: TextModel,
  textView: TextView
):

  def addSymbol(symbolModel: SymbolModel): TextViewModel =

    val symbolView = SymbolView.fromModel(symbolModel)
    val symbolViewModel = SymbolViewModel(symbolModel, symbolView)

    TextViewModel(
      textModel.addSymbol(symbolModel),
      textView.addSymbol(symbolView)
    )
    
object TextViewModel:

  lazy val empty =

    TextViewModel(TextModel.empty, TextView.empty)