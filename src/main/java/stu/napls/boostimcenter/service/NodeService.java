package stu.napls.boostimcenter.service;

import stu.napls.boostimcenter.model.Node;

public interface NodeService {
    Node findById(long id);

    Node findBestNode();

    Node update(Node node);
}
