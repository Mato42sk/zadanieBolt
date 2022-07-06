package com.example.userLogin.appRun;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    @Query("select o from Order o where o.id =?1 and o.provider = ?2")
    Optional<Order> findOrders(Long id, String provider);

    @Modifying
    @Transactional
    @Query("delete from Order o where o.provider =?1 and o.id=?2")
    void deleteOrder(String provider, Long id);

    @Query("select max(o.id) from Order o")
    Long max();

    @Query("select count(o.provider) from Order o where o.provider=?1")
    Long countProviderOrders(String provider);

    @Transactional
    @Modifying
    @Query("delete from Order o where o.customer =?1")
    void deleteAllOrdersC(String customer);

    @Transactional
    @Modifying
    @Query("delete from Order o where o.provider =?1")
    void deleteAllOrdersP(String provider);

}
