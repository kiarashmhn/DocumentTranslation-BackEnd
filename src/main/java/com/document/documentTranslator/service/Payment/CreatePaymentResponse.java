package com.document.documentTranslator.service.Payment;

public class CreatePaymentResponse {
    private String clientSecret;

    public CreatePaymentResponse(String clientSecret) {
        setClientSecret(clientSecret);
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
