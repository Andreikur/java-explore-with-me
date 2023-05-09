package ru.practicum.viewStats.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.service.ViewStatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/stats")
public class ViewStateController {
    private final ViewStatsService viewStatsService;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public List<ViewStatsDto> getStats(@RequestParam(required = false) String start,
                                       @RequestParam(required = false) String end,
                                       @RequestParam String uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {
        return viewStatsService.getStats(LocalDateTime.parse(start, FORMATTER), LocalDateTime.parse(end, FORMATTER),
                uris, unique);
    }
}
