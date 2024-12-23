package ba.unsa.etf.ts.Therapy.service;

//import ba.unsa.etf.pnwt.proto.LoggingRequest;


import ba.unsa.etf.ts.Therapy.dto.PatientDto;
import ba.unsa.etf.ts.Therapy.dto.PsychologistDto;
import ba.unsa.etf.ts.Therapy.dto.UserDto;
import ba.unsa.etf.ts.Therapy.exceptions.UserAlreadyExistsException;
import ba.unsa.etf.ts.Therapy.exceptions.InvalidFormatException;
import ba.unsa.etf.ts.Therapy.exceptions.UserNotFoundException;
import ba.unsa.etf.ts.Therapy.mapper.UserMapper;
import ba.unsa.etf.ts.Therapy.repository.PatientRepo;
import ba.unsa.etf.ts.Therapy.repository.RoleRepository;
import ba.unsa.etf.ts.Therapy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ba.unsa.etf.ts.Therapy.models.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientService patientService;
    private final PsychologistService psychologistService;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
//    @GrpcClient("logging")
//    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;
    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, PatientService patientService, PsychologistService psychologistService, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
        this.psychologistService = psychologistService;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createPatient(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email '" + email + "' already exists.");
        }

        String password = userDto.getPassword();
        if (!isPasswordValid(password)) {
            throw new InvalidFormatException("Invalid password. Password must meet policy requirements.");
        }

        if (!isEmailValid(email)) {
            throw new InvalidFormatException("Invalid email format.");
        }

        var role = roleRepository.findById(userDto.getRoleId());
        if (role.isEmpty()) {
            throw new InvalidFormatException("Role does not exist");
        }

        UserEntity user = userMapper.mapToUser(userDto);
        user.setPassword(password);
        user.setRole(role.get());

        UserEntity savedUser = userRepository.save(user);
        PatientDto patientDto = new PatientDto();
        patientDto.setUserId(savedUser.getUserId());
        patientDto.setSelectedPsychologistId(null);
        patientDto.setAge(20);
        patientService.savePatient(patientDto);
        return userMapper.mapToUserDto(savedUser);
    }



    @Override
    public  UserDto createPsychologist(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email '" + email + "' already exists.");
        }


        String password = userDto.getPassword();
        if (!isPasswordValid(password)) {
            throw new InvalidFormatException("Invalid password. Password must meet policy requirements.");
        }

        if (!isEmailValid(email)) {
            throw new InvalidFormatException("Invalid email format.");
        }

        UserEntity user = userMapper.mapToUser(userDto);
        user.setPassword(password);

        UserEntity savedUser = userRepository.save(user);
        PsychologistDto psychologistDto = new PsychologistDto();
        psychologistDto.setUserId(savedUser.getUserId());
        psychologistService.savePsychologist(psychologistDto);
        String id = savedUser.getUserId();

//        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
//                .setServiceName("UserService")
//                .setControllerName("UserController")
//                .setActionUrl("/registerPsychologist")
//                .setActionType("POST")
//                .setActionResponse("SUCCESS")
//                .build();
//        loggingServiceBlockingStub.logRequest(loggingRequest);

        return userMapper.mapToUserDto(savedUser);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private boolean isEmailValid(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }


    @Override
    public List<UserDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : users) {
            userDtos.add(userMapper.mapToUserDto(userEntity));
        }

//        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
//                .setServiceName("UserService")
//                .setControllerName("UserController")
//                .setActionUrl("/users")
//                .setActionType("GET")
//                .setActionResponse("SUCCESS")
//                .build();
//        loggingServiceBlockingStub.logRequest(loggingRequest);
        return userDtos;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return userMapper.mapToUserDto(user);
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }
    @Override
    public UserDto getUserById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        return userMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setType(userDto.getType());
            user.setName(userDto.getName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.mapToUserDto(updatedUser);
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException("User not found with email: " + userDto.getEmail());
        }
    }

    @Override
    public List<UserDto> searchUsersByName(String name) {
        List<UserEntity> users = userRepository.findByNameContainingIgnoreCase(name);
        List<UserDto> userDtos = new ArrayList<>();
        for(UserEntity u : users) {
            userDtos.add(userMapper.mapToUserDto(u));
        }
        return userDtos;
    }


}
