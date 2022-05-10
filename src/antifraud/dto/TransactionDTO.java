package antifraud.dto;

import antifraud.validation.patterns.PatternsValidatorUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    private Long amount;

    @NotNull
    @NotEmpty
    @Pattern(regexp = PatternsValidatorUtil.IP_FORMAT, message = "Invalid IP format.")
    private String ip;

    @LuhnCheck(message = "Invalid card number.")
    private String number;

}
