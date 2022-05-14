package antifraud.controllers;

import antifraud.dto.FeedBackDTO;
import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/antifraud")
@Validated
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResultDTO> addTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        log.info("Incoming transaction {}", transactionDTO);
        return new ResponseEntity<>(transactionService.validate(transactionDTO), HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<TransactionDTO> addFeedback(@RequestBody @Valid FeedBackDTO feedBackDTO) {
        log.info("Incoming feedback {}", feedBackDTO);
        TransactionDTO transaction = transactionService.changeFraudLimits(feedBackDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionDTO>> getAll() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        log.info("All transactions" + transactions.toString());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionDTO>> getTransactionByCardNumber(@PathVariable("number") @LuhnCheck(message = "Invalid card number.") String cardNumber) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
