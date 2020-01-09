package stu.napls.boostim.service;

import stu.napls.boostim.model.Node;
import stu.napls.boostim.model.User;

public interface NodeService {
    Node findById(long id);

    Node findBestNode();

    Node update(Node node);
}
