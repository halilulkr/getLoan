package com.userForCredit.getLoan.business.concretes;

import com.userForCredit.getLoan.business.abstracts.UserService;
import com.userForCredit.getLoan.business.dtos.UserListDto;
import com.userForCredit.getLoan.business.requests.userRequests.CreateUserRequest;
import com.userForCredit.getLoan.business.requests.userRequests.UpdateUserRequest;
import com.userForCredit.getLoan.core.utilities.business.BusinessRules;
import com.userForCredit.getLoan.core.utilities.constants.Messages;
import com.userForCredit.getLoan.core.utilities.mapping.ModelMapperService;
import com.userForCredit.getLoan.core.utilities.results.*;
import com.userForCredit.getLoan.dataAccess.abstracts.UserDao;
import com.userForCredit.getLoan.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final ModelMapperService modelMapperService;

    public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
        this.userDao = userDao;
        this.modelMapperService = modelMapperService;
    }

    //CRUD

    @Override
    public Result add(CreateUserRequest createUserRequest) {
        Result result = BusinessRules.run(
                checkNationalityNumberIfExist(createUserRequest.getNationalityNumber()),
                checkPhoneNumberIfExist(createUserRequest.getPhoneNumber())
        );

        if (result!=null){
            return result;
        }

        User response = modelMapperService.forRequest().map(createUserRequest,User.class);
        this.userDao.save(response);

        return new SuccessResult(Messages.userAdded);
    }

    @Override
    public Result update(UpdateUserRequest updateUserRequest) {
        Result result = BusinessRules.run(
                checkUserIdIfExist(updateUserRequest.getId())
        );

        if (result!=null) {
            return result;
        }

        User response = modelMapperService.forRequest().map(updateUserRequest,User.class);
        this.userDao.save(response);

        return new SuccessResult(Messages.userUpdated);
    }

    @Override
    public Result delete(Integer userId) {
        Result result = BusinessRules.run(
                checkUserIdIfExist(userId)
        );



        if (result!=null) {
            return result;
        }

        this.userDao.deleteById(userId);

        return new SuccessResult(Messages.userDeleted);
    }

    @Override
    public DataResult<List<UserListDto>> getAll() {
        Result result = BusinessRules.run(
                checkAllUserIfExist()
        );

        if (result!=null) {
            return new ErrorDataResult<>(result.getMessage());
        }

        //Sorted by firstName (ASC)
        List<User> userList = this.userDao.findAll(Sort.by("firstName"));
        List<UserListDto> response = userList
                .stream()
                .map(user -> modelMapperService
                        .forDto()
                        .map(user, UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response, Messages.allUsersListed);
    }

    @Override
    public DataResult<UserListDto> getById(Integer userId) {
        Result result = BusinessRules.run(
                checkUserIdIfExist(userId)
        );

        if (result!=null){
            return new ErrorDataResult<>(result.getMessage());
        }

        User user = this.userDao.findById(userId).get();
        UserListDto response = modelMapperService.forDto().map(user, UserListDto.class);

        return new SuccessDataResult<UserListDto>(response, Messages.userListedById);
    }

    @Override
    public DataResult<UserListDto> getByNationalityNumber(String nationalityNumber) {
        Result result = BusinessRules.run(
                checkNationalityNumberIfNotExist(nationalityNumber)
        );

        if(result!=null){
            return new ErrorDataResult<>(result.getMessage());
        }

        User user = this.userDao.findByNationalityNumber(nationalityNumber).get();
        UserListDto response = modelMapperService.forDto().map(user, UserListDto.class);

        return new SuccessDataResult<UserListDto>(response);
    }

    @Override
    public User getByNationalityNumberForOtherServices(String nationalityNumber) {
        return this.userDao.findByNationalityNumber(nationalityNumber).get();
    }

    //Local Methods

    private Result checkNationalityNumberIfExist(String nationalityNumber){
        if (this.userDao.findByNationalityNumber(nationalityNumber).isPresent()){
            return new ErrorResult(Messages.userNationalityNumberAlreadyExist);
        }
        return new SuccessResult();
    }

    private Result checkPhoneNumberIfExist(String phoneNumber){
        if (this.userDao.findByPhoneNumber(phoneNumber).isPresent()){
            return new ErrorResult(Messages.userPhoneNumberAlreadyExist);
        }
        return new SuccessResult();
    }

    private Result checkUserIdIfExist(Integer userId){
        if (this.userDao.findById(userId).isEmpty()){
            return new ErrorResult(Messages.userIdNotExist);
        }
        return new SuccessResult();
    }

    private Result checkAllUserIfExist(){
        if (this.userDao.findAll().isEmpty()){
            return new ErrorResult(Messages.anyUserNotExist);
        }
        return new SuccessResult();
    }

    private Result checkNationalityNumberIfNotExist(String nationalityNumber){
        if (this.userDao.findByNationalityNumber(nationalityNumber).isEmpty()){
            return new ErrorResult(Messages.userNationalityNumberNotExist);
        }
        return new SuccessResult();
    }
}
