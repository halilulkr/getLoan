package com.userForCredit.getLoan.business.abstracts;

import com.userForCredit.getLoan.business.dtos.UserListDto;
import com.userForCredit.getLoan.business.requests.userRequests.CreateUserRequest;
import com.userForCredit.getLoan.business.requests.userRequests.UpdateUserRequest;
import com.userForCredit.getLoan.core.utilities.results.DataResult;
import com.userForCredit.getLoan.core.utilities.results.Result;
import com.userForCredit.getLoan.entities.User;

import java.util.List;

public interface UserService {

    public Result add(CreateUserRequest createUserRequest);
    public Result update(UpdateUserRequest updateUserRequest);
    public Result delete(Integer userId);

    DataResult<List<UserListDto>> getAll();
    DataResult<UserListDto> getById(Integer userId);
    DataResult<UserListDto> getByNationalityNumber(String nationalityNumber);
    User getByNationalityNumberForOtherServices(String nationalityNumber);
}
