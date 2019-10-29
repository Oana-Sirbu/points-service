package de.rakuten.points.repository;

import de.rakuten.points.model.PointsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsTransactionRepository extends JpaRepository<PointsTransaction, String> {}
