package ru.practicum.viewStats.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.service.ViewStatsService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/stats")
public class ViewStateController {
    private final ViewStatsService viewStatsService;

    @GetMapping
    public List<ViewStatsDto> getStats(){


        return new List<ViewStatsDto>;
    }

}
