package com.userForCredit.getLoan.business.concretes;

import com.userForCredit.getLoan.business.abstracts.CreditCalculatorService;
import com.userForCredit.getLoan.business.abstracts.CreditService;
import com.userForCredit.getLoan.business.abstracts.UserService;
import com.userForCredit.getLoan.business.dtos.CreditListDto;
import com.userForCredit.getLoan.business.requests.creditRequests.CreateCreditRequest;
import com.userForCredit.getLoan.business.requests.creditRequests.UpdateCreditRequest;
import com.userForCredit.getLoan.core.adapters.CreditScoreService;
import com.userForCredit.getLoan.core.utilities.business.BusinessRules;
import com.userForCredit.getLoan.core.utilities.constants.Messages;
import com.userForCredit.getLoan.core.utilities.mapping.ModelMapperService;
import com.userForCredit.getLoan.core.utilities.results.*;
import com.userForCredit.getLoan.core.utilities.sms.SmsService;
import com.userForCredit.getLoan.dataAccess.abstracts.CreditDao;
import com.userForCredit.getLoan.entities.Credit;
import com.userForCredit.getLoan.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CreditManager implements CreditService {

    private final CreditDao creditDao;
    private final ModelMapperService modelMapperService;
    private final CreditCalculatorService creditCalculatorService;
    private final UserService userService;
    private final CreditScoreService creditScoreService;
    private final SmsService smsService;

    public CreditManager(CreditDao creditDao, ModelMapperService modelMapperService,
                         CreditCalculatorService creditCalculatorService , UserService userService,
                         CreditScoreService creditScoreService, SmsService smsService) {
        this.creditDao = creditDao;
        this.modelMapperService = modelMapperService;
        this.creditCalculatorService = creditCalculatorService;
        this.userService = userService;
        this.creditScoreService = creditScoreService;
        this.smsService = smsService;
    }

    //CRUD

    @Override
    public Result add(CreateCreditRequest createCreditRequest) {
        Result result = BusinessRules.run(
                checkNationalityNumberIfNotExist(createCreditRequest.getNationalityNumber()),
                checkNationalityNumberIfExist(createCreditRequest.getNationalityNumber())
        );

        if (result!=null){
            return result;
        }

        User user = this.userService.getByNationalityNumberForOtherServices(createCreditRequest.getNationalityNumber());

        double creditAmount = getCreditAmount(createCreditRequest.getNationalityNumber(),
                user.getSalary());

        if(creditAmount==0){
            return new ErrorResult(Messages.creditNotApproved);
        }

        Credit credit = Credit.builder().creditAmount(creditAmount).user(user).build();

        this.creditDao.save(credit);
        sendSmsIfCreditApproved(createCreditRequest.getNationalityNumber());

        return new SuccessResult(Messages.creditApproved);
    }

    @Override
    public Result update(UpdateCreditRequest updateCreditRequest) {
        Result result = BusinessRules.run(
                checkCreditIdIfNotExist(updateCreditRequest.getId())
        );

        if (result!=null){
            return result;
        }

        Credit credit = this.creditDao.findById(updateCreditRequest.getId()).get();
        Credit response = Credit
                .builder()
                .creditAmount(updateCreditRequest.getCreditAmount())
                .user(credit.getUser())
                .build();
        response.setId(updateCreditRequest.getId());

        this.creditDao.save(response);

        return new SuccessResult(Messages.creditUpdated);
    }

    @Override
    public Result delete(Integer creditId) {
        Result result = BusinessRules.run(
                checkCreditIdIfNotExist(creditId)
        );

        if (result!=null){
            return result;
        }

        this.creditDao.deleteById(creditId);

        return new SuccessResult(Messages.creditDeleted);
    }

    @Override
    public DataResult<List<CreditListDto>> getAll() {
        Result result = BusinessRules.run(
                checkAllCreditIfNotExist()
        );

        if (result!=null){
            return new ErrorDataResult<>(result.getMessage());
        }

        List<Credit> creditList = this.creditDao.findAll();
        List<CreditListDto> response = creditList
                .stream()
                .map(credit -> modelMapperService.forDto().map(credit, CreditListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CreditListDto>>(response, Messages.creditsListedById);
    }

    @Override
    public DataResult<CreditListDto> getById(Integer creditId) {
        Result result = BusinessRules.run(
                checkCreditIdIfNotExist(creditId)
        );

        if (result!=null){
            return new ErrorDataResult<>(result.getMessage());
        }

        Credit credit = this.creditDao.getById(creditId);
        CreditListDto response = modelMapperService.forDto().map(credit, CreditListDto.class);

        return new SuccessDataResult<CreditListDto>(response, Messages.creditListedById);
    }

    @Override
    public DataResult<CreditListDto> getByNationalityNumber(String nationalityNumber) {
        Result result = BusinessRules.run(
                checkNationalityNumberIfNotExist(nationalityNumber),
                checkCreditAmountByNationalityNumberIfNotExist(nationalityNumber)
        );

        if (result!=null){
            return new ErrorDataResult<>(result.getMessage());
        }

        Credit credit = this.creditDao.findCreditByUser_NationalityNumber(nationalityNumber).get();
        CreditListDto response = modelMapperService.forDto().map(credit, CreditListDto.class);

        return new SuccessDataResult<CreditListDto>(response, Messages.creditListedByNationalityNumber);
    }

    //Local methods

    private Double getCreditAmount(String nationalityNumber, Double salary){
        double creditScore = this.creditScoreService.getUserCreditScore(nationalityNumber);
        double creditAmount = this.creditCalculatorService.creditAmountCalculator(creditScore, salary);
        return creditAmount;
    }

    private Result checkNationalityNumberIfNotExist(String nationalityNumber){
        if (!this.userService.getByNationalityNumber(nationalityNumber).isSuccess()){
            return new ErrorResult(Messages.userNationalityNumberNotExist);
        }
        return new SuccessResult();
    }

    private Result checkCreditIdIfNotExist(Integer creditId){
        if (this.creditDao.findById(creditId).isEmpty()){
            return new ErrorResult(Messages.creditIdNotExist);
        }
        return new SuccessResult();
    }

    private Result checkAllCreditIfNotExist(){
        if (this.creditDao.findAll().isEmpty()){
            return new ErrorResult(Messages.anyCreditNotExist);
        }
        return new SuccessResult();
    }

    private Result checkCreditAmountByNationalityNumberIfNotExist(String nationalityNumber){
        if (this.creditDao.findCreditAmountByUser_NationalityNumber(nationalityNumber).isEmpty()){
            return new ErrorResult(Messages.anyCreditByNationalityNumberNotExist);
        }
        return new SuccessResult();
    }

    private void sendSmsIfCreditApproved(String nationalityNumber){
        List<Credit> creditList = this.creditDao.findCreditByUser_NationalityNumberOrderByIdDesc(nationalityNumber).get();
        Credit sms = creditList.get(0);
        this.smsService.sendSmsIfCreditApproved(sms);
    }

    //Vatandaşlık numarasına göre listelerken NonUnique hatasını çözmek için
    private Result checkNationalityNumberIfExist(String nationalityNumber){
        if (this.creditDao.findCreditByUser_NationalityNumber(nationalityNumber).isPresent()){
            return new ErrorResult(Messages.userCreditAlreadyExistByNationalityNumber);
        }
        return new SuccessResult();
    }
}
