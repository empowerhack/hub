package io.empowerhack.hub.controller.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public final class VersionAdvice {

    @Value("${version}")
    private String version;

    @ModelAttribute("version")
    public String getVersion() {
        return version;
    }
}
