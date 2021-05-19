package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.UserDto;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }


    public User getOne(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


    public ApiResponse addUser(UserDto  userDto) {

        if (userRepository.existsByUsername(userDto.getUsername()))
            return new ApiResponse("Bunday user mavjud",false);

        User user = new User(
                userDto.getFullName(),
                userDto.getUsername(),
                userDto.getPassword(),
                true,
                roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role","id",userDto.getRoleId(), "user not found"))
        );

        userRepository.save(user);
        return new ApiResponse("user qo'shildi",true);
    }


    public ApiResponse editUser(Long id, UserDto userDto){
        boolean exists = userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id);
        if (exists)
            return new ApiResponse("Bunday user mavjud",false);

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("user topilmadi",false);
        User editingUser = optionalUser.get();

        editingUser.setFullName(userDto.getFullName());
        editingUser.setUsername(userDto.getUsername());
        editingUser.setPassword(userDto.getPassword());

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent())
            return new ApiResponse("Role topilmadi",false);
        Role role = optionalRole.get();

        editingUser.setRole(role);
        return new ApiResponse("user tahrirlandi",true);
    }


    public ApiResponse delete(Long id){
        try {
        userRepository.deleteById(id);
            return new ApiResponse("uchirildi",true);
        }catch (Exception e){
            return new ApiResponse("uchirilmadi",false);
        }
    }
}
