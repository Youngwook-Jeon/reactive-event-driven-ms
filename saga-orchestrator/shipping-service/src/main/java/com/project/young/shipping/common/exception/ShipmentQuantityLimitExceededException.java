package com.project.young.shipping.common.exception;

public class ShipmentQuantityLimitExceededException extends RuntimeException {

    private static final String MESSAGE = "Shipment quantity exceeded the limit";

    public ShipmentQuantityLimitExceededException() {
        super(MESSAGE);
    }
}
