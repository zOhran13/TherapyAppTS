package ba.unsa.etf.ts.Therapy.repository;


import ba.unsa.etf.ts.Therapy.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    boolean existsByName(String name);

}