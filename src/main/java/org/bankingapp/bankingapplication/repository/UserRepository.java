package org.bankingapp.bankingapplication.repository;

import org.bankingapp.bankingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

//    check if a recond in the db already exits by email
    Boolean existsByEmail(String email);

    Boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);


}
