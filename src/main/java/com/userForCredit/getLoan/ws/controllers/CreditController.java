package com.userForCredit.getLoan.ws.controllers;

import com.userForCredit.getLoan.business.abstracts.CreditService;
import com.userForCredit.getLoan.business.requests.creditRequests.CreateCreditRequest;
import com.userForCredit.getLoan.business.requests.creditRequests.UpdateCreditRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/credits")
@CrossOrigin
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid CreateCreditRequest createCreditRequest){
        return ResponseEntity.ok(this.creditService.add(createCreditRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateCreditRequest updateCreditRequest){
        return ResponseEntity.ok(this.creditService.update(updateCreditRequest));
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.creditService.getAll());
    }

    @GetMapping("getById/{creditId}")
    public ResponseEntity<?> getById(@PathVariable Integer creditId){
        return ResponseEntity.ok(this.creditService.getById(creditId));
    }

    @DeleteMapping("delete/{creditId}")
    public ResponseEntity<?> delete(@PathVariable Integer creditId){
        return ResponseEntity.ok(this.creditService.delete(creditId));
    }

    @GetMapping("getByNationalityNumber")
    public ResponseEntity<?> getByNationalityNumber(@RequestParam String nationalityNumber){
        return ResponseEntity.ok(this.creditService.getByNationalityNumber(nationalityNumber));
    }
}
