package tn.ensit.soa.UserSOA.friendrequest.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ensit.soa.UserSOA.friendrequest.dto.FriendRequestDto;
import tn.ensit.soa.UserSOA.friendrequest.entities.FriendRequest;
import tn.ensit.soa.UserSOA.friendrequest.services.FriendRequestService;

import java.util.List;

@RestController
@RequestMapping("/friendRequests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<FriendRequest> sendRequest(@RequestBody FriendRequestDto requestDto) {
        FriendRequest friendRequest = friendRequestService.sendFriendRequest(requestDto.getRequesterId(), requestDto.getReceiverId());
        return ResponseEntity.ok(friendRequest);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<FriendRequest>> getPendingRequests(@PathVariable Long userId) {
        List<FriendRequest> requests = friendRequestService.getPendingRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<FriendRequest> acceptRequest(@PathVariable Long requestId) {
        FriendRequest friendRequest = friendRequestService.acceptRequest(requestId);
        return ResponseEntity.ok(friendRequest);
    }
}
