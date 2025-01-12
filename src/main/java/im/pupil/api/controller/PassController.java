package im.pupil.api.controller;

import im.pupil.api.dto.lesson.GetFullPassDto;
import im.pupil.api.dto.lesson.GetFullPassWithGroupMemberDto;
import im.pupil.api.dto.lesson.GetWeekDayPassDto;
import im.pupil.api.service.schedule.PassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/education/schedule/lesson/passes")
public class PassController {

    private final PassService passService;

    public PassController(PassService passService) {
        this.passService = passService;
    }

    @GetMapping("/week")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetWeekDayPassDto> readWeekStatistics(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("date") LocalDate date
    ) {
        return passService.readPassesCountOfGroupMemberByWeek(groupMemberId, date);
    }

    @GetMapping("/month")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetFullPassDto> readMonthStatistics(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("date") LocalDate date
    ) {
        return passService.readPassesOfGroupMemberByMonth(groupMemberId, date);
    }

    @GetMapping("/semester")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetFullPassDto> readPassesByDates(
            @RequestParam("groupMemberId") Integer groupMemberId
    ) {
        return passService.readPassesOfGroupMemberBySemester(groupMemberId);
    }

    @GetMapping("/group/week")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetFullPassWithGroupMemberDto> readPassesOfGroupByWeek(
            @RequestParam("groupId") Integer groupId,
            @RequestParam("date") LocalDate date
    ) {
        return passService.readPassesOfGroupByWeek(groupId, date);
    }

    @GetMapping("/group/month")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetFullPassWithGroupMemberDto> readPassesOfGroupByMonth(
            @RequestParam("groupId") Integer groupId,
            @RequestParam("date") LocalDate date
    ) {
        return passService.readPassesOfGroupByMonth(groupId, date);
    }
}























