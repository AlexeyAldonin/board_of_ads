package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.NotificationDto;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    public NotificationRestController(){}

    @GetMapping
    public Response<List<NotificationDto>> getNotificationsOfUser(@AuthenticationPrincipal User principal){
        log.info("In NotificationRestController: inside getNotificationsOfUser notification ");
        User user = userService.getUserById(principal.getId());
        List<UserNotification> userNotifications = notificationService.getUsersAllNotifications(user);
        if ( userNotifications.size() > 0 ) {
            List<NotificationDto> notificationsResponseList = new ArrayList<>();
            for (UserNotification userNote: userNotifications) {
                notificationsResponseList.add(new NotificationDto(userNote));
            }
            return Response.ok(notificationsResponseList);
        } else {
            return  new ErrorResponse<>(new Error(204, "Notifications not found"));
        }
    }

    @PatchMapping("/{noteId}")
    public Response<Void> changeStatusToRead(@PathVariable Long noteId, @AuthenticationPrincipal User principal){
        log.info("inside api changeStatusToRead ");
        UserNotification un = notificationService.findByNoteIdAndUserId(noteId, principal.getId());
        if (un != null) {
            un.setStatus("read");
            notificationService.updateUserNotificationFields(un);
            return new Response<>();
        }
        return new ErrorResponse<>(new Error(204, "Notification not found"));
    }

    @DeleteMapping("/{noteId}")
    public Response<Void> deleteNotificationFromUser(@PathVariable Long noteId, @AuthenticationPrincipal User principal) {
        log.info("inside api deleteNotificationFromUser ");
        UserNotification un = notificationService.findByNoteIdAndUserId(noteId, principal.getId());
        if (un != null) {
            notificationService.deleteUserNotification(un);
            return new Response<>();
        }
        return new ErrorResponse<>(new Error(204, "Notification not found"));
    }

    @GetMapping("/count-map")
    public Response<int[]> getNotificationsCountOfUser(@AuthenticationPrincipal User principal){
        log.info("In getNotificationsCountOfUser api-method notification ");
        User user = userService.getUserById(principal.getId());
        int[] countMap = notificationService.getUsersNotificationsCountMap(user);
            return  Response.ok(countMap);
    }
}

