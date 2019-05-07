package tvz.labos.repositories;

import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import tvz.labos.models.Wallet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Primary
@Repository
@Transactional
public class HibernateWalletRepository implements WalletRepository {
    private final EntityManager em;

    public HibernateWalletRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Iterable<Wallet> findAll() {
        Session session = (Session) em.getDelegate();
        return session.createQuery("select w from Wallet w", Wallet.class).getResultList();
    }

    @Override
    public Wallet findOneByOwner(String owner) {
        Wallet wallet = null;
        Session session = (Session) em.getDelegate();
        Query query = session.createQuery("select w from Wallet w where w.owner = :param");
        try {
            wallet = (Wallet) query.setParameter("param", owner).getSingleResult();
            if (wallet instanceof Wallet) {
                return wallet;
            }
            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Wallet findOne(Long id) {
        Session session = (Session) em.getDelegate();
        return session.find(Wallet.class, id);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return em.merge(wallet);
    }

    @Override
    public void delete(Wallet wallet) {
        Session session = (Session) em.getDelegate();
        session.delete(wallet);
    }
}
