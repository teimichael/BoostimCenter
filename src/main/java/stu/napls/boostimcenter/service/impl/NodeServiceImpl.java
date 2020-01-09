package stu.napls.boostimcenter.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostimcenter.model.Node;
import stu.napls.boostimcenter.repository.NodeRepository;
import stu.napls.boostimcenter.service.NodeService;

import javax.annotation.Resource;
import java.util.Optional;

@Service("nodeService")
public class NodeServiceImpl implements NodeService {

    @Resource
    private NodeRepository nodeRepository;

    @Override
    public Node findById(long id) {
        Node node = null;
        Optional<Node> result = nodeRepository.findById(id);
        if (result.isPresent()) {
            node = result.get();
        }
        return node;
    }

    @Override
    public Node findBestNode() {
        return nodeRepository.findByOrderByClientNumber().get(0);
    }

    @Override
    public Node update(Node node) {
        return nodeRepository.save(node);
    }
}
