package ba.unsa.etf.ts.Therapy.repository;


import ba.unsa.etf.ts.Therapy.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
}
