package ba.unsa.etf.ts.Therapy;

import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class TherapyApplication implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final DailyReportRepo dailyReportRepository;
	private final MessageRepo messageRepository;
	private final PatientRepo patientRepository;
	private final PsychologistRepo psychologistRepository;
	private final QualityRateRepo qualityRateRepository;
	private final WeeklyReportRepo weeklyReportRepository;
	private final SessionRepo sessionRepository;

	@Autowired
	public TherapyApplication(RoleRepository roleRepository,
							  UserRepository userRepository,
							  DailyReportRepo dailyReportRepository,
							  MessageRepo messageRepository,
							  PatientRepo patientRepository,
							  PsychologistRepo psychologistRepository,
							  QualityRateRepo qualityRateRepository,
							  WeeklyReportRepo weeklyReportRepository,
							  SessionRepo sessionRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.dailyReportRepository = dailyReportRepository;
		this.messageRepository = messageRepository;
		this.patientRepository = patientRepository;
		this.psychologistRepository = psychologistRepository;
		this.qualityRateRepository = qualityRateRepository;
		this.weeklyReportRepository = weeklyReportRepository;
		this.sessionRepository = sessionRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TherapyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeRoles();
		initializeTestData();
	}

	private void initializeRoles() {
		/*if (!roleRepository.existsByName("Administrator")) {
			roleRepository.save(new RoleEntity("Administrator", UUID.randomUUID().toString()));
		}
		if (!roleRepository.existsByName("Psychologist")) {
			roleRepository.save(new RoleEntity("Psychologist", UUID.randomUUID().toString()));
		}
		if (!roleRepository.existsByName("Patient")) {
			roleRepository.save(new RoleEntity("Patient", UUID.randomUUID().toString()));
		}*/
	}

	private void initializeTestData() {
		// Psychologist psychologist = new Psychologist();
		// psychologist.setUserId(UUID.randomUUID().toString());
		// psychologist = psychologistRepository.save(psychologist);

		// Patient patient = new Patient(30, psychologist);
		// patient.setUserId(UUID.randomUUID().toString());
		// patientRepository.save(patient);

		// Message message = new Message("Sample message content", new Date(), patient, psychologist);
		// messageRepository.save(message);

		// QualityRate qualityRate = new QualityRate(psychologist, patient, 5);
		// qualityRateRepository.save(qualityRate);

		// WeeklyReport weeklyReport = new WeeklyReport("Sample weekly report content", psychologist, patient);
		// weeklyReportRepository.save(weeklyReport);

		// DailyReport dailyReport = new DailyReport("Sample content", patient, weeklyReport);
		// dailyReportRepository.save(dailyReport);

		// Session session = new Session(psychologist, patient, "Monday", "09:00");
		// sessionRepository.save(session);
	}
}
