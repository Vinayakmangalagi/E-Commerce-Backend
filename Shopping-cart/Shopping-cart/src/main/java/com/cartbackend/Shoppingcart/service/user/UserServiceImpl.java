package com.cartbackend.Shoppingcart.service.user;

import com.cartbackend.Shoppingcart.dto.UserDto;
import com.cartbackend.Shoppingcart.exception.AlreadyExistException;
import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.User;
import com.cartbackend.Shoppingcart.repository.UserRepository;
import com.cartbackend.Shoppingcart.request.CreateUserRequest;
import com.cartbackend.Shoppingcart.request.UserUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.getEmail())).map(req->{
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepository.save(user);
        }).orElseThrow(()->new AlreadyExistException("Oops!" +request.getEmail() + "already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException( "user already exists"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,() ->{
           throw new   ResourceNotFoundException("user not found");
        });
    }
    @Override
    public UserDto convertUserToDto(User user){

        return modelMapper.map(user,UserDto.class);
    }
}
