package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, String> {
}
