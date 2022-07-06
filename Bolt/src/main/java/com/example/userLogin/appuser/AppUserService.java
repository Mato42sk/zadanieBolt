package com.example.userLogin.appuser;

import com.example.userLogin.registration.token.ConfirmationToken;
import com.example.userLogin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;

import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        }

        public String signUpUser(AppUser appUser) {
            boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
            String token = null;

            if (userExists) {
                String role = appUserRepository.findByEmail(appUser.getEmail()).get().getAppUserRole().toString();
                String userEmail = appUserRepository.findByEmail(appUser.getUsername()).get().getEmail();
                String fName = appUserRepository.findByEmail(appUser.getUsername()).get().getFirstName();
                String lName = appUserRepository.findByEmail(appUser.getUsername()).get().getLastName();
                Boolean enabledU = appUserRepository.findByEmail(appUser.getUsername()).get().getEnabled();
                Boolean available = appUserRepository.findByEmail(appUser.getUsername()).get().getAvailability();

                if (Objects.equals(role, "CUSTOMER")) {
                    if (Objects.equals(userEmail, appUser.getUsername()) &&
                            Objects.equals(fName, appUser.getFirstName()) &&
                            Objects.equals(lName, appUser.getLastName()) &&
                            available &&
                            appUser.getCar() != null) {

                        appUserRepository.updateCustomerToProvider(appUser.getCar(),
                                appUser.getIBAN(),
                                appUser.getDescription(),
                                AppUserRole.CUSTOMER_PROVIDER,
                                appUser.getEmail());

                                return "User updated to CUSTOMER - PROVIDER";
                    } else {
                        if (enabledU) {
                            if(available){
                                throw new IllegalStateException("This account is already registered as a customer");
                            }else{
                                throw new IllegalStateException("Please log in before updating your profile");
                            }
                        }else{
                            throw new IllegalStateException("email already taken");
                        }
                    }
                } else if (Objects.equals(role, "PROVIDER")) {
                    if (Objects.equals(userEmail, appUser.getUsername()) &&
                            available){
                         appUserRepository.updateProviderToCustomer(appUser.getCardNumber(),
                                 appUser.getCardValidity(),
                                 appUser.getCv(),
                                 appUser.getPhoneNumber(),
                                 AppUserRole.CUSTOMER_PROVIDER,
                                 appUser.getEmail());

                        return "User updated to CUSTOMER - PROVIDER";
                    }else {
                        if (enabledU) {
                            System.out.println("\n\n\n\n\n" + available+ "\n\n\n\n\n");
                            if(available){
                                throw new IllegalStateException("This account is already registered as a customer");
                            }else{
                                throw new IllegalStateException("Please log in before updating your profile");
                            }
                        }else{
                            throw new IllegalStateException("email already taken");
                        }
                    }
                } else if (Objects.equals(role, "CUSTOMER_PROVIDER")) {
                    throw new IllegalStateException("email already taken");
                }
            } else {
                if (appUser.getIBAN()==null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
                    YearMonth cardExpiryDate = YearMonth.parse(appUser.getCardValidity(),formatter);
                    if (cardExpiryDate.isBefore(YearMonth.now())){
                        throw new IllegalStateException("Card already expired. Enter a valid credit card and try again");
                    }
                }
                String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
                appUser.setPassword(encodedPassword);
                appUserRepository.save(appUser);

                token = UUID.randomUUID().toString();
                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(30),
                        appUser
                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);
            }
            return token;
        }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
