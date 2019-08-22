package io.github.oxnz.Ingrid.nashorn;

import org.springframework.stereotype.Service;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Service
public class NashornService {

    private final ScriptEngine scriptEngine;

    public NashornService() {
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public <T, R> R execute(String script, Bindings bindings) throws ScriptException {
        return (R) scriptEngine.eval(script, bindings);
    }
}
