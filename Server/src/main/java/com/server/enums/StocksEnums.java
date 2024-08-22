package com.server.enums;

public enum StocksEnums {
    AAPL("Apple Inc."),
    MSFT("Microsoft Corporation"),
    AMZN("Amazon.com, Inc."),
    GOOGL("Alphabet Inc. (Class A)"),
    GOOG("Alphabet Inc. (Class C)"),
    META("Facebook, Inc."),
    TSLA("Tesla, Inc."),
    BRK_B("Berkshire Hathaway Inc. (Class B)"),
    JNJ("Johnson & Johnson"),
    V("Visa Inc."),
    WMT("Walmart Inc."),
    PG("Procter & Gamble Co."),
    MA("Mastercard Incorporated"),
    NVDA("NVIDIA Corporation"),
    HD("The Home Depot, Inc."),
    PYPL("PayPal Holdings, Inc."),
    DIS("The Walt Disney Company"),
    ADBE("Adobe Inc."),
    NFLX("Netflix, Inc."),
    CMCSA("Comcast Corporation"),
    PEP("PepsiCo, Inc."),
    CSCO("Cisco Systems, Inc."),
    XOM("Exxon Mobil Corporation"),
    INTC("Intel Corporation"),
    KO("The Coca-Cola Company");

    private final String companyName;

    StocksEnums(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
