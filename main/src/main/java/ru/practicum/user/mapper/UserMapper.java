package ru.practicum.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<User> toUser(Iterable<UserDto> usersDto) {
        List<User> userList = new ArrayList<>();
        for (UserDto userDto : usersDto) {
            userList.add(toUser(userDto));
        }
        return userList;
    }

    public static List<UserDto> toUserDto(Iterable<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(toUserDto(user));
        }
        return userDtoList;
    }
}
