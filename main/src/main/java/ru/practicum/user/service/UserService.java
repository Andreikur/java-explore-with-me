package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
    List<UserDto> getUser(List<Long> listId, int from, int size);
    void removeUser(long userId);

}
