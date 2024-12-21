//package ba.unsa.etf.ts.Therapy.controllers;
//
//import ba.unsa.etf.ts.Therapy.configuration.GoogleConfiguration;
//import ba.unsa.etf.ts.Therapy.service.GoogleCalendarService;
//import ba.unsa.etf.ts.Therapy.service.StateService;
//import com.google.api.client.auth.oauth2.TokenResponse;
//import com.google.api.services.calendar.CalendarScopes;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/route")
//public class RouteController {
//
//    private final GoogleConfiguration configuration;
//    private final StateService state;
//    private final GoogleCalendarService calendarService;
//
//    @GetMapping("/")
//    public Map<String, Object> index(@RequestParam("psychologistEmail") String psychologistEmail,
//                                     @RequestParam("day") String day,
//                                     @RequestParam("time") String time) {
//        Map<String, Object> model = new HashMap<>();
//        if (state.token.isPresent()) {
//            TokenResponse tokenResponse = state.token.get();
//            DayOfWeek sessionDay = DayOfWeek.valueOf(day.toUpperCase());
//            LocalTime sessionTime = LocalTime.parse(time);
//
//            Optional<String> entriesOpt = calendarService.createEntryy(tokenResponse, sessionDay, sessionTime, psychologistEmail);
//
//            if (entriesOpt.isEmpty()) {
//                model.put("error", "Could not load calendar entries");
//                model.put("viewName", "error");
//            } else {
//                String entries = entriesOpt.get();
//                model.put("entries", entries);
//                model.put("viewName", "index");
//            }
//        } else {
//            String googleUrl = String.format(
//                    "https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&access_type=offline",
//                    configuration.getClientId(),
//                    configuration.getRedirectUri(),
//                    CalendarScopes.CALENDAR);
//            model.put("googleUrl", googleUrl);
//            model.put("viewName", "login");
//        }
//        return model;
//    }
//
//    @GetMapping("/token")
//    public void token(@RequestParam String code) {
//        Optional<TokenResponse> tokenOpt = calendarService.authorize(code);
//        if (tokenOpt.isEmpty()) {
//            throw new IllegalStateException("There is no token available");
//        }
//        TokenResponse token = tokenOpt.get();
//        state.token = Optional.of(token);
//    }
//}
