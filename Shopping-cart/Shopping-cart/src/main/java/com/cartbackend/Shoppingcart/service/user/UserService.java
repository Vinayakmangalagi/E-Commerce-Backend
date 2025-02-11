package com.cartbackend.Shoppingcart.service.user;

import com.cartbackend.Shoppingcart.dto.UserDto;
import com.cartbackend.Shoppingcart.model.User;
import com.cartbackend.Shoppingcart.request.CreateUserRequest;
import com.cartbackend.Shoppingcart.request.UserUpdateRequest;

public interface UserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
