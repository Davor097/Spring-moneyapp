package tvz.labos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tvz.labos.models.Wallet;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findFirstById(Long id);
    Wallet findFirstByOwner(String username);


   /* Iterable<Wallet> findAll();
    Wallet findOne(Long id);
    Wallet findOneByOwner(String owner);
    Wallet save(Wallet expense);
    void delete(Wallet wallet);*/
}
