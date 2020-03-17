package pl.jarekit.rael.repo;

import org.springframework.data.repository.CrudRepository;
import pl.jarekit.rael.model.Address;

import java.util.Optional;

public interface AddressRepo extends CrudRepository<Address, Long> {

    @Override
    <S extends Address> S save(S s);

    @Override
    <S extends Address> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<Address> findById(Long aLong);

    @Override
    Iterable<Address> findAll();

    @Override
    Iterable<Address> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Address address);
}
