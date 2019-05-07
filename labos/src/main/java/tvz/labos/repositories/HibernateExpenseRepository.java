package tvz.labos.repositories;

import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import tvz.labos.models.Expense;
import tvz.labos.models.Wallet;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;

@Primary
@Repository
@Transactional
public class HibernateExpenseRepository implements ExpenseRepository{
    private final EntityManager em;

    public HibernateExpenseRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Iterable<Expense> findAll() {
        Session session = (Session) em.getDelegate();
        return session.createQuery("select e from Expense e", Expense.class).getResultList();
    }

    @Override
    public Iterable<Expense> findAllByWalletId(Long id) {
        Session session = (Session) em.getDelegate();
        Query query = session.createQuery("select e from Expense e where e.wallet.id = ?1", Expense.class);
        return query.setParameter(1, id).getResultList();
    }

    @Override
    public Expense findOne(Long id) {
        Session session = (Session) em.getDelegate();
        return session.find(Expense.class, id);
    }

    @Override
    public Expense save(Expense expense, Wallet wallet) {
        /* TODO
        Pitati zasto hibernate ne postavi wallet nego se ja moram mucit i manualno postavit.
         */
        expense.setWallet(wallet);
        return em.merge(expense);
    }
    @Override
    public void delete(Expense expense) {
        Session session = (Session) em.getDelegate();
        session.delete(expense);
    }
}
