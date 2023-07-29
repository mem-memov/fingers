package net.mem_memov.fingers

import com.fazecast.jSerialComm.*

case class MessageListener(
  process: Array[Boolean] => Unit
) extends SerialPortMessageListener:

  override def getMessageDelimiter: Array[Byte] =

    Array(13, 10)

  override def delimiterIndicatesEndOfMessage(): Boolean =

    true

  override def getListeningEvents: Int =

    SerialPort.LISTENING_EVENT_DATA_RECEIVED

  override def serialEvent(event: SerialPortEvent): Unit =

    val bits = event
      .getReceivedData
      .dropRight(getMessageDelimiter.length) // drop line delimiter
      .map(_.toChar)
      .mkString("")
      .split(" ")
      .map(_.toInt).flatMap { byteInt =>
        Array(
          ((byteInt >> 0) & 1) == 1,
          ((byteInt >> 1) & 1) == 1,
          ((byteInt >> 2) & 1) == 1,
          ((byteInt >> 3) & 1) == 1,
          ((byteInt >> 4) & 1) == 1,
          ((byteInt >> 5) & 1) == 1,
          ((byteInt >> 6) & 1) == 1,
          ((byteInt >> 7) & 1) == 1,
        ).reverse
      }.take(24)

    process(bits)