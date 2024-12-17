package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {

}
