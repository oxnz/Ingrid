package io.github.oxnz.Ingrid.tx

import java.util

import io.github.oxnz.Ingrid.tx.mq.RedisMessageProducer

import scala.jdk.CollectionConverters._
import io.micrometer.core.annotation.Timed
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("tx")) class TxController(val txRecordRepo: TxRecordRepo, val txResultRepo: TxResultRepo, val messageProducer: RedisMessageProducer) {
  final private val log: Logger = LoggerFactory.getLogger(getClass)

  @GetMapping(Array("records"))
  @Timed def records: ResponseEntity[List[TxRecord]] = {
    val entities: List[TxRecord] = txRecordRepo.findAll.asScala.toList
    new ResponseEntity[List[TxRecord]](entities, HttpStatus.OK)
  }


  @GetMapping(Array("results"))
  @Timed def txResults: ResponseEntity[List[TxResult]] = {
    val entities: List[TxResult] = txResultRepo.findAll.asScala.toList
    new ResponseEntity[List[TxResult]](entities, HttpStatus.OK)
  }

  @PostMapping(Array("record"))
  @Timed(value = "post") def post(@RequestBody request: TxRequest): ResponseEntity[TxResponse] = {
    val entity: TxResponse = process(request)
    new ResponseEntity[TxResponse](entity, HttpStatus.OK)
  }

  private def process(request: TxRequest): TxResponse = {
    var record: TxRecord = new TxRecord(request.cat, request.ref, request.state, request.city)
    record = txRecordRepo.save(record)
    val event: TxEvent = new TxEvent(record.id)
    messageProducer.publish(event)
    new TxResponse(record.id)
  }
}

