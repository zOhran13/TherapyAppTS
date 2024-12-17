package ba.unsa.etf.ts.Therapy.exceptions;

public class UserNotFound extends IllegalArgumentException{
    public UserNotFound(String m){
        super(m);
    }
}
