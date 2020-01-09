package stu.napls.boostim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostim.model.Node;

import java.util.List;

public interface NodeRepository  extends JpaRepository<Node, Long> {

    List<Node> findByOrderByClientNumber();
}