import com.fazecast.jSerialComm.*
import net.mem_memov.fingers.MessageListener

import collection.JavaConverters.*

@main def hello: Unit =


  val comPort = SerialPort.getCommPorts()(0)
  comPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY)
//  comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
  val isOpen = comPort.openPort()
  val listener = new MessageListener
  comPort.addDataListener(listener)

  try
    while (true)
      Thread.sleep(1000)
  catch
    case exception =>
      exception.printStackTrace
  finally
    comPort.removeDataListener()
    comPort.closePort()

