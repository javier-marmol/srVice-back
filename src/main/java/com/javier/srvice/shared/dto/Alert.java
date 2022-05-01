package com.javier.srvice.shared.dto;

public class Alert {
    private String content;

    public Alert(String mensaje) {
        this.content = mensaje;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
