package net.mademocratie.gae.server.json.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonServiceResponse {
    public enum ResponseStatus {
        OK, FAILED
    }
    ResponseStatus status;

    String message;

    String context;

    public JsonServiceResponse() {
    }

    public JsonServiceResponse(String message, ResponseStatus status) {
        this.message = message;
        this.status = status;
    }

    public JsonServiceResponse(String context, String message, ResponseStatus status) {
        this.context = context;
        this.message = message;
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
