import com.fazecast.jSerialComm.*
import collection.JavaConverters._

@main def hello: Unit =
  val commPorts = SerialPort.getCommPorts()
  println("COM ports:")
  commPorts.map(commPort => println(commPort.getSystemPortName()))
  print(commPorts.length)
  val commPort = commPorts(0)

  // default connection settings for Arduino
  commPort.setComPortParameters(9600, 8, 1, 0)
  // block until bytes can be read
  commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

  val isOpen = commPort.openPort()

  while(true)
  {
    val b = commPort.getInputStream().read().toByte;
    println("Received data: " + b.toString);
  }


  val isClosed = commPort.closePort();

