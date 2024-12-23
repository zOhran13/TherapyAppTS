package ba.unsa.etf.ts.Therapy.service;

//import ba.unsa.etf.pnwt.proto.LoggingRequest;
//import net.devh.boot.grpc.client.inject.GrpcClient;
import ba.unsa.etf.ts.Therapy.repository.StressReliefActionRepository;
import ba.unsa.etf.ts.Therapy.request.CreateMeditationLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateMeditationLogResponse;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.mapper.CreateMeditationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateMeditationLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;
//    @GrpcClient("grpc")
//    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;

    public ResponseEntity<CreateMeditationLogResponse> handle(CreateMeditationLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        StressReliefAction action = CreateMeditationLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Meditation savedMeditation = (Meditation) stressReliefActionRepository.save(action);
//        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
//                .setServiceName("StressReliefService")
//                .setControllerName("StressReliefActionController")
//                .setActionUrl("/stressrelief/meditation")
//                .setActionType("POST")
//                .setActionResponse("SUCCESS")
//                .build();
//        loggingServiceBlockingStub.logRequest(loggingRequest);
        CreateMeditationLogResponse response = CreateMeditationLogMapper.INSTANCE.entityToResponse(savedMeditation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
