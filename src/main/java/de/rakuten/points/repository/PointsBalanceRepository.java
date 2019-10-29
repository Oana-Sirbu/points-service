package de.rakuten.points.repository;

import de.rakuten.points.model.PointsBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointsBalanceRepository extends JpaRepository<PointsBalance, String> {
  Optional<PointsBalance> getByCustomerEmail(String customerEmail);
}
