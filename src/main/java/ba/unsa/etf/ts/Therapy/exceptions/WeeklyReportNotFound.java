package ba.unsa.etf.ts.Therapy.exceptions;

public class WeeklyReportNotFound extends IllegalArgumentException{
    public WeeklyReportNotFound(String m){
        super(m);
    }
}