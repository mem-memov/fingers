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

  commPort.addDataListener(new SerialPortDataListener() {

   def getListeningEvents(): Int = SerialPort.LISTENING_EVENT_DATA_AVAILABLE

   def serialEvent(event: SerialPortEvent): Unit =
   {
      if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {

        import com.google.common.primitives.Bytes
        var newData = new Array[Byte](commPort.bytesAvailable())
        val numRead = commPort.readBytes(newData, newData.length)
        newData.map(_.toChar).foreach { char =>
          if char.isDigit then
            val byteInt = char.toString.toInt
            val byteBits = Vector(
              ((byteInt >> 0) & 1) == 1,
              ((byteInt >> 1) & 1) == 1,
              ((byteInt >> 2) & 1) == 1,
              ((byteInt >> 3) & 1) == 1,
              ((byteInt >> 4) & 1) == 1,
              ((byteInt >> 5) & 1) == 1,
              ((byteInt >> 6) & 1) == 1,
              ((byteInt >> 7) & 1) == 1,
            )
            val s = byteBits.map(bit => if bit then "1" else "0").mkString("")
            print(s)
            print(" ")
          else
            print(char)
        }
      }
   }
  });

  while(true)
  {
    Thread.sleep(100)
  }


  val isClosed = commPort.closePort();

def toBinaryString(byte: Byte): String =

  val zeroBased = byte.toInt + Byte.MaxValue.toInt + 1
  String.format("%8s", Integer.toBinaryString(zeroBased & 0xFF)).replace(' ', '0')