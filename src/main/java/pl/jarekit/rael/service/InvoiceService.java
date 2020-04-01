package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.repo.InvoiceRepo;

import java.util.Optional;

@Service
public class InvoiceService {
    
    private InvoiceRepo invoiceRepo;
    private LoginService loginService;

    @Autowired
    public InvoiceService(InvoiceRepo invoiceRepo, LoginService loginService) {
        this.invoiceRepo = invoiceRepo;
        this.loginService = loginService;
    }

    public Invoice saveInvoice(Invoice invoice){
        putClientPartnerInInvoiceByType(invoice);
        invoice.setUser(loginService.getUser());
        LogUtils.saveLogStatic("Added invoice: " + invoice , Level.INFO);
        return invoiceRepo.save(invoice);
    }



    public Iterable<Invoice> saveInvoices(Iterable<Invoice>  invoices){
        LogUtils.saveLogStatic("Added list of invoices: " + invoices , Level.INFO);
        return invoiceRepo.saveAll(invoices);
    }

    public Iterable<Invoice> getInvoices(){
        LogUtils.saveLogStatic("Loaded all invoices" , Level.INFO);
        return invoiceRepo.findAllByUser(loginService.getUser());
    }

    public Invoice getInvoiceById(long id) {
        Optional<Invoice> loadedInvoice = invoiceRepo.findById(id);
        if (loadedInvoice.isPresent()) {
            LogUtils.saveLogStatic("Loaded invoice: " + loadedInvoice, Level.INFO);
            return loadedInvoice.get();
        } else {
            LogUtils.saveLogStatic("Failed Loaded all by id: " + id, Level.WARNING);
            return loadedInvoice.orElse(null);
        }
    }

    public Invoice updateInvoice(Invoice invoice){

        if (invoiceRepo.existsById(invoice.getId())) {
            Invoice invoiceToEdit = invoiceRepo.findById((invoice.getId())).get();
            String log = "Edited invoice: from: " + invoiceToEdit;

            invoiceToEdit.setAmount(invoice.getAmount());
            invoiceToEdit.setAmountType(invoice.getAmountType());
            // todo set the rest fields

            log = log + ", to: " + invoiceToEdit;
            LogUtils.saveLogStatic(log, Level.INFO);
            return invoiceRepo.save(invoiceToEdit);
        } else{
            LogUtils.saveLogStatic( "Failed: Edited invoice, can't find invoice id: " + invoice.getId() +
                    ", so created new Invoice: " + invoice, Level.WARNING);
            return invoiceRepo.save(invoice);
        }
    }


    public String deleteInvoice(long id){
        Optional<Invoice> invoiceToDelete = invoiceRepo.findById(id);
        if (invoiceToDelete.isPresent()){
            invoiceRepo.deleteById(id);
            LogUtils.saveLogStatic("Deleted invoice: " + invoiceToDelete, Level.INFO);
            return "Deleted invoice: " + invoiceToDelete;
        } else {
            LogUtils.saveLogStatic("Failed Deleted invoice by id: " + id, Level.WARNING);
            return "Failed Deleted invoice by id: " + id;
        }
    }

    public boolean existById(long id){
        return invoiceRepo.existsById(id);
    }

    private void putClientPartnerInInvoiceByType(Invoice invoice) {
        switch (invoice.getType()) {
            case "FK":
                invoice.setClientPartner(invoice.getClientSeller());
                invoice.setClientBuyer(loginService.getUser().getClientUser());
                break;
            case "FS":
                invoice.setClientPartner(invoice.getClientBuyer());
                invoice.setClientSeller(loginService.getUser().getClientUser());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + invoice.getType());
        }
    }

    public Iterable<Invoice> getInvoicesForAdmin(){
        LogUtils.saveLogStatic("Loaded all invoices for admin panel" , Level.WARNING);
        return invoiceRepo.findAll();
    }
}
