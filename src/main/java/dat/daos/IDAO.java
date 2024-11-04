package dat.daos;

import java.util.List;

public interface IDAO<DTO, ID> {

    /**
     * Create a new entity from DTO
     * @param dto The DTO to create entity from
     * @return The created entity as DTO
     */
    DTO create(DTO dto);

    /**
     * Get all entities as DTOs
     * @return List of all entities as DTOs
     */
    List<DTO> getAll();

    /**
     * Get entity by ID as DTO
     * @param id The ID of the entity
     * @return The entity as DTO if found, otherwise null
     */
    DTO getById(ID id);

    /**
     * Update entity from DTO
     * @param dto The DTO with updated values
     * @return The updated entity as DTO
     */
    DTO update(DTO dto);

    /**
     * Delete entity by ID
     * @param id The ID of the entity to delete
     * @return true if deleted, false if not found
     */
    boolean delete(ID id);
}