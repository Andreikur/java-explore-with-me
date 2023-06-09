package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> listId, int from, int size) {
        List<User> userList = new ArrayList<>();
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        if (listId.isEmpty()) {
            userList.addAll(userRepository.findAllPage(pageRequest));
        } else {
            userList.addAll(userRepository.findAllById(listId));
        }
        return UserMapper.toUserDto(userList);
    }

    @Override
    @Transactional
    public void removeUser(long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        userRepository.deleteById(userId);
    }
}
