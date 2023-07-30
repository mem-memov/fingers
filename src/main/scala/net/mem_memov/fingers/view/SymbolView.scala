package net.mem_memov.fingers.view

import net.mem_memov.fingers.model.SymbolModel
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.FlowPane
import scalafx.scene.paint.Color

case class SymbolView(
  canvas: Canvas
):

  def addToChildren(pane: FlowPane): FlowPane =

    pane.children.addOne(canvas)
    pane

object SymbolView:

  def fromModel(symbolModel: SymbolModel): SymbolView =

    val dotWidth = 6
    val dotHeight = 6
    val symbolWidth = dotWidth * 6
    val symbolHeight = dotWidth * 4

    val trueColor = Color.Black
    val falseColor = Color.LightGray

    val canvas = new Canvas(symbolWidth, symbolHeight)

    val context = canvas.getGraphicsContext2D

    context.setFill(falseColor)
    context.fillRect(0, 0, symbolWidth, symbolHeight)

    context.setFill(trueColor)
    val modifiedContext = symbolModel.toSymbolRows.zipWithIndex.foldLeft(context) { case (context, (row, rowIndex)) =>
      row.zipWithIndex.foldLeft(context) { case (context, (cell, cellIndex)) =>
        if cell then
          context.fillRect(
            cellIndex * dotWidth,
            rowIndex * dotHeight,
            dotWidth,
            dotHeight
          )
          context
        else
          context
      }
    }

    SymbolView(canvas)
