package uz.sukhrob.lesson7task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @NotNull(message = "User Full Name must not be empty")
    private String fullName;
    @NotNull(message = "User username must not be empty")
    private String username;
    @NotNull(message = "User password must not be empty")
    private String password;
    @NotNull(message = "User confirmation Password must not be empty")
    private String confirmPassword;


}
