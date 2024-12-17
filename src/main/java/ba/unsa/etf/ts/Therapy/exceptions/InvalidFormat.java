package ba.unsa.etf.ts.Therapy.exceptions;
import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidFormat extends RuntimeException{
    public InvalidFormat(String m){
        super(m);
    }
}
