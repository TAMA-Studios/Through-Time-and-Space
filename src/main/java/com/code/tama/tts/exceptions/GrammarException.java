package com.code.tama.tts.exceptions;

import java.util.List;

public class GrammarException extends Exception {
    List<String> exceptions;

    public GrammarException(String message) {
        super(message);
    }

    public GrammarException(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public String getMessage() {
        return this.exceptions != null ? this.exceptions.toString() : super.getMessage();
    }
}
