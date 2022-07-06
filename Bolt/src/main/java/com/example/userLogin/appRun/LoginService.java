package com.example.userLogin.appRun;

import com.example.userLogin.appuser.AppUser;
import com.example.userLogin.appuser.AppUserRepository;
import com.example.userLogin.appuser.AppUserRole;
import com.example.userLogin.registration.token.ConfirmationTokenRepository;
import com.example.userLogin.requests.DeleteUserRequest;
import com.example.userLogin.requests.LoginRequest;
import com.example.userLogin.requests.LogoutRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final OrdersRepository ordersRepository;

    public String login(LoginRequest lrequest) {
        boolean userExists2 = appUserRepository.findByEmail(lrequest.getEmail()).isPresent();

        if (userExists2){
            boolean online = appUserRepository.findByEmail(lrequest.getEmail()).get().getAvailability();
            if(online){
                return "You are already logged in";
            }else{
                String lrequestPassword = lrequest.getPassword();
                String userPassword = appUserRepository.findByEmail(lrequest.getEmail()).get().getPassword();

                if(bCryptPasswordEncoder.matches(lrequestPassword, userPassword)){
                    appUserRepository.availability(true, lrequest.getEmail());
                    return "You logged in";

                }else{
                    return "wrong password";
                }
            }
        }else{
            return "User doesn't exists";
        }
    }

    public String logout(LogoutRequest logoutRequest) {
        boolean userExists2 = appUserRepository.findByEmail(logoutRequest.getEmail()).isPresent();

        if (userExists2){
            boolean online = appUserRepository.findByEmail(logoutRequest.getEmail()).get().getAvailability();
            if(!online){
                return "You are not logged in";
            }else{
                String lrequestPassword = logoutRequest.getPassword();
                String userPassword = appUserRepository.findByEmail(logoutRequest.getEmail()).get().getPassword();

                if(bCryptPasswordEncoder.matches(lrequestPassword, userPassword)){
                    appUserRepository.availability(false, logoutRequest.getEmail());
                    return "You logged out";
                }else{
                    return "wrong password";
                }
            }
        }else{
            return "User doesn't exists";
        }
    }

    public String deleteUser(DeleteUserRequest deleteUserRequest) {
        String email = deleteUserRequest.getEmail();
        boolean userExists = appUserRepository.findByEmail(email).isPresent();
        if (userExists){
            String password = deleteUserRequest.getPassword();
            String dbPassword = appUserRepository.findByEmail(email).get().getPassword();
            if (bCryptPasswordEncoder.matches(password,dbPassword)) {
                AppUser appUser = appUserRepository.findByEmail(email).get();
                String token = confirmationTokenRepository.findByAppUser(appUser).get().getToken();
                confirmationTokenRepository.delete(token);
                if (appUser.getAppUserRole() == AppUserRole.CUSTOMER) {
                    ordersRepository.deleteAllOrdersC(email);
                }else if (appUser.getAppUserRole() == AppUserRole.PROVIDER){
                    ordersRepository.deleteAllOrdersP(email);
                }else{
                    ordersRepository.deleteAllOrdersC(email);
                    ordersRepository.deleteAllOrdersP(email);
                }
                appUserRepository.deleteAppUserByEmail(email);
                return "User deleted";
            }else{
                return "Incorect password";
            }
        }else{
            return "User with this email doesn't exists";
        }
    }








}
