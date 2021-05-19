package uz.pdp.appnewssite.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.LoginDto;
import uz.pdp.appnewssite.payload.RegisterDto;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;
import uz.pdp.appnewssite.security.JwtProvider;
import uz.pdp.appnewssite.utils.AppConstants;

@Service
public class AuthService implements UserDetailsService {

    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;
    final
    PasswordEncoder passwordEncoder;
    final
    AuthenticationManager authenticationManager;
    final
    JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, @Lazy JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }



    public ApiResponse register(RegisterDto registerDto){

        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("parollar mos emas",false);
        if (userRepository.existsByUsername(registerDto.getUsername()))
            return new ApiResponse("bunday username li user mavjud",false);

        User user = new User(
                registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                true,
                roleRepository.findByName(AppConstants.USER).orElseThrow(() ->
                        new ResourceNotFoundException("Role","name",AppConstants.USER,"role name not found"))
        );

        userRepository.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yxatdan o'tdingiz",true);
    }



    public ApiResponse login(LoginDto loginDto){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            String token = jwtProvider.generateToken(loginDto.getUsername(), authenticate.getAuthorities());
            return new ApiResponse("Ok",true,token);
        }catch (Exception e){}
        return new ApiResponse("Parol yoki login xato",false);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }


}
