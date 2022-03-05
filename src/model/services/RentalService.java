package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

    private Double pricePerDay;
    private Double pricerPerHour;
    private TaxService taxService;

    public RentalService(Double pricePerDay, Double pricerPerHour, TaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricerPerHour = pricerPerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental) {
        long t1 = carRental.getStart().getTime();
        long t2 = carRental.getFinish().getTime();
        double hours = (double) (t2 - t1) / 1000.0 / 60.0 / 60.0;
        double basicPayment;
        if (hours <= 12.0) {
            basicPayment = Math.ceil(hours) * pricerPerHour;
        } else {
            basicPayment = Math.ceil(hours / 24.0) * pricePerDay;
        }
        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
