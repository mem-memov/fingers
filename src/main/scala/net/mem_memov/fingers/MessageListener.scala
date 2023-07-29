package net.mem_memov.fingers

import com.fazecast.jSerialComm.*

class MessageListener extends SerialPortMessageListener:

  override def getMessageDelimiter: Array[Byte] =

    Array(13, 10)

  override def delimiterIndicatesEndOfMessage(): Boolean =

    true

  override def getListeningEvents: Int =

    SerialPort.LISTENING_EVENT_DATA_RECEIVED

  override def serialEvent(event: SerialPortEvent): Unit =

    val bytes = event
      .getReceivedData
      .dropRight(getMessageDelimiter.length) // drop line delimiter
      .map(_.toChar)
      .mkString("")
      .split(" ")
      .map(_.toInt)
      .foreach { byteInt =>
        val byteBits = Vector(
          ((byteInt >> 0) & 1) == 1,
          ((byteInt >> 1) & 1) == 1,
          ((byteInt >> 2) & 1) == 1,
          ((byteInt >> 3) & 1) == 1,
          ((byteInt >> 4) & 1) == 1,
          ((byteInt >> 5) & 1) == 1,
          ((byteInt >> 6) & 1) == 1,
          ((byteInt >> 7) & 1) == 1,
        ).reverse

        val s = byteBits.map(bit => if bit then "1" else "0").mkString("")
        print(s)
        print(" ")
      }

    println()

  def toBinaryString(byte: Byte): String =

    val zeroBased = byte.toInt + Byte.MaxValue.toInt + 1
    String.format("%8s", Integer.toBinaryString(zeroBased & 0xFF)).replace(' ', '0')