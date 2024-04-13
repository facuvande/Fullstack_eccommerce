package com.usersservice.controller;

import com.usersservice.dto.*;
import com.usersservice.model.User;
import com.usersservice.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    // AUTH
    @PostMapping("/auth/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, HttpServletResponse response){
        return userService.createUser(userDTO, response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        return userService.login(loginDTO, response);
    }

    @PostMapping("/auth/validateToken")
    public ResponseEntity<ValidationTokenDTO> validateToken(@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                User user = userService.getUserByEmail(userService.getUsernameByToken(token));
                return new ResponseEntity<>(new ValidationTokenDTO(true, user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getFavorite_product_ids(), user.getId_cart()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/auth/role/{token}")
    public ResponseEntity<String> getRoleByToken(@PathVariable String token){
        String email = userService.getUsernameByToken(token);
        return new ResponseEntity<>(userService.getRoleByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/api")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }


    @GetMapping("/api/{id_user}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id_user){
        UserResponseDTO myUser = userService.getUserById(id_user);
        if(myUser != null) {
            return new ResponseEntity<>(myUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/api/{id_user}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id_user){
        userService.deleteUserById(id_user);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @PutMapping("/api/editUser")
    public ResponseEntity<?> editUserByEmail(@RequestBody UserDTO newUserData, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                User user = userService.editUserByEmail(newUserData.getEmail(), newUserData);
                if(user == null){
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }else{
                    return new ResponseEntity<>(new ValidationTokenDTO(true, user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getFavorite_product_ids(), user.getId_cart()), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Not authenticated", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/api/addFavorite/{id_product}")
    public ResponseEntity<?> saveProductFavoriteByEmail(@PathVariable Long id_product, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                String email_user = userService.getUsernameByToken(token);
                System.out.println(email_user);
                User user = userService.getUserByEmail(email_user);
                System.out.println(user.getName());

                if(user == null){
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }else{
                    return userService.saveProductFavoriteByEmail(email_user, id_product);
                }
            }else{
                System.out.println(isValidToken);
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Not authenticated", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/api/addPayment/{payment_id}")
    public ResponseEntity<?> addPayment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long payment_id){
        System.out.println(authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                String email_user = userService.getUsernameByToken(token);
                User user = userService.getUserByEmail(email_user);

                if(user == null){
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }else{
                    return userService.addPaymentByUserEmail(email_user, payment_id);
                }
            }else{
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }else{
            System.out.println("entra");
            return new ResponseEntity<>("Not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/api/deleteFavorite/{id_product}")
    public ResponseEntity<?> deleteProductFavoriteByEmail(@PathVariable Long id_product, @RequestHeader("Authorization") String authorizationHeader){
        System.out.println("llega");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                String email_user = userService.getUsernameByToken(token);
                User user = userService.getUserByEmail(email_user);

                if(user == null){
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }else{
                    return userService.deleteProductFavoriteByEmail(email_user, id_product);
                }
            }else{
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Not authenticated", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/api/user/idProductsFavorites")
    public ResponseEntity<List<Long>> getFavoriteProductsIds(@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            System.out.println(token);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                String email_user = userService.getUsernameByToken(token);
                return userService.getFavoriteProductsIds(email_user);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/api/user/favorite/{id_product}")
    public ResponseEntity<String> removeUserFavoriteProductDeleted(@PathVariable Long id_product, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            System.out.println(token);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                // Todos los usuarios que tienen ese producto en favorito
                List<User> userFavoriteProductId = userService.findUserIdsByFavoriteProductId(id_product);

                for(User myUser : userFavoriteProductId) {
                    List<Long> listFavoriteProductIds = myUser.getFavorite_product_ids();
                    for (int i = 0; i < listFavoriteProductIds.size(); i++) {
                        if (listFavoriteProductIds.get(i).equals(id_product)) {
                            listFavoriteProductIds.remove(i);
                        }
                    }

                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(myUser.getEmail());
                    userDTO.setName(myUser.getName());
                    userDTO.setLastname(myUser.getLastname());
                    userDTO.setPassword(myUser.getPassword());
                    userDTO.setFavorite_product_ids(listFavoriteProductIds);

                    userService.editUserByEmail(myUser.getEmail(), userDTO);
                }
                return new ResponseEntity<>("Eliminado correctamente de todos los usuarios", HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
