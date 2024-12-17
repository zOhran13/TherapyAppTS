package ba.unsa.etf.ts.Therapy.repository;


import ba.unsa.etf.ts.Therapy.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
