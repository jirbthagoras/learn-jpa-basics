package jirbthagoras.jpa.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jirbthagoras.jpa.core.entity.*;
import jirbthagoras.jpa.core.enums.CustomerType;
import jirbthagoras.jpa.core.utils.JpaUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

@Slf4j
public class JPATest {

    private EntityManager entityManager;

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        Assertions.assertNotNull(entityManagerFactory);
    }

    @Test
    void testTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            transaction.commit();
        } catch (Throwable throwable) {
            transaction.rollback();
        }
    }

    @Test
    void testInsert() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Jabriel");
        customer.setPrimaryEmail("hansjabriel@gmail.com");

        entityManager.persist(customer);

        transaction.commit();
    }

    @Test
    void testFind() {
        Customer customer = entityManager.find(Customer.class, "1");

        Assertions.assertEquals("1", customer.getId());
        Assertions.assertEquals("Jabriel", customer.getName());
    }

    @Test
    void testMerge() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");

        customer.setName("Banon ");

        entityManager.merge(customer);

        transaction.commit();
    }

    @Test
    void testGeneratedValue() {

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Category category = new Category();
        category.setName("Erlangga");

        entityManager.persist(category);

        transaction.commit();

        Assertions.assertNotNull(category.getId());
    }

    @Test
    void testInsertEnum() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setName("Raihan");
        customer.setId("4");
        customer.setType(CustomerType.PREMIUM);
        entityManager.persist(customer);

        transaction.commit();
    }

    @Test
    void testInsertDatetime() {
        Category category = new Category();
        category.setName("Ihsan");
        category.setCreatedAt(Calendar.getInstance());

        TransactionProcess(() -> {
            entityManager.persist(category);
        });
    }

    @Test
    void testInsertEmbeddedObject() {
        Member member = new Member();
        member.setEmail("hansjabriel@gmail.com");

        Name name = new Name();
        name.setTitle("Title Test");
        name.setFirstName("Jabriel First name");
        member.setName(name);

        TransactionProcess(() -> {
            entityManager.persist(member);
        });
    }

    @Test
    void testCollectionTable() {
        Name name = new Name();
        name.setTitle("Mr. ");
        name.setFirstName("Erlangga");

        Member member = new Member();
        member.setEmail("tresnamada408@gmail.com");
        member.setName(name);

        member.setHobbies(new ArrayList<>());
        member.getHobbies().add("Coding");
        member.getHobbies().add("Ngegay");

        TransactionProcess(() -> {
            entityManager.persist(member);
        });
    }

    @Test
    void testMapCollectionTable() {
        Name name = new Name();
        name.setTitle("Mr. ");
        name.setFirstName("Banon");

        Member member = new Member();
        member.setEmail("banonkenta@gmail.com");
        member.setName(name);

        member.setSkills(new HashMap<>());
        member.getSkills().put("Java", 40);
        member.getSkills().put("Golang", 80);

        TransactionProcess(() -> {
            entityManager.persist(member);
        });
    }

    @Test
    void testEntityListener() {
        Category category = new Category();
        category.setName("Contoh Entity Listener");

        TransactionProcess(() -> {
            entityManager.persist(category);
        });

        Assertions.assertNotNull(category.getUpdatedAt());
    }

    @Test
    void testOneToOnePrimaryKey() {
        Credential credential = new Credential();
        credential.setId(1);
        credential.setEmail("hansjabriel@gmail.com");
        credential.setPassword("rahasia");

        User user = new User();
        user.setId(1);
        user.setName("Jabriel Hans");

        TransactionProcess(() -> {

            entityManager.persist(user);
            entityManager.persist(credential);

        });
    }

    @Test
    void testSaveOneToOne() {
        TransactionProcess(() -> {
            User user = entityManager.find(User.class, 1);

            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(100_000L);
            entityManager.persist(wallet);
        });
    }

    @Test
    void testGetOneToOneRelation() {
        TransactionProcess(() -> {
            User user = entityManager.find(User.class, 1);

            Assertions.assertNotNull(user.getWallet());
            Assertions.assertEquals(100_000L, user.getWallet().getBalance());
        });
    }

    @Test
    void testGetOneToOnePrimaryKey() {
        TransactionProcess(() -> {
            User foundUser = entityManager.find(User.class, 1);

            Assertions.assertEquals("hansjabriel@gmail.com", foundUser.getCredential().getEmail());
            Assertions.assertEquals("rahasia", foundUser.getCredential().getPassword());
        });
    }

    void TransactionProcess(Runnable run) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            run.run();

            log.info("Transaction Commited");
            transaction.commit();
        } catch (Throwable e) {
            log.info("Transaction rollbacked {}", e.getMessage());
            transaction.rollback();
        }
    }

}
