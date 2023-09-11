package in.ajit.expensetracker.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.ajit.expensetracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	// SELECT * FROM tbl_expenses WHERE user_id=? and category=?
	Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

	// SELECT * FROM tbl_expenses WHERE user_id=? and name LIKE '%keyword%'
	Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

	// SELECT * FROM tbl_expenses WHERE user_id=? and date BETWEEN 'startDate' AND
	// 'endDate'
	Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

	// SELECT *FROM tbl_expenses WHERE user_id=?
	Page<Expense> findByUserId(Long userId, Pageable page);

	// SELECT * FROM tbl_expenses WHERE user_id=? and id=?
	Optional<Expense> findByUserIdAndId(Long userId, Long id);
}
