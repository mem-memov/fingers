package net.mem_memov.fingers

import net.mem_memov.fingers.model.SymbolModel
import net.mem_memov.fingers.viewModel.TextViewModel
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.FlowPane
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.Text
import scalafx.event.EventHandler
import com.fazecast.jSerialComm.SerialPort

object Gui extends JFXApp3:

  val comPort: SerialPort = SerialPort.getCommPorts()(0)
  comPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY)
  //  comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
  val isOpen: Boolean = comPort.openPort()

  override def start(): Unit = {

    var textViewModel = TextViewModel.empty



    val process = (bits: Array[Boolean]) =>
      val symbolModel = SymbolModel(bits)

      val runnable = new Runnable {
        override def run(): Unit = {
          textViewModel.addSymbol(symbolModel)
        }
      }

      println(symbolModel.toString)
      println()

      Platform.runLater(runnable)

    val listener = MessageListener(process)


    stage = new JFXApp3.PrimaryStage {
      title = "Fingers"
      scene = new Scene {
        fill = Color.Gray
        content = textViewModel.textView.pane
      }
    }

    comPort.addDataListener(listener)
  }

  override def stopApp(): Unit =

    comPort.removeDataListener()
    comPort.closePort()