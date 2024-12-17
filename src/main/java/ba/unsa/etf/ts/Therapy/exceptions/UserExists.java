package ba.unsa.etf.ts.Therapy.exceptions;

public class UserExists extends IllegalArgumentException{
    public UserExists(String m){
        super(m);
    }
}
