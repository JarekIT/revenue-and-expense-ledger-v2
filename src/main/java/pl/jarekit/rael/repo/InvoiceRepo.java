package pl.jarekit.rael.repo;

import org.springframework.data.repository.CrudRepository;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.model.User;

public interface InvoiceRepo extends CrudRepository<Invoice, Long> {

    Iterable<Invoice> findAllByUser(User user);

}
