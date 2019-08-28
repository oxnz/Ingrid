package io.github.oxnz.Ingrid.tx

import java.lang

import io.micrometer.core.annotation.Timed
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RequestMapping}

@Timed
@Controller
@RequestMapping(Array("admin/tx")) class TxAdminController(val txRecordRepo: TxRecordRepo, val txResultRepo: TxResultRepo, val messageProducer: TxEventProducer) {
  final private val log: Logger = LoggerFactory.getLogger(getClass)

  @GetMapping(Array("")) def index: String = "admin/tx/index"

  @PostMapping(Array("")) def post(@RequestBody request: TxRequest): String = {
    val entity: TxResponse = process(request)
    "index"
  }

  @GetMapping(Array("records")) def txRecords(model: Model): String = {
    val records: lang.Iterable[TxRecord] = txRecordRepo.findAll
    model.addAttribute("records", records)
    "admin/tx/records"
  }

  @GetMapping(Array("results")) def txResults(model: Model): String = {
    val results: lang.Iterable[TxResult] = txResultRepo.findAll
    model.addAttribute("results", results)
    "admin/tx/results"
  }

  private def process(request: TxRequest): TxResponse = {
    var record: TxRecord = new TxRecord(request.cat, request.ref, request.state, request.city)
    record = txRecordRepo.save(record)
    val event: TxEvent = new TxEvent(record.id)
    messageProducer.publish(event)
    new TxResponse(record.id)
  }
}
