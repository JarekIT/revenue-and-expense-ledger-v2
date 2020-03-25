package pl.jarekit.rael.repo;

import org.springframework.data.repository.CrudRepository;
import pl.jarekit.rael.model.Invoice;

public interface InvoiceRepo extends CrudRepository<Invoice, Long> {

}
