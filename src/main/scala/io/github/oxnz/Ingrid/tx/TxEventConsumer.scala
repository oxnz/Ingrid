package io.github.oxnz.Ingrid.tx

import java.io.{ByteArrayInputStream, IOException, ObjectInputStream}

import io.micrometer.core.instrument.MeterRegistry
import javax.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.{Message, MessageListener}
import org.springframework.stereotype.Service

@Service class TxEventConsumer(val metrics: MeterRegistry, val txService: TxService) extends MessageListener with AutoCloseable {
  final private[tx] val log = LoggerFactory.getLogger(getClass)

  override def onMessage(message: Message, pattern: Array[Byte]): Unit = {
    val bis = new ByteArrayInputStream(message.getBody)
    val in = new ObjectInputStream(bis)
    try {
      val event = in.readObject.asInstanceOf[TxEvent]
      log.debug("msg: {}", event)
      txService.process(event.id)
    } catch {
      case e@(_: IOException | _: ClassNotFoundException | _: TxException) =>
        throw new RuntimeException(e)
    } finally {
      if (bis != null) bis.close()
      if (in != null) in.close()
    }
  }

  @PreDestroy
  override def close(): Unit = {
    log.debug("closing ...")
    txService.close()
    log.debug("closed")
  }
}