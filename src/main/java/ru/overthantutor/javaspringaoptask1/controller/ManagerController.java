package ru.overthantutor.javaspringaoptask1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.overthantutor.javaspringaoptask1.aspect.TrackUserAction;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;
import ru.overthantutor.javaspringaoptask1.domain.Role;
import ru.overthantutor.javaspringaoptask1.service.JpaUserDetailsManager;
import ru.overthantutor.javaspringaoptask1.service.UserCreationService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final UserCreationService userCreationService;

    @TrackUserAction
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<AuthUserDetails> userDetailsList = jpaUserDetailsManager.getAllUser().stream()
                .filter(user -> user.getAuthorities().stream().anyMatch(auth  -> auth.getAuthority().equals("USER"))).toList();
        model.addAttribute("users", userDetailsList);
        return "user-list";
    }

    /**
     * Forming user creating panel
     * @param user    empty user
     * @return        user-create.html
     */
    @TrackUserAction
    @GetMapping("/user-create")
    public String createUserForm(AuthUserDetails user) {
        return "user-create";
    }

    /**
     * Creating user
     * @param user    creating user
     * @return        redirect:/users
     */
    @TrackUserAction
    @PostMapping(value = "/user-create")
    public String createUser(@RequestBody AuthUserDetails user) {
        if (user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getAge() == null) {
            return "redirect:/users";
        }
        userCreationService.createUser(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getAge(), Role.USER);
        return "redirect:/users";
    }

    /**
     * Deleting user
     * @param username username
     * @return         redirect:/users
     */
    @TrackUserAction
    @GetMapping("user-delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        jpaUserDetailsManager.deleteUser(username);
        return "redirect:/manager/users";
    }

    /**
     * Forming user updating panel
     * @param username username
     * @param model   html model
     * @return        user-update.html or redirect:/users
     */
    @TrackUserAction
    @GetMapping("user-update/{username}")
    public String updateUserForm(@PathVariable("username") String username, Model model) {
        AuthUserDetails authUserDetails = (AuthUserDetails) jpaUserDetailsManager.loadUserByUsername(username);
        if (authUserDetails == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", authUserDetails);
        return "user-update";
    }

    /**
     * Updating user
     * @param user    user with updated data
     * @return        redirect:/users
     */
    @TrackUserAction
    @PostMapping("user-update")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void updateUser(@RequestBody AuthUserDetails user) {
        Optional<AuthUserDetails> optional = jpaUserDetailsManager.findById(user.getId());
        if (optional.isPresent()) {
            AuthUserDetails updatedUser = optional.get();
            updatedUser.setLastname(user.getLastname());
            updatedUser.setFirstname(user.getFirstname());
            updatedUser.setUsername(user.getUsername());
            updatedUser.setAge(user.getAge());
            jpaUserDetailsManager.updateUser(updatedUser);
        }
    }
}
