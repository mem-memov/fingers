package net.mem_memov.fingers.view

import scalafx.scene.layout.FlowPane
import scalafx.geometry.Insets

case class TextView(
  pane: FlowPane
):

  def addSymbol(symbolView: SymbolView): TextView =

    TextView(
      symbolView.addToChildren(pane)
    )

object TextView:

  lazy val empty: TextView =

    val flowPane = new FlowPane() {
      padding = Insets(50, 80, 50, 80)
    }

    TextView(flowPane)