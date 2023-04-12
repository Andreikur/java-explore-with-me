package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    /**
     * Добавление нового пользователя
     *
     * @param userDto
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * Получение информации о пользователях
     *
     * @param ids
     * @param from
     * @param size
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                  @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size) {
        return userService.getUsers(ids, from, size);
    }

    /**
     * Удаление пользователя
     *
     * @param userId
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Long userId) {
        userService.removeUser(userId);
    }
}
