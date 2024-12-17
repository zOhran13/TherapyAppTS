package ba.unsa.etf.ts.Therapy.exceptions;



public class DailyReportNotFound extends IllegalArgumentException{
    public DailyReportNotFound(String m){
        super(m);
    }
}