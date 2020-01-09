package stu.napls.boostim.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostim.model.Conversation;
import stu.napls.boostim.model.Node;
import stu.napls.boostim.repository.NodeRepository;
import stu.napls.boostim.service.NodeService;

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
