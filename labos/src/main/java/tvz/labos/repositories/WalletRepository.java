package tvz.labos.repositories;

import tvz.labos.models.Wallet;

public interface WalletRepository {
    Iterable<Wallet> findAll();
    Wallet findOne(Long id);
    Wallet findOneByOwner(String owner);
    Wallet save(Wallet expense);
}
