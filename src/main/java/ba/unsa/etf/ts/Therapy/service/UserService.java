package ba.unsa.etf.ts.Therapy.service;



import ba.unsa.etf.ts.Therapy.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createPatient(UserDto userDto);
    UserDto createPsychologist(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserByEmail(String email);
    UserDto getUserById(String id);
    UserDto updateUser(UserDto userDto, String password);
    void deleteUser(UserDto userDto);
    List<UserDto> searchUsersByName(String name);




}
