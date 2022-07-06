package com.example.Controllers;

import com.example.userLogin.appRun.LoginService;
import com.example.userLogin.appRun.OrdersService;
import com.example.userLogin.registration.RegistrationService;
import com.example.userLogin.requests.*;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor

public class RegistrationController {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final OrdersService ordersService;

    @PostMapping
    public String register(@RequestBody @Valid RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

//-------------------------------------------------

    @PostMapping(path = "login")
    public String login(@RequestBody @Valid LoginRequest lrequest){
        return loginService.login(lrequest);
    }

    @PostMapping(path = "logout")
    public String logout(@RequestBody @Valid LogoutRequest logoutRequest){
        return loginService.logout(logoutRequest);
    }

    @GetMapping(path = "online")
    public String online() {
        return ordersService.showOnlineProviders();
    }

    @GetMapping(path = "orders")
    public String[][] showOrders(@RequestParam("provider") String provider){
        return ordersService.showOrders(provider);
    }

    @PostMapping(path = "send")
    public String sendOrder(@RequestBody @Valid OrderRequest orderRequest){
        return ordersService.sendOrder(orderRequest);
    }

    @PostMapping(path = "deleteOrder")
    public String deleteOrder(@RequestBody @Valid DeleteOrderRequest deleteOrderRequest){
        return ordersService.deleteOrder(deleteOrderRequest);
    }

    @PostMapping(path = "deleteUser")
    public String deleteUser(@RequestBody @Valid DeleteUserRequest deleteUserRequest){
        return loginService.deleteUser(deleteUserRequest);
    }
}





