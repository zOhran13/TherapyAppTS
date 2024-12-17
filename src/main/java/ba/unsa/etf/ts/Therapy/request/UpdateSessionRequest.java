package ba.unsa.etf.ts.Therapy.request;

public class UpdateSessionRequest {
    private String newDay;
    private String newTime;

    public UpdateSessionRequest(String friday, String time) {
        newDay=friday;
        newTime=time;
    }

    public String getNewDay() {
        return newDay;
    }

    public String getNewTime() {
        return newTime;
    }
}
