package stu.napls.boostim.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Component
@Getter
public class WebServer {

    @Value("${webserver.url}")
    private String url;
}
