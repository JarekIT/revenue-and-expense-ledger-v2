package pl.jarekit.rael;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
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
