package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.repo.UserRepo;

import java.time.LocalDate;
import java.util.Random;

@Service
public class SubscriptionService {

    private LoginService loginService;
    private UserRepo userRepo;

    @Autowired
    public SubscriptionService(LoginService loginService, UserRepo userRepo) {
        this.loginService = loginService;
        this.userRepo = userRepo;
    }

    void giveFree7DaysSubscriptionIfFirstTimeLogin(User loadedUser){
        if (loadedUser.getExpireDate() == null){
            setFreeSubscriptionFor7Days(loadedUser);
        }
    }

    private void setFreeSubscriptionFor7Days(User user){
        LocalDate now = LocalDate.now();
        LocalDate newExpireDate = now.plusDays(7);
        user.setExpireDate(newExpireDate);
        userRepo.save(user);

        LogUtils.saveLogStatic(String.format("Subscription: activated 7 days trial, by: %s",
                user.getUsername()),
                Level.INFO);
    }

    public void extendSubscription(int code){
        if (validateCode(code)){
            User user = loginService.getUser();
            LocalDate expireDate = user.getExpireDate();
            LocalDate newExpireDate = expireDate.plusMonths(1);
            user.setExpireDate(newExpireDate);
            userRepo.save(user);

            LogUtils.saveLogStatic(String.format("Subscription: extended for 1 month, by: %s",
                    loginService.getUser().getUsername()),
                    Level.INFO);
        }
    }

    public int generateCode(){
        int randomNumber = new Random().nextInt(52940) + 5882;
        int code = ( randomNumber * 17 ) + 7;
        LogUtils.saveLogStatic(String.format("Subscription: generated code: %d by: %s",
                code, loginService.getUser().getUsername()),
                Level.INFO);
        return code;
    }

    private boolean validateCode(int code){
        if (( code - 7 ) % 17 == 0){
            LogUtils.saveLogStatic(String.format("Subscription: correct code: %d was used by: %s",
                    code, loginService.getUser().getUsername()),
                    Level.INFO);
            return true;
        } else {
            LogUtils.saveLogStatic(String.format("Subscription: incorrect code: %d was used by: %s",
                    code, loginService.getUser().getUsername()),
                    Level.WARNING);
            return false;
        }
    }



}
