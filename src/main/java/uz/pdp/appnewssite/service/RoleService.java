package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RoleDto;
import uz.pdp.appnewssite.repository.RoleRepository;

import java.util.*;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll(){
        return roleRepository.findAll();
    }


    public Role getOne(Long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }


    public ApiResponse add(RoleDto roleDto){
        boolean exists = roleRepository.existsByName(roleDto.getName());
        if (exists)
            return new ApiResponse("Bu lavozim mavjud",false);

        Role role = new Role(
                roleDto.getName(),
                roleDto.getDescription(),
                roleDto.getPermissions()
        );

        roleRepository.save(role);
        return new ApiResponse("Saqlandi",true);
    }


    public ApiResponse edit(Long id,RoleDto roleDto) {

        boolean exists = roleRepository.existsByNameAndIdNot(roleDto.getName(), id);
        if (exists)
            return new ApiResponse("Bunday role mavjud",false);

        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent())
            return new ApiResponse("role topilmadi",false);
        Role editingRole = optionalRole.get();

        editingRole.setName(roleDto.getName());
        editingRole.setDescription(roleDto.getDescription());
        editingRole.setPermissions(roleDto.getPermissions());
        roleRepository.save(editingRole);

        return new ApiResponse("Tahrirlandi",true);
    }


    public ApiResponse delete(Long id){
        try {
        roleRepository.deleteById(id);
        return new ApiResponse("uchirildi",true);
        }catch (Exception e){
            return new ApiResponse("uchirilmadi",false);
        }
    }
}
