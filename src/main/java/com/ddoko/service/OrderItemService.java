package com.ddoko.service;

import com.ddoko.domain.OrderItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderItem}.
 */
public interface OrderItemService {

    /**
     * Save a orderItem.
     *
     * @param orderItem the entity to save.
     * @return the persisted entity.
     */
    OrderItem save(OrderItem orderItem);

    /**
     * Get all the orderItems.
     *
     * @return the list of entities.
     */
    List<OrderItem> findAll();

    /**
     * Get the "id" orderItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderItem> findOne(Long id);

    /**
     * Delete the "id" orderItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
