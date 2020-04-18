package com.ddoko.service;

import com.ddoko.domain.Publisher;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Publisher}.
 */
public interface PublisherService {

    /**
     * Save a publisher.
     *
     * @param publisher the entity to save.
     * @return the persisted entity.
     */
    Publisher save(Publisher publisher);

    /**
     * Get all the publishers.
     *
     * @return the list of entities.
     */
    List<Publisher> findAll();

    /**
     * Get the "id" publisher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Publisher> findOne(Long id);

    /**
     * Delete the "id" publisher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
