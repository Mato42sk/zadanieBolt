package com.example.userLogin.appRun;

import com.example.userLogin.appuser.AppUser;
import com.example.userLogin.appuser.AppUserRepository;
import com.example.userLogin.appuser.AppUserRole;
import com.example.userLogin.requests.DeleteOrderRequest;
import com.example.userLogin.requests.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.userLogin.appuser.AppUserRole.*;

@Service
@AllArgsConstructor
public class OrdersService {
    private final AppUserRepository appUserRepository;
    private final OrdersRepository ordersRepository;

    public String showOnlineProviders(){
        List onlineProviders = new ArrayList();
        Long count = appUserRepository.count();

        if (count == 0){
            return "Nobody is online";
        }else {
            for (int i = 1; i <= count; i++) {
                try{
                    String email = appUserRepository.findById(Long.valueOf(i)).get().getEmail();
                    if (email != null) {
                        Boolean enabled = appUserRepository.findByEmail(email).get().getEnabled();
                        Boolean available = appUserRepository.findByEmail(email).get().getAvailability();
                        if (enabled && available) {
                            AppUserRole role = appUserRepository.findByEmail(email).get().getAppUserRole();
                            if (role == PROVIDER || role == AppUserRole.CUSTOMER_PROVIDER){
                                onlineProviders.add(email);
                            }
                        }
                    }
                }catch (NoSuchElementException e){
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf(onlineProviders);
    }

    public String[][] showOrders(String provider){
        boolean providerExists = appUserRepository.findByEmail(provider).isPresent();
        if (providerExists){
            boolean logged = appUserRepository.findByEmail(provider).get().getAvailability();
            if (logged){
                long ordersCount;
                String[ ][ ] ordersList = new String[10][4];
                if (ordersRepository.max() == null) {
                    ordersCount = 0;
                }else{
                    ordersCount = ordersRepository.max();
                }
                int id = 1;
                for (long i =0; i< ordersCount;i++){
                    try{
                        String aDestination = ordersRepository.findOrders(i+1,provider).get().getADestination();
                        String fDestination = ordersRepository.findOrders(i+1,provider).get().getFDestination();
                        String customer = ordersRepository.findOrders(i+1,provider).get().getCustomer();
                        Long oId = ordersRepository.findOrders(i+1,provider).get().getId();
                        ordersList[Math.toIntExact(id-1)][0]= String.valueOf(oId);
                        ordersList[Math.toIntExact(id-1)][1] = customer;
                        ordersList[Math.toIntExact(id-1)][2]= aDestination;
                        ordersList[Math.toIntExact(id-1)][3]= fDestination;
                        id++;
                    }catch(NoSuchElementException e){
                        e.printStackTrace();
                    }
                }
                return ordersList;
            }else{
                throw new IllegalStateException("You must be logged in, if you want show your orders");
            }
        }else{
            throw new IllegalStateException("Provider doesn't exists");
        }
    }

    public String sendOrder(OrderRequest orderRequest){
        String customer = orderRequest.getCustomer();
        boolean customerExists = appUserRepository.findByEmail(customer).isPresent();
        String provider = orderRequest.getProvider();
        boolean providerExists = appUserRepository.findByEmail(provider).isPresent();

// Customer
        if (customerExists){
            AppUserRole role = appUserRepository.findByEmail(customer).get().getAppUserRole();
            if (!(role == CUSTOMER || role == CUSTOMER_PROVIDER)){
                 throw new IllegalStateException("Only Customers can send an order");
            }
            boolean logged = appUserRepository.findByEmail(customer).get().getAvailability();
            if (!logged){
                throw new IllegalStateException("Customer must be logged in");
            }
        }else{
            throw new IllegalStateException("customer doesn't exists");
        }
// Provider
        if (providerExists){
            AppUserRole role = appUserRepository.findByEmail(provider).get().getAppUserRole();
            if (role == CUSTOMER){
                throw new IllegalStateException("Only Providers can get an order");
            }
        }else{
            throw new IllegalStateException("Provider doesn't exists");
        }
//customer == provider
        if (Objects.equals(customer, provider)){
            throw new IllegalStateException("Provider can't be same as the customer");
        }else{
            AppUser appUser = appUserRepository.findByEmail(provider).get();
            Order order = new Order(
                    orderRequest.getCustomer(),
                    orderRequest.getProvider(),
                    orderRequest.getADestination(),
                    orderRequest.getFDestination(),
                    appUser);

            Long count = ordersRepository.countProviderOrders(provider);

            if (count == 10){
                return "One provider can have max 10 orders";
            }else{
                ordersRepository.save(order);
                return "saved";
            }
        }
    }

    public String deleteOrder(DeleteOrderRequest deleteOrderRequest){
        String provider = deleteOrderRequest.getProvider();
        Long id = deleteOrderRequest.getId();
        boolean orderExists = ordersRepository.findOrders(id,provider).isPresent();
        if (orderExists){
            ordersRepository.deleteOrder(provider, id);
            return "Order deleted";
        }else{
            return"Order with id> "+id+" and provider> "+provider+" doesn't exists";
        }
    }

}
