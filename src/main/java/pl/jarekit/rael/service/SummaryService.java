package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.model.Invoice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class SummaryService {

    private InvoiceService invoiceService;

    @Autowired
    public SummaryService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    private Map<Integer, BigDecimal> revenues = new HashMap<>();
    private Map<Integer, BigDecimal> expenses = new HashMap<>();
    private Map<Integer, BigDecimal> profits = new HashMap<>();
    private Map<Integer, BigDecimal> taxes = new HashMap<>();
    private Map<Integer, BigDecimal> netProfits = new HashMap<>();


    public List<Map<Integer, BigDecimal>> putZerosInSummaryEmptyFields() {

        List<Map<Integer, BigDecimal>> zeroSummary = new ArrayList<>();

        bigDecimalInitializationToZeros(revenues);
        bigDecimalInitializationToZeros(expenses);
        bigDecimalInitializationToZeros(profits);
        bigDecimalInitializationToZeros(taxes);
        bigDecimalInitializationToZeros(netProfits);

        zeroSummary.add(revenues);
        zeroSummary.add(expenses);
        zeroSummary.add(profits);
        zeroSummary.add(taxes);
        zeroSummary.add(netProfits);

        return zeroSummary;
    }


    public List<Map<Integer, BigDecimal>> summarizeTaxYear(int year, double taxRate) {

        List<Map<Integer, BigDecimal>> summary = new ArrayList<>();

        List<Invoice> invoices = getInvoicesToList();

        bigDecimalInitializationToZeros(revenues);
        bigDecimalInitializationToZeros(expenses);
        bigDecimalInitializationToZeros(profits);
        bigDecimalInitializationToZeros(taxes);
        bigDecimalInitializationToZeros(netProfits);

        filterInvoicesToSummarizeRevenueAndExpensesInYear(year, invoices);
        calculateProfits();
        calculateTaxes(taxRate);
        calculateNetProfits();

        summary.add(revenues);
        summary.add(expenses);
        summary.add(profits);
        summary.add(taxes);
        summary.add(netProfits);

        return summary;
    }


    private List<Invoice> getInvoicesToList() {
        List<Invoice> invoices = new ArrayList<>();
        invoiceService.getInvoices().forEach(invoices::add);
        return invoices;
    }

    private void filterInvoicesToSummarizeRevenueAndExpensesInYear(int year, List<Invoice> invoices) {
        invoices.stream()
                .filter(invoice -> invoice.getPeriod().getYear() == year)
                .forEach(invoice -> {
                    int month = invoice.getPeriod().getMonthValue();

                    if (invoice.getCategory() == 7 || invoice.getCategory() == 8) {
                        BigDecimal sum = revenues.get(month);
                        sum = sum.add(invoice.getAmount());
                        revenues.put(month, sum);
                    } else if (invoice.getCategory() == 12 || invoice.getCategory() == 13) {
                        expenses.put(month, (expenses.get(month).add(invoice.getAmount())));
                    }
                });
    }

    private void calculateProfits() {
        IntStream.rangeClosed(1, 12)
                .forEach(i -> profits.put(i, profits.get(i).add(revenues.get(i)).subtract(expenses.get(i))));
    }

    private void calculateTaxes(double taxRate) {
        BigDecimal taxPercent = BigDecimal.valueOf(taxRate / 100);

        IntStream.rangeClosed(1, 12)
                .forEach(i -> taxes.put(i,
                        profits.get(i)
                        .multiply(taxPercent)
                        .round(new MathContext(0, RoundingMode.HALF_UP))));
    }

    private void calculateNetProfits() {
        IntStream.rangeClosed(1, 12)
                .forEach(i -> netProfits.put(i, profits.get(i).subtract(taxes.get(i))));
    }

    private void bigDecimalInitializationToZeros(Map<Integer, BigDecimal> map) {
        IntStream.rangeClosed(1, 12)
                .forEach(j -> map.put(j, BigDecimal.ZERO));
    }

}
