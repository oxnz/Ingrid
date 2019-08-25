package io.github.oxnz.Ingrid.dts;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dts")
public class TxController {

    @RequestMapping("")
    @Timed(value = "index")
    public String index() {
        return "dts index";
    }
}
