package pl.jarekit.rael.repo;

import org.springframework.data.repository.CrudRepository;
import pl.jarekit.rael.model.Client;

import java.util.Optional;

public interface ClientRepo extends CrudRepository<Client, Long> {

    @Override
    <S extends Client> S save(S s);

    @Override
    <S extends Client> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<Client> findById(Long aLong);

    @Override
    Iterable<Client> findAll();

    @Override
    Iterable<Client> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Client client);
}
