package ba.unsa.etf.ts.Therapy.exceptions;

public class SessionNotFound extends IllegalArgumentException{
    public SessionNotFound(String m){
        super(m);
    }
}
