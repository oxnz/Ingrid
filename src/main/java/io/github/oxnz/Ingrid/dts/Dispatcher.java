package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Dispatcher {

    public List<DestSpec> dispatch(TxRecord record) {
        List<DestSpec> destSpecs = new ArrayList<>();
        destSpecs.add(new WuHanPSBDestSpec());
        return destSpecs;
    }

}
