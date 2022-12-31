package com.twotrillion.comcal.user.schedule.controller;

import com.twotrillion.comcal.user.schedule.service.ScheduleService;
import com.twotrillion.comcal.user.schedule.vo.InfosForGetScheduleVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "/create_schedule", method = RequestMethod.POST)
    @ResponseBody
    public Object create_schedule(@RequestBody ScheduleVo scheduleVo, HttpSession session) {
        System.out.println("[ScheduleController] create_schedule() CALLED!!");

        Map<String, String> map = scheduleService.create_schedule(scheduleVo, session);

        return map;
    }

    @RequestMapping(value = "/get_schedule_by_no", method = RequestMethod.POST)
    @ResponseBody
    public Object get_schedule_by_no(@RequestBody Map<String, Integer> msg, HttpSession session) {
        System.out.println("[ScheduleController] get_schedule_by_no() CALLED!!");
        Map<String, Object> map = scheduleService.get_schedule_by_no(msg.get("scd_no"), session);

        return map;
    }
    @RequestMapping(value = "/get_schedule_list_by_date", method = RequestMethod.POST)
    @ResponseBody
    public Object get_schedule_list_by_date(@RequestBody InfosForGetScheduleVo infosForGetScheduleVo, HttpSession session) {
        System.out.println("[ScheduleController] get_schedule_by_date() CALLED!!");
        Map<String, Object> map = scheduleService.get_schedule_list_by_date(infosForGetScheduleVo, session);

        return map;
    }

    @RequestMapping(value = "/schedule_auth_confirm", method = RequestMethod.POST)
    @ResponseBody
    public Object schedule_auth_confirm(@RequestBody Map<String, Integer> msg, HttpSession session) {
        System.out.println("[ScheduleController] schedule_auth_confirm() CALLED!!");
        Map<String, Object> map = scheduleService.schedule_auth_confirm(msg.get("scd_no"), session);

        return map;
    }

    @RequestMapping(value = "/modify_schedule", method = RequestMethod.POST)
    @ResponseBody
    public Object modify_schedule(@RequestBody ScheduleVo scheduleVo, HttpSession session) {
        System.out.println("[ScheduleController] modify_schedule() CALLED!!");

        System.out.println(scheduleVo);
        Map<String, String> map = scheduleService.modify_schedule(scheduleVo, session);

        return map;
    }

    @RequestMapping(value = "/delete_schedule", method = RequestMethod.POST)
    @ResponseBody
    public Object delete_schedule(@RequestBody Map<String, Integer> msg, HttpSession session) {
        System.out.println("[ScheduleController] delete_schedule() CALLED!!");
        Map<String, String> map = scheduleService.delete_schedule(msg.get("scd_no"), session);

        return map;
    }

    @RequestMapping(value = "/get_schedule_list", method = RequestMethod.POST)
    @ResponseBody
    public Object get_schedule_list(@RequestBody InfosForGetScheduleVo infosForGetScheduleVo, HttpSession session) {
        System.out.println("[ScheduleController] get_schedule_list() CALLED!!");

        Map<String, Object> map = scheduleService.get_schedule_list(infosForGetScheduleVo, session);

        return map;
    }
}
